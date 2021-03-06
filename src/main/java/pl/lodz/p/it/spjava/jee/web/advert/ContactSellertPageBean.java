package pl.lodz.p.it.spjava.jee.web.advert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import pl.lodz.p.it.spjava.jee.ejb.managers.EmailManager;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Advert;
import pl.lodz.p.it.spjava.jee.model.Status;
import pl.lodz.p.it.spjava.jee.web.account.AccountSession;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Named("contactSellerPageBean")
@RequestScoped
public class ContactSellertPageBean implements Serializable {

    private static final int STATUS_RESERVED = 2;
    private static final String MAILING_ERROR = "advert.message.error";

    private static final Logger LOGGER = Logger.getLogger(ContactSellertPageBean.class.getName());

    public ContactSellertPageBean() {
    }

    @Inject
    private AdvertSession advertSession;
    @Inject
    private AccountSession accountSession;
    @Inject
    private EmailManager emailManager;

    @PostConstruct
    private void init() {
        statusList = advertSession.obtainStatus();
        account = accountSession.getUserAccount();
        advert = advertSession.getViewAdvert();
        reserveFlag = advertSession.isReserveFlag();
        if (reserveFlag) {
            msg = ContextUtils.i18NMessageMail("contact.seller.reserve.msg");
        } else {
            msg = ContextUtils.i18NMessageMail("contact.seller.standard.msg");
        }
    }
    private Account account;
    private Advert advert;
    private String msg;
    private boolean reserveFlag;
    private List<Status> statusList;

    public boolean isReserveFlag() {
        return reserveFlag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String send() {
        try {
            if (msg == null) {
                ContextUtils.emitI18NMessage("contactSellerForm:body", "field.required");
                return null;
            }
            if (reserveFlag) {
                reserveAdvert();
            }
            String subject = ContextUtils.i18NMessageMail("contact.seller.subject") + advert.getTitle();
            String toAddress = advert.getAccount().getEmail();
            String preMsg = ContextUtils.i18NMessageMail("contact.seller.pre.msg") + " " + account.getFirstName() + " " + account.getLastName();
            String postMsg = ContextUtils.i18NMessageMail("contact.seller.post.msg") + " " + account.getEmail();
            StringBuilder sb = new StringBuilder();
            sb.append(preMsg).append(System.lineSeparator()).append(System.lineSeparator()).append(msg).
                    append(System.lineSeparator()).append(System.lineSeparator()).append(postMsg);
            emailManager.sendEmail(toAddress, subject, sb.toString());
            return "success";
        } catch (BaseException be) {
            LOGGER.log(Level.SEVERE, null, be);
            ContextUtils.dialogBox(be.getMessage());
            return null;
        } catch (AddressException aex) {
            LOGGER.log(Level.SEVERE, null, aex);
            ContextUtils.dialogBox(MAILING_ERROR);
            return null;
        } catch (MessagingException mex) {
            LOGGER.log(Level.SEVERE, null, mex);
            ContextUtils.dialogBox(MAILING_ERROR);
            return null;
        }
    }

    public void reserveAdvert() throws BaseException {
        advert.setAdvertReserveDate(new Date());
        advert.setStatus(statusList.get(STATUS_RESERVED));
        advert.SetBuyerAccount(accountSession.getUserAccount());
        advertSession.editAdvert(advert);
    }
}
