package pl.lodz.p.it.spjava.jee.exception;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import pl.lodz.p.it.spjava.jee.model.Account;

/**
 *
 * @author Krzysiek
 */
public class AccountException extends BaseException {

    public final static String KEY_EMAIL_UNIQUE_VIOLATION = "email.used";
    public final static String KEY_ACCOUNT_USED_VIOLATION = "account.used";
    public final static String KEY_ACCOUNT_CHANGED_VIOLATION = "account.changed";
    
    public AccountException() {
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountException(Throwable cause) {
        super(cause);
    }

    public AccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    private Account account;

    public Account getAccount() {
        return account;
    }
    
    public static AccountException createForNonUniqueEmail(Account account, PersistenceException pe){
        AccountException ae = new AccountException(KEY_EMAIL_UNIQUE_VIOLATION, pe);
        ae.account=account;
        return ae;
    }
    
    public static AccountException createForAccountUsedByDB(Account account, PersistenceException pe){
        AccountException ae = new AccountException(KEY_ACCOUNT_USED_VIOLATION, pe);
        ae.account=account;
        return ae;
    }
    
    public static AccountException createForAccountOptimistickLockEx(Account account, OptimisticLockException ole){
        AccountException ae = new AccountException(KEY_ACCOUNT_CHANGED_VIOLATION, ole);
        ae.account=account;
        return ae;
    }
    
}
