package pl.lodz.p.it.spjava.jee.ejb.endpoint;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import pl.lodz.p.it.spjava.jee.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.exception.BaseException;
import pl.lodz.p.it.spjava.jee.model.Account;

/**
 *
 * @author Krzysiek
 */
@Stateless
@LocalBean
@Log
public class AccountEndpoint extends AbstractEndpoint {
    
    @Inject
    private AccountFacade accountFacade;

    public void create(final Account account) throws BaseException {
        accountFacade.create(account);
    }

    public void edit(final Account account) throws BaseException {
        accountFacade.edit(account);
    }

    public void delete(final Account account) throws BaseException {
        accountFacade.remove(account);
    }

    public Account obtainUserAccount() {
        return obtainAccountByLogin(obtainUserLogin());
    }

    public String obtainUserLogin(){
        return sctx.getCallerPrincipal().getName();
    }

    public Account obtainAccountByLogin(String login) {
        return accountFacade.obtainAccountByLogin(login);
    }
    
    public Account obtainAccountByVerifCode(String code) throws NoResultException{
        return accountFacade.obtainAccountByVerifCode(code);
    }

    public List<Account> obtainAccounts() {
        return accountFacade.findAll();
    }

    
}
