package pl.lodz.p.it.spjava.jee.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.model.Status;

/**
 *
 * @author Krzysiek
 */
@Stateless
@Log
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class StatusFacade extends AbstractFacade<Status> {
    @PersistenceContext(unitName = "AdvertApp_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StatusFacade() {
        super(Status.class);
    }
}
