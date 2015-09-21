package pl.lodz.p.it.spjava.jee.ejb.endpoint;

import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
@Stateless
@LocalBean
@Log
public class AdvertEndpoint extends AbstractEndpoint {

    @Inject
    private AdvertFacade advertFacade;

    public void create(final Advert advert) throws BaseException {
        advertFacade.create(advert);
    }
    
    public List<Advert> obtainSearchedAdverts(final Object searchValue){
        return advertFacade.obtainSearchedAdverts(searchValue);
    }
    
    public List<Advert> obtainUserAdverts(final Account account){
        return advertFacade.obtainUserAdverts(account);
    }
    
    public List<Advert> obtainReservedAdverts(final Account account){
        return advertFacade.obtainReservedAdverts(account);
    }
    
    public List<Advert> obtainExpiredAdverts(final Date from, Date until){
        return advertFacade.obtainExpiredAdverts(from, until);
    }
    
    public List<Advert> obtainExpiredReservedAdverts(final Date from, Date until){
        return advertFacade.obtainExpiredReservedAdverts(from, until);
    }
    
    public void editAdvert(final Advert advert) throws BaseException{
        advertFacade.edit(advert);
    }
    
    public void deleteAdvert (final Advert advert) throws BaseException{
        advertFacade.remove(advert);
    }

}
