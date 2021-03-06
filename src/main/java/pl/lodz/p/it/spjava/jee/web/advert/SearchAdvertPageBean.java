package pl.lodz.p.it.spjava.jee.web.advert;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import pl.lodz.p.it.spjava.jee.model.Category;
import pl.lodz.p.it.spjava.jee.web.util.ContextUtils;

/**
 *
 * @author Krzysiek
 */
@Named("searchAdvertPageBean")
@ViewScoped
public class SearchAdvertPageBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(SearchAdvertPageBean.class.getName());

    public SearchAdvertPageBean() {
    }

    @Inject
    private AdvertSession advertSession;

    @PostConstruct
    private void init() {
        categoryList = advertSession.obtainCategory();
        searchBy = null;
        searchValue = null;
    }
    private List<Category> categoryList;
    private String searchBy;
    private Object searchValue;

    public Object getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public String searchAdvert() {
        if (searchBy == null) {
            ContextUtils.dialogMessage("enter.search.by");
            return null;
        }
        if (searchValue == null) {
            ContextUtils.dialogMessage("enter.search.value");
            return null;
        }
        advertSession.setSearchValue(searchValue);
        return "listSearchedAdverts";
    }
}
