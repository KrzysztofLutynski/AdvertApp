package pl.lodz.p.it.spjava.jee.web.account;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Type;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Named("listAccountPageBean")
@ViewScoped
public class ListAccountPageBean implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(ListAccountPageBean.class.getName());
    
    public ListAccountPageBean() {
    }
    
    @Inject
    private AccountSession accountSession;
    
    @PostConstruct
    private void init() {
        account = accountSession.getUserAccount();
        typeList = accountSession.obtainTypes();
        accountList = accountSession.obtainAccounts();
    }
    
    private Account account;
    private List<Account> accountList;
    private List<Type> typeList;
    
    public Account getAccount() {
        return account;
    }
    
    public List<Account> getAccountList() {
        return accountList;
    }
    
    public List<Type> getTypeList() {
        return typeList;
    }
    
    public String editAccount(Account account) {
        try {
            accountSession.editAccount(account);
            return "success";
        } catch (BaseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            ContextUtils.dialogBox(ex.getMessage());
            return null;
        }
    }
    
    public String deleteAccount(Account account) {
        try {
            accountSession.deleteAccount(account);
            return "listAccount";
        } catch (BaseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            ContextUtils.dialogBox(ex.getMessage());
            return null;
        }
    }
}
