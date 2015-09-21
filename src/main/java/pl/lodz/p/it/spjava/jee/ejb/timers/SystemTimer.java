package pl.lodz.p.it.spjava.jee.ejb.timers;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Krzysiek
 */
@Startup
@Singleton
@LocalBean
public class SystemTimer {
    
    @Inject
    private HandleExpiration he;
    @Inject
    private HandleReservation hr;
    
//    @Schedule(second = "1", minute = "1", hour = "*/1",persistent = false) //co 1 godzinę
//    @Schedule(second = "1", minute = "*/1", hour = "*",persistent = false) //co 1 minutę
    public void expWork(){
        he.deleteExpiredAdverts();
        he.remindExpiration();
    }
    
//    @Schedule(second = "1", minute = "1", hour = "*/2",persistent = false) //co 2 godziny
//    @Schedule(second = "1", minute = "*/1", hour = "*",persistent = false) //co 1 minutę
    public void reservWork(){
        hr.deleteExpiredReservedAdverts();
        hr.remindReservedExpiration();
    }
    
}
