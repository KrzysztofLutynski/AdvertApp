package pl.lodz.p.it.spjava.jee.web.advert;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Advert;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Named("listSearchedAdvertsPageBean")
@ViewScoped
public class ListSearchedAdvertsPageBean implements Serializable {

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

    public List<Advert> getAdvertList() {
        return advertList;
    }

    public boolean renderTable() {
        return !advertList.isEmpty();
    }

    public String viewAdvert(Advert advert) {
        advertSession.setViewAdvert(advert);
        return "viewAdvert";
    }

    public String deleteAdvert(Advert advert) {
//        TODO: stworzyć motodę wysyłającą e-mail z informacją o usunięciu ogłoszenia przez administratora
        try {
            advertSession.deleteAdvert(advert);
            return "listSearchedAdverts";
        } catch (BaseException be) {
            LOGGER.log(Level.SEVERE, null, be);
            ContextUtils.dialogBox(be.getMessage());
            return null;
        }
    }
}
