package pl.lodz.p.it.spjava.jee.ejb.timers;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.mail.MessagingException;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.AdvertEndpoint;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
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
@Log
public class HandleReservation {

    private static final Logger LOGGER = Logger.getLogger(HandleReservation.class.getName());

    public HandleReservation() {
    }

    @Inject
    private AdvertEndpoint advertEndpoint;
    @Inject
    private EmailManager emailManager;

    private List<Advert> expiredReservedAdvertsList;
    private List<Advert> preExpiredReservedAdvertsList;

    public void deleteExpiredReservedAdverts() {
        Calendar calFrom = Calendar.getInstance();
        calFrom.add(Calendar.DAY_OF_MONTH, -30);
        Calendar calUntil = Calendar.getInstance();
        calUntil.add(Calendar.DAY_OF_MONTH, -10);
        expiredReservedAdvertsList = advertEndpoint.obtainExpiredReservedAdverts(calFrom.getTime(), calUntil.getTime());
        for (Advert advert : expiredReservedAdvertsList) {
            try {
                sendDeleteMessage(advert);
                advertEndpoint.deleteAdvert(advert);
            } catch (BaseException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    public void remindReservedExpiration() {
        Calendar calFrom = Calendar.getInstance();
        calFrom.add(Calendar.DAY_OF_MONTH, -7);
        calFrom.add(Calendar.HOUR_OF_DAY, -2);// należy zmienić liczbę dodanych godzin w zależności od czasu wywoływania metody @Schedule klasy SystemTimer.
        Calendar calUntil = Calendar.getInstance();
        calUntil.add(Calendar.DAY_OF_MONTH, -7);
        preExpiredReservedAdvertsList = advertEndpoint.obtainExpiredReservedAdverts(calFrom.getTime(), calUntil.getTime());
        for (Advert advert : preExpiredReservedAdvertsList) {
            sendRemindingMessage(advert);
        }
    }

    public void sendDeleteMessage(Advert advert) {
        try {
            String subject = ContextUtils.i18NMessageMail("contact.expired.reserve.subject") + advert.getTitle();
            String toAddress = advert.getAccount().getEmail();
            String preMsg = ContextUtils.i18NMessageMail("contact.pre.msg");
            String msg = ContextUtils.i18NMessageMail("contact.expired.reserve.msg");
            StringBuilder sb = new StringBuilder();
            sb.append(preMsg).append(System.lineSeparator()).append(System.lineSeparator()).append(msg);
            emailManager.sendEmail(advert.getAccount().getEmail(), subject, sb.toString());
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void sendRemindingMessage(Advert advert) {
        try {
            String subject = ContextUtils.i18NMessageMail("contact.pre.expired.reserve.subject") + advert.getTitle();
            String toAddress = advert.getAccount().getEmail();
            String preMsg = ContextUtils.i18NMessageMail("contact.pre.msg");
            String msg = ContextUtils.i18NMessageMail("contact.pre.expired.reserve.msg");
            StringBuilder sb = new StringBuilder();
            sb.append(preMsg).append(System.lineSeparator()).append(System.lineSeparator()).append(msg);
            emailManager.sendEmail(advert.getAccount().getEmail(), subject, sb.toString());
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
