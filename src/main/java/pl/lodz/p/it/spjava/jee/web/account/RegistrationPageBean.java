package pl.lodz.p.it.spjava.jee.web.account;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.TypeEndpoint;
import pl.lodz.p.it.spjava.jee.ejb.managers.EmailVerificationManager;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;
import pl.lodz.p.it.spjava.jee.web.util.HashPassGenerator;

/**
 *
 * @author Krzysiek
 */
@Named("registrationPageBean")
@RequestScoped
public class RegistrationPageBean {

    private static final int TYPE_USER = 0;
    private static final int PASS_MIN_CHAR = 4;
    
    private static final Logger LOGGER = Logger.getLogger(RegistrationPageBean.class.getName());

    public RegistrationPageBean() {
    }

    @Inject
    private AccountEndpoint accountEndpoint;
    @Inject
    private TypeEndpoint typeEndpoint;
    @Inject
    private EmailVerificationManager emailVerifManager;

    private Account account = new Account();
    private String passwordCheck = "";

    public Account getAccount() {
        return account;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String register() {
        try {
            if (account.getPassword().length() < PASS_MIN_CHAR) {
                ContextUtils.emitI18NMessage("registerForm:password", "password.not.long.enough");
                return null;
            }
            if (!(passwordCheck.equals(account.getPassword()))) {
                ContextUtils.emitI18NMessage("registerForm:passwordCheck", "password.not.matching");
                return null;
            }
            account.setPassword(HashPassGenerator.generateHash(passwordCheck));
            passwordCheck = "";
            account.setType(typeEndpoint.obtainTypes().get(TYPE_USER));
            account.setLastLoginDate(new Date());
            account.setActivationDate(new Date());
            account.setActive(false);
            account.setVerification(emailVerifManager.getVerifCode());
            accountEndpoint.create(account);
            emailVerifManager.sendVerificationEmail(account);
            return "success";
        } catch (BaseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            if (ContextUtils.isI18NKeyExist(ex.getMessage())) {
                ContextUtils.emitI18NMessage("registerForm:email", ex.getMessage());
            }
        }
        return null;
    }
}
