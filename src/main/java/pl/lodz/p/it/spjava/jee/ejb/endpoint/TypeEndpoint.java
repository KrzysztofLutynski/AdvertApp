package pl.lodz.p.it.spjava.jee.ejb.endpoint;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.jee.ejb.facade.TypeFacade;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.model.Type;

/**
 *
 * @author Krzysiek
 */
@Stateless
@LocalBean
@Log
public class TypeEndpoint extends AbstractEndpoint {

    @Inject
    private TypeFacade typeFacade;

    public List<Type> obtainTypes(){
        return typeFacade.findAll();
    }

}
