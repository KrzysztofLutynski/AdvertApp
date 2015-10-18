package pl.lodz.p.it.spjava.jee.web.advert;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.AdvertEndpoint;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.CategoryEndpoint;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.StatusEndpoint;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Advert;
import pl.lodz.p.it.spjava.jee.model.Category;
import pl.lodz.p.it.spjava.jee.model.Status;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Named("advertSession")
@SessionScoped
public class AdvertSession implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(AdvertSession.class.getName());

    public AdvertSession() {
    }

    @Inject
    private AdvertEndpoint advertEndpoint;
    @Inject
    private CategoryEndpoint categoryEndpoint;
    @Inject
    private StatusEndpoint statusEndpoint;

    @PostConstruct
    public void init() {
        templateAdvert = new Advert();
    }
    private Advert editAdvert;
    private Advert viewAdvert;
    private Advert templateAdvert;
    private Object searchValue;
    private boolean reserveFlag;

    public boolean isReserveFlag() {
        return reserveFlag;
    }

    public void setReserveFlag(boolean reserveFlag) {
        this.reserveFlag = reserveFlag;
    }

    public Advert getTemplateAdvert() {
        return templateAdvert;
    }

    public void setTemplateAdvert(Advert templateAdvert) {
        this.templateAdvert = templateAdvert;
    }

    public Object getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public Advert getViewAdvert() {
        return viewAdvert;
    }

    public void setViewAdvert(Advert viewAdvert) {
        this.viewAdvert = viewAdvert;
    }

    public Advert getEditAdvert() {
        return editAdvert;
    }

    public void setEditAdvert(Advert editAdvert) {
        this.editAdvert = editAdvert;
    }

    public List<Advert> obtainUserAdverts(Account account) {
        return advertEndpoint.obtainUserAdverts(account);
    }

    public List<Advert> obtainSearchedAdverts(Object object) {
        return advertEndpoint.obtainSearchedAdverts(object);
    }

    public List<Advert> obtainReservedAdverts(Account account) {
        return advertEndpoint.obtainReservedAdverts(account);
    }

    public List<Category> obtainCategory() {
        return categoryEndpoint.obtainCategory();
    }

    public List<Status> obtainStatus() {
        return statusEndpoint.obtainStatus();
    }

    public void createAdvert(Advert advert) {
        advertEndpoint.create(advert);
    }

    public void editAdvert(Advert advert) {
        advertEndpoint.editAdvert(advert);
    }

    public void deleteAdvert(Advert advert) {
        advertEndpoint.deleteAdvert(advert);
    }
}
