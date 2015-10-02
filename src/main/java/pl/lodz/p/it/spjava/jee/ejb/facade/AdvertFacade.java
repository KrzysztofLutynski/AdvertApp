package pl.lodz.p.it.spjava.jee.ejb.facade;

import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Advert;
import pl.lodz.p.it.spjava.jee.model.Category;

/**
 *
 * @author Krzysiek
 */
@Stateless
public class AdvertFacade extends AbstractFacade<Advert> {

    @PersistenceContext(unitName = "pl.lodz.p.it.spjava_AdvertApp_war_0.1-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdvertFacade() {
        super(Advert.class);
    }

    @RolesAllowed("User")
    public List<Advert> obtainUserAdverts(Account account) {
        TypedQuery<Advert> tq = em.createNamedQuery("Advert.findByIdAccount", Advert.class);
        tq.setParameter("x", account.getIdAccount());
        return tq.getResultList();
    }

    public List<Advert> obtainSearchedAdverts(Object searchValue) {
        String searchClass = searchValue.getClass().getSimpleName();
        TypedQuery<Advert> tq = null;
        switch (searchClass) {
            case "Category":
                Category searchCategory = (Category) searchValue;
                tq = em.createNamedQuery("Advert.findByCategory", Advert.class);
                tq.setParameter("x", searchCategory.getIdCategory());
                break;
            case "Date":
                Date searchDate = (Date) searchValue;
                tq = em.createNamedQuery("Advert.findByEditDate", Advert.class);
                tq.setParameter("x", searchDate);
                break;
            case "String":
                String searchLastName = (String) searchValue;
                tq = em.createNamedQuery("Advert.findByLastName", Advert.class);
                tq.setParameter("x", searchLastName);
                break;
        }
        return tq.getResultList();
    }

    @RolesAllowed("User")
    public List<Advert> obtainReservedAdverts(Account account) {
        TypedQuery<Advert> tq = em.createNamedQuery("Advert.findByUserReserv", Advert.class);
        tq.setParameter("x", account.getIdAccount());
        return tq.getResultList();
    }

    @RolesAllowed("System")
    public List<Advert> obtainExpiredAdverts(Date from, Date until) {
        TypedQuery<Advert> tq = em.createNamedQuery("Advert.findByExpiryDate", Advert.class);
        tq.setParameter("x", from);
        tq.setParameter("y", until);
        return tq.getResultList();
    }

    @RolesAllowed("System")
    public List<Advert> obtainExpiredReservedAdverts(Date from, Date until) {
        TypedQuery<Advert> tq = em.createNamedQuery("Advert.findByReserveDate", Advert.class);
        tq.setParameter("x", from);
        tq.setParameter("y", until);
        return tq.getResultList();
    }
}
