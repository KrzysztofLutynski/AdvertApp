package pl.lodz.p.it.spjava.jee.ejb.facade;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.exception.AccountException;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Account_;

/**
 *
 * @author Krzysiek
 */
@Stateless
@Log
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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

    @RolesAllowed({"User", "Administrator"})
    public Account obtainAccountByLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get(Account_.email), login));
        TypedQuery<Account> tq = em.createQuery(query);
        return tq.getSingleResult();
    }

    @RolesAllowed("System")
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
    @PermitAll
    public void edit(Account entity) throws BaseException {
        try {
            super.edit(entity);
            em.flush();
        }catch (OptimisticLockException ole){
            throw AccountException.createForAccountChanged(entity, ole);
        } catch (PersistenceException pe) {
            throw AccountException.createForNonUniqueEmail(entity, pe);
        }
    }

    @Override
    @RolesAllowed({"User", "Administrator"})
    public void remove(Account entity) throws BaseException {
        try {
            super.remove(entity);
            em.flush();
        }catch (OptimisticLockException ole){
            throw AccountException.createForAccountChanged(entity, ole);
        } catch (PersistenceException pe) {
            throw AccountException.createForAccountUsedByDB(entity, pe);
        }
    }
}
