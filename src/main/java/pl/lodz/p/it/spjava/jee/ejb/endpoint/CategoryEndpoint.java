package pl.lodz.p.it.spjava.jee.ejb.endpoint;



import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.jee.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.model.Category;

/**
 *
 * @author Krzysiek
 */
@Stateless
@LocalBean
@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CategoryEndpoint extends AbstractEndpoint {

    @Inject
    private CategoryFacade categoryFacade;

    public List<Category> obtainCategory(){
        return categoryFacade.findAll();
    }

}
