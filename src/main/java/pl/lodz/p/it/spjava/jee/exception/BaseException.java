package pl.lodz.p.it.spjava.jee.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Krzysiek
 */
@ApplicationException(rollback=true)
public abstract class BaseException extends Exception {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
    
}
