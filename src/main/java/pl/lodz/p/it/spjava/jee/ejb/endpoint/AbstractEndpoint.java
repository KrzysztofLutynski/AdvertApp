package pl.lodz.p.it.spjava.jee.ejb.endpoint;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;

/**
 *
 * @author Krzysiek
 */
public abstract class AbstractEndpoint {
    
    @Resource
    SessionContext sctx;
    protected static final Logger LOGGER = Logger.getGlobal();
    private String transactionID;
    
    public void afterBegin(){
        transactionID = newTransactionID();
        LOGGER.log(Level.INFO, "TXid={0} afterBegin: {1}, identity: {2}", 
                new Object[]{transactionID, this.getClass().getName(), sctx.getCallerPrincipal().getName()});
    }
    public void beforeCompletion(){
        LOGGER.log(Level.INFO, "TXid={0} beforeCompletion: {1}, identity: {2}", 
                new Object[]{transactionID, this.getClass().getName(), sctx.getCallerPrincipal().getName()});
    }
    
    public void afterCompletion(boolean commited){
        LOGGER.log(Level.INFO, "TXid={0} afterCompletion: {1}, identity: {2}, result: {3}", 
                new Object[]{transactionID, this.getClass().getName(), sctx.getCallerPrincipal().getName(), commited ? "COMMIT" : "ROLLBACK"});
    }
    
    private String newTransactionID() {
        Long time = System.currentTimeMillis();
        Integer rand = (int) (Math.random() * 100);
        return time.toString() + rand.toString();
    }
}
