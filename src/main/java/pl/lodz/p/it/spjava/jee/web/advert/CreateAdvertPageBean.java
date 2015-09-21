package pl.lodz.p.it.spjava.jee.web.advert;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Advert;
import pl.lodz.p.it.spjava.jee.model.Category;
import pl.lodz.p.it.spjava.jee.model.Status;
import pl.lodz.p.it.spjava.jee.web.account.AccountSession;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Named("createAdvertPageBean")
@ViewScoped
public class CreateAdvertPageBean implements Serializable {

    private static final int CATEGORY_SOCIAL = 0;
    private static final int STATUS_ACTIVE = 0;
    private static final int STATUS_UNRESERVABLE = 1;

    private static final Logger LOGGER = Logger.getLogger(CreateAdvertPageBean.class.getName());

    public CreateAdvertPageBean() {
    }

    @Inject
    private AdvertSession advertSession;
    @Inject
    private AccountSession accountSession;

    @PostConstruct
    private void init() {
        categoryList = advertSession.obtainCategory();
        statusList = advertSession.obtainStatus();
        account = accountSession.getAccountUser();
        advert = advertSession.getTemplateAdvert();
    }
    private List<Category> categoryList;
    private List<Status> statusList;
    private Account account;
    private Advert advert;

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void uploadPicture(FileUploadEvent event) {
        UploadedFile picture = event.getFile();
        byte[] pictureByte = picture.getContents();
        advert.setPicture(pictureByte);
    }

    public Date maxExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    public String createAdvert() {
        try {
            if (advert.getAdvertExpiryDate() != null && advert.getAdvertExpiryDate().before(new Date())) {
                ContextUtils.emitI18NMessage("createAdvertForm:dateExpiry", "advert.date.expiry.constraint");
                return null;
            }
            if (advert.getCategory().equals(categoryList.get(CATEGORY_SOCIAL))) {
                advert.setStatus(statusList.get(STATUS_UNRESERVABLE));
            } else {
                advert.setStatus(statusList.get(STATUS_ACTIVE));
            }
            advert.setIdAdvert(null);
            advert.setAdvertAddDate(new Date());
            advert.setAdvertEditDate(new Date());
            advert.setAccount(account);
            advertSession.init();
            advertSession.createAdvert(advert);
            return "success";
        } catch (BaseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
