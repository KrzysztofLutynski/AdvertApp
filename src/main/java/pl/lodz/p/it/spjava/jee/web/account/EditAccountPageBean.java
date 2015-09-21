package pl.lodz.p.it.spjava.jee.web.account;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.jee.ejb.managers.EmailVerificationManager;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;
import pl.lodz.p.it.spjava.jee.web.util.HashPassGenerator;

/**
 *
 * @author Krzysiek
 */
@Named("editAccountPageBean")
@RequestScoped
public class EditAccountPageBean {

    private static final int PASS_MIN_CHAR = 4;
    private static final Logger LOGGER = Logger.getLogger(EditAccountPageBean.class.getName());

    public EditAccountPageBean() {
    }

    @Inject
    private AccountSession accountSession;
    @Inject
    private EmailVerificationManager emailVerifManager;

    @PostConstruct
    private void init() {
        account = accountSession.getAccountUser();
        emailCheck = account.getEmail();
    }

    private Account account;
    private String emailCheck = "";
    private String passwordCheck = "";

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String editAccount() {
        try {
            if (account.getPassword().length() < PASS_MIN_CHAR) {
                ContextUtils.emitI18NMessage("editAccountForm:password", "password.not.long.enough");
                return null;
            }
            if (!(passwordCheck.equals(account.getPassword()))) {
                ContextUtils.emitI18NMessage("editAccountForm:passwordCheck", "password.not.matching");
                return null;
            }
            account.setPassword(HashPassGenerator.generateHash(passwordCheck));
            passwordCheck = "";
            if (account.getEmail().equals(emailCheck)) {
                accountSession.accountEdit(account);
                accountSession.obtainUserAccount();
            } else {
                account.setActive(false);
                account.setVerification(emailVerifManager.getVerifCode());
                accountSession.setAccountUser(account);
                emailVerifManager.sendVerificationEmail(account);
                accountSession.resetSession();
            }
            return "success";
        } catch (BaseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            if (ContextUtils.isI18NKeyExist(ex.getMessage())) {
                ContextUtils.emitI18NMessage("editAccountForm:email", ex.getMessage());
            }
        }
        return null;
    }

    public String deleteAccount(){
        try {
            accountSession.deleteAccount(account);
            accountSession.resetSession();
            return "success";
        } catch (BaseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            if (ContextUtils.isI18NKeyExist(ex.getMessage())) {
                ContextUtils.emitI18NMessage("editAccountForm:remark", ex.getMessage());
            }
            return null;
        }
    }
}