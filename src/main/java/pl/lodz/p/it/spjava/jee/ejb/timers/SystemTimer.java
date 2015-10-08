package pl.lodz.p.it.spjava.jee.ejb.timers;

import javax.annotation.security.RunAs;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;

/**
 *
 * @author Krzysiek
 */
@Startup
@Singleton
@LocalBean
@RunAs("System")
@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SystemTimer {
    
    @Inject
    private HandleExpiration he;
    @Inject
    private HandleReservation hr;
    
    @Schedule(second = "1", minute = "1", hour = "*/1",persistent = false) //co 1 godzinę
//    @Schedule(second = "1", minute = "*/1", hour = "*",persistent = false) //co 1 minutę
    public void expWork(){
        he.deleteExpiredAdverts();
        he.remindExpiration();
    }
    
    @Schedule(second = "1", minute = "1", hour = "*/2",persistent = false) //co 2 godziny
//    @Schedule(second = "1", minute = "*/1", hour = "*",persistent = false) //co 1 minutę
    public void reservWork(){
        hr.deleteExpiredReservedAdverts();
        hr.remindReservedExpiration();
    }
    
}
