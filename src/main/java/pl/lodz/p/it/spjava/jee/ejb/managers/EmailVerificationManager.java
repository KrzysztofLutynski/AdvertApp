package pl.lodz.p.it.spjava.jee.ejb.managers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Stateless
public class EmailVerificationManager {
    
    private static final Logger LOGGER = Logger.getLogger(EmailVerificationManager.class.getName());

    public EmailVerificationManager() {
    }

    @Inject
    private EmailManager emailManager;
    
    @PostConstruct
    public void init(){
        verifCode = verificationCode();
    }
            
    private String verifCode;

    public String getVerifCode() {
        return verifCode;
    }

    private String verificationCode() {
        Long time = System.currentTimeMillis();
        Integer rand = (int) (Math.random() * 100);
        return time.toString() + rand.toString();
    }
    
    public void sendVerificationEmail(Account account) {
        String address = account.getEmail();
        String subject = ContextUtils.i18NMessage("contact.registration.subject");
        String message = ContextUtils.i18NMessage("contact.registration.message");
        String url = "http://localhost:8080/AdvertApp/EmailVerificationServlet?verifCode=" + verifCode;
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(System.lineSeparator()).append(System.lineSeparator()).append(url);
        try {
            emailManager.sendEmail(address, subject, sb.toString());
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
