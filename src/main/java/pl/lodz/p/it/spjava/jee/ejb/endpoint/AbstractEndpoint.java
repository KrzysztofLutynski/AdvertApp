package pl.lodz.p.it.spjava.jee.ejb.endpoint;

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
        //przypisanie wartości transactionID - stworzyć unikalny ciąg znaków 
        //identyfikujących transakcje można wykorzystać threadlocalrandom i currenttimemillis
       
        //wpis do loggera
    }
    public void beforeCompletion(){
        //wpis do loggera
    }
    
    public void afterCompletion(boolean commited){
        //wis do loggera
    }
    
    
}
