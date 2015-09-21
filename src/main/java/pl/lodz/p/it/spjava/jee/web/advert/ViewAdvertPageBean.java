package pl.lodz.p.it.spjava.jee.web.advert;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.jee.model.Advert;
import pl.lodz.p.it.spjava.jee.model.Status;
import pl.lodz.p.it.spjava.jee.web.account.AccountSession;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Named("viewAdvertPageBean")
@RequestScoped
public class ViewAdvertPageBean {

    private static final int STATUS_ACTIVE = 0;
    private static final int STATUS_UNRESERVABLE = 1;
    private static final int STATUS_RESERVED = 2;

    private static final Logger LOGGER = Logger.getLogger(ViewAdvertPageBean.class.getName());

    public ViewAdvertPageBean() {
    }

    @Inject
    private AdvertSession advertSession;
    @Inject
    private AccountSession accountSession;

    @PostConstruct
    private void init() {
        statusList = advertSession.obtainStatus();
        advert = advertSession.getViewAdvert();
        advertSession.setReserveFlag(false);

    }
    private List<Status> statusList;
    private Advert advert;

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    public boolean renderReserved() {
        return advert.getStatus().equals(statusList.get(STATUS_RESERVED));
    }

    public boolean disableReserve() {
        if (ContextUtils.getContext().getRemoteUser()!=null) {
            return (!advert.getStatus().equals(statusList.get(STATUS_ACTIVE))) 
                    || advert.getAccount().equals(accountSession.getAccountUser());
        } else {
            return true;
        }
    }

    public boolean disableContactSeller() {
        if (ContextUtils.getContext().getRemoteUser()!=null) {
            return advert.getAccount().equals(accountSession.getAccountUser());
        }else{
            return true;
        }
    }

    public String reserveAdvert() {
        advertSession.setReserveFlag(true);
        return "contactSeller";
    }

    public String contactSeller() {
        return "contactSeller";
    }

}
