package pl.lodz.p.it.spjava.jee.ejb.managers;

import java.util.Date;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;

/**
 *
 * @author Krzysiek
 */
@Stateless
@RolesAllowed({"User","Administrator","System"})
@Log
public class EmailManager {

    public EmailManager() {
    }

    @Resource(name = "mail/appMailSession")
    private Session mailSession;

    public void sendEmail(String address, String subject, String message) throws AddressException, MessagingException {
        Message mes = new MimeMessage(mailSession);
        mes.setSubject(subject);
        mes.setText(message);
        mes.setSentDate(new Date());
        Transport transport = mailSession.getTransport("smtps");
        transport.connect();
        transport.sendMessage(mes,InternetAddress.parseHeader(address, false));
        transport.close();
    }
}
