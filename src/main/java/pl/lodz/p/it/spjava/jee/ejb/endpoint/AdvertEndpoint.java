package pl.lodz.p.it.spjava.jee.ejb.endpoint;

import java.util.Date;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.jee.ejb.facade.AdvertFacade;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;
import pl.lodz.p.it.spjava.jee.model.Advert;

/**
 *
 * @author Krzysiek
 */
@Stateful
@LocalBean
@Log
@RolesAllowed({"User","Administrator"})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AdvertEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @Inject
    private AdvertFacade advertFacade;

    public void create(final Advert advert) {
        advertFacade.create(advert);
    }
    
    @PermitAll
    public List<Advert> obtainSearchedAdverts(final Object searchValue){
        return advertFacade.obtainSearchedAdverts(searchValue);
    }
    
    public List<Advert> obtainUserAdverts(final Account account){
        return advertFacade.obtainUserAdverts(account);
    }
    
    public List<Advert> obtainReservedAdverts(final Account account){
        return advertFacade.obtainReservedAdverts(account);
    }
    
    @RolesAllowed("System")
    public List<Advert> obtainExpiredAdverts(final Date from, Date until){
        return advertFacade.obtainExpiredAdverts(from, until);
    }
    
    @RolesAllowed("System")
    public List<Advert> obtainExpiredReservedAdverts(final Date from, Date until){
        return advertFacade.obtainExpiredReservedAdverts(from, until);
    }
    
    public void editAdvert(final Advert advert) throws BaseException{
        advertFacade.edit(advert);
    }
    
    @RolesAllowed({"User","Administrator","System"})
    public void deleteAdvert (final Advert advert) throws BaseException{
        advertFacade.remove(advert);
    }

}
