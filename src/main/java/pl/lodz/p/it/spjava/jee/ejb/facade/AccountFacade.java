package pl.lodz.p.it.spjava.jee.ejb.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.lodz.p.it.spjava.jee.exception.AccountException;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Account_;

/**
 *
 * @author Krzysiek
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "pl.lodz.p.it.spjava_AdvertApp_war_0.1-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    public Account obtainAccountByLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get(Account_.email), login));
        TypedQuery<Account> tq = em.createQuery(query);
        return tq.getSingleResult();
    }

    public Account obtainAccountByVerifCode(String code) throws NoResultException {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByVerification", Account.class);
        tq.setParameter("x", code);
        return tq.getSingleResult();
    }

    @Override
    public void create(Account entity) throws BaseException {
        try {
            super.create(entity);
            em.flush();
        } catch (PersistenceException pe) {
            throw AccountException.createForNonUniqueEmail(entity, pe);
        }
    }

    @Override
    public void edit(Account entity) throws BaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (PersistenceException pe) {
            throw AccountException.createForNonUniqueEmail(entity, pe);
        }
    }

    @Override
    public void remove(Account entity) throws BaseException {
        try {
            super.remove(entity);
            em.flush();
        } catch (PersistenceException pe) {
            throw AccountException.createForAccountUsedByDB(entity, pe);
        }
    }
}
