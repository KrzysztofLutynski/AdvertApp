package pl.lodz.p.it.spjava.jee.web.advert;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Advert;

/**
 *
 * @author Krzysiek
 */
@Named("listSearchedAdvertsPageBean")
@RequestScoped
public class ListSearchedAdvertsPageBean {

    private static final Logger LOGGER = Logger.getLogger(ListSearchedAdvertsPageBean.class.getName());

    public ListSearchedAdvertsPageBean() {
    }

    @Inject
    private AdvertSession advertSession;

    @PostConstruct
    private void init() {
        searchValue = advertSession.getSearchValue();
        advertList = advertSession.obtainSearchedAdverts(searchValue);
    }

    private List<Advert> advertList;
    private Object searchValue;

    public Object getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public List<Advert> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<Advert> advertList) {
        this.advertList = advertList;
    }
    
    public boolean renderTable(){
        return !advertList.isEmpty();
    }

    public String viewAdvert(Advert advert) {
        advertSession.setViewAdvert(advert);
        return "viewAdvert";
    }

    public String deleteAdvert(Advert advert) throws BaseException {
//        TODO: stworzyć motodę wysyłającą e-mail z informacją o usunięciu ogłoszenia przez administratora
        advertSession.deleteAdvert(advert);
        return "listSearchedAdverts"+ "?faces-redirect=true";
    }
}
