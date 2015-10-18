package pl.lodz.p.it.spjava.jee.exception;

import javax.persistence.OptimisticLockException;
import pl.lodz.p.it.spjava.jee.model.Advert;

/**
 *
 * @author Krzysiek
 */
public class AdvertException extends BaseException {

    public final static String KEY_ADVERT_EDITION_VIOLATION = "advert.edition.error";
    
    public AdvertException() {
    }

    public AdvertException(String message) {
        super(message);
    }

    public AdvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdvertException(Throwable cause) {
        super(cause);
    }

    public AdvertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    private Advert advert;

    public Advert getAdvert() {
        return advert;
    }
    
    public static AdvertException createForOptimisticLockException(Advert advert, OptimisticLockException ole){
        AdvertException ae = new AdvertException(KEY_ADVERT_EDITION_VIOLATION, ole);
        ae.advert=advert;
        return ae;
    }
    
}
