package pl.lodz.p.it.spjava.jee.ejb.timers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.mail.MessagingException;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.AdvertEndpoint;
import pl.lodz.p.it.spjava.jee.ejb.managers.EmailManager;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Advert;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Stateful
@RolesAllowed("System")
public class HandleExpiration {

    private static final Logger LOGGER = Logger.getLogger(HandleExpiration.class.getName());

    public HandleExpiration() {
    }

    @Inject
    private AdvertEndpoint advertEndpoint;
    @Inject
    private EmailManager emailManager;

    private List<Advert> expiredAdvertsList;
    private List<Advert> preExpiredAdvertsList;

    public void deleteExpiredAdverts() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        expiredAdvertsList = advertEndpoint.obtainExpiredAdverts(cal.getTime(), new Date());
        for (Advert advert : expiredAdvertsList) {
            try {
                sendDeleteMessage(advert);
                advertEndpoint.deleteAdvert(advert);
            } catch (BaseException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    public void remindExpiration() {
        Calendar calFrom = Calendar.getInstance();
        calFrom.add(Calendar.DAY_OF_MONTH, 3);
        Calendar calUntil = Calendar.getInstance();
        calUntil.add(Calendar.DAY_OF_MONTH, 3);
        calUntil.add(Calendar.HOUR_OF_DAY, 1);// należy zmienić liczbę dodanych godzin w zależności od czasu wywoływania metody @Schedule klasy SystemTimer.
        preExpiredAdvertsList = advertEndpoint.obtainExpiredAdverts(calFrom.getTime(), calUntil.getTime());
        for (Advert advert : preExpiredAdvertsList) {
            sendRemindingMessage(advert);
        }
    }

    public void sendDeleteMessage(Advert advert) {
        try {
            String subject = ContextUtils.i18NMessageMail("contact.expired.subject") + advert.getTitle();
            String toAddress = advert.getAccount().getEmail();
            String preMsg = ContextUtils.i18NMessageMail("contact.pre.msg");
            String msg = ContextUtils.i18NMessageMail("contact.expired.msg");
            StringBuilder sb = new StringBuilder();
            sb.append(preMsg).append(System.lineSeparator()).append(System.lineSeparator()).append(msg);
            emailManager.sendEmail(advert.getAccount().getEmail(), subject, sb.toString());
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void sendRemindingMessage(Advert advert) {
        try {
            String subject = ContextUtils.i18NMessageMail("contact.pre.expired.subject") + advert.getTitle();
            String toAddress = advert.getAccount().getEmail();
            String preMsg = ContextUtils.i18NMessageMail("contact.pre.msg");
            String msg = ContextUtils.i18NMessageMail("contact.pre.expired.msg");
            StringBuilder sb = new StringBuilder();
            sb.append(preMsg).append(System.lineSeparator()).append(System.lineSeparator()).append(msg);
            emailManager.sendEmail(advert.getAccount().getEmail(), subject, sb.toString());
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
