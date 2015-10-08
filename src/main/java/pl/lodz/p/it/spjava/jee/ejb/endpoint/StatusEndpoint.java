package pl.lodz.p.it.spjava.jee.ejb.endpoint;



import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.jee.ejb.facade.StatusFacade;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.model.Status;

/**
 *
 * @author Krzysiek
 */
@Stateless
@LocalBean
@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class StatusEndpoint extends AbstractEndpoint  {

    @Inject
    private StatusFacade statusFacade;

    public List<Status> obtainStatus(){
        return statusFacade.findAll();
    }

}
