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
@Named("listReservedAdvertsPageBean")
@RequestScoped
public class ListReservedAdvertsPageBean {

    private static final int STATUS_ACTIVE = 0;

    private static final Logger LOGGER = Logger.getLogger(ListReservedAdvertsPageBean.class.getName());

    public ListReservedAdvertsPageBean() {
    }

    @Inject
    private AdvertSession advertSession;
    @Inject
    private AccountSession accountSession;

    @PostConstruct
    private void init() {
        account = accountSession.getAccountUser();
        advertList = advertSession.obtainReservedAdverts(account);
        statusList = advertSession.obtainStatus();
    }

    private List<Status> statusList;
    private List<Advert> advertList;
    private Account account;

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
    
    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }
    
    public boolean renderTable(){
        return !advertList.isEmpty();
    }

    public String viewAdvert(Advert advert) {
        advertSession.setViewAdvert(advert);
        return "viewAdvert";
    }

    public String quitReserv(Advert advert) {
        try {
            advert.SetBuyerAccount(null);
            advert.setAdvertReserveDate(null);
            advert.setStatus(statusList.get(STATUS_ACTIVE));
            advertSession.editAdvert(advert);
            return "listReservedAdverts";
        } catch (BaseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            ContextUtils.dialogBox();
            return null;
        }
    }
}
