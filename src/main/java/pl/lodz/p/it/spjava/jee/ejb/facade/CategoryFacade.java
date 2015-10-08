package pl.lodz.p.it.spjava.jee.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.model.Category;

/**
 *
 * @author Krzysiek
 */
@Stateless
@Log
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CategoryFacade extends AbstractFacade<Category> {
    @PersistenceContext(unitName = "pl.lodz.p.it.spjava_AdvertApp_war_0.1-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
}
