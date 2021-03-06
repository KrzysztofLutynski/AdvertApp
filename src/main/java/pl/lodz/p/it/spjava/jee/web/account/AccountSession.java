package pl.lodz.p.it.spjava.jee.web.account;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.TypeEndpoint;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Type;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Named("accountSession")
@SessionScoped
public class AccountSession implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(AccountSession.class.getName());

    public AccountSession() {
    }

    @Inject
    private AccountEndpoint accountEndpoint;
    @Inject
    private TypeEndpoint typeEndpoint;

    @PostConstruct
    private void construct() {
        userAccount = obtainUserAccount();
        lastLoginDate = new Date();
    }

    @PreDestroy
    private void destroy() {
        try {
            userAccount.setLastLoginDate(lastLoginDate);
            accountEndpoint.edit(userAccount);
        } catch (BaseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private Account userAccount;
    private Date lastLoginDate;

    public Account getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

    public void registerAccount(Account account) throws BaseException {
        accountEndpoint.create(account);
    }

    public void editAccount(Account account) throws BaseException {
        accountEndpoint.edit(account);
    }

    public void deleteAccount(Account account) throws BaseException {
        accountEndpoint.delete(account);
    }

    public Account obtainUserAccount() {
        return accountEndpoint.obtainUserAccount();
    }

    public String resetSession() {
        ContextUtils.invalidateSession();
        return "start";
    }

    public List<Account> obtainAccounts() {
        return accountEndpoint.obtainAccounts();
    }

    public List<Type> obtainTypes() {
        return typeEndpoint.obtainTypes();
    }
}
