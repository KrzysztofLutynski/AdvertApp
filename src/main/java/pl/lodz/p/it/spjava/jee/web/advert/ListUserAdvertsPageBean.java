package pl.lodz.p.it.spjava.jee.web.advert;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
@Named("listUserAdvertsPageBean")
@RequestScoped
public class ListUserAdvertsPageBean {

    private static final int STATUS_ACTIVE = 0;
    private static final int STATUS_RESERVED = 2;

    private static final Logger LOGGER = Logger.getLogger(ListUserAdvertsPageBean.class.getName());

    public ListUserAdvertsPageBean() {
    }

    @Inject
    private AdvertSession advertSession;
    @Inject
    private AccountSession accountSesion;

    @PostConstruct
    private void init() {
        statusList = advertSession.obtainStatus();
        account = accountSesion.getAccountUser();
        advertList = advertSession.obtainUserAdverts(account);
    }

    private Account account;
    private List<Advert> advertList;
    private List<Status> statusList;

    public List<Status> getListStatus() {
        return statusList;
    }

    public void setListStatus(List<Status> listStatus) {
        this.statusList = listStatus;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Advert> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<Advert> advertList) {
        this.advertList = advertList;
    }

    public boolean renderTable() {
        return !advertList.isEmpty();
    }

    public boolean renderReserved(Advert advert) {
        return advert.getStatus().equals(statusList.get(STATUS_RESERVED));
    }

    public String editAdvert(Advert advert) {
        advertSession.setEditAdvert(advert);
        return "editAdvert";
    }

    public String deleteAdvert(Advert advert) {
        advertSession.deleteAdvert(advert);
        return "listUserAdverts";
    }

    public String viewAdvert(Advert advert) {
        advertSession.setViewAdvert(advert);
        return "viewAdvert";
    }

    public String templateAdvert(Advert advert) {
        advert.SetBuyerAccount(null);
        advert.setAdvertReserveDate(null);
        advertSession.setTemplateAdvert(advert);
        return "createAdvert";
    }

    public String quitReserv(Advert advert) {
        advert.SetBuyerAccount(null);
        advert.setAdvertReserveDate(null);
        advert.setStatus(statusList.get(STATUS_ACTIVE));
        advertSession.editAdvert(advert);
        return "listUserAdverts";
    }

}
