package pl.lodz.p.it.spjava.jee.ejb.managers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RunAs;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.lodz.p.it.spjava.jee.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.jee.ejb.interceptors.Log;
import pl.lodz.p.it.spjava.jee.model.Account;

/**
 *
 * @author Krzysiek
 */
@WebServlet(name = "EmailVerificationServlet", urlPatterns = {"/EmailVerificationServlet"})
@RunAs("System")
@Log
public class EmailVerificationServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EmailVerificationServlet.class.getName());

    @Inject
    private AccountEndpoint accountEndpoint;

    private Account account;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String verifCode = request.getParameter("verifCode");
            account = accountEndpoint.obtainAccountByVerifCode(verifCode);
            account.setActive(true);
            account.setVerification(null);
            accountEndpoint.edit(account);
            response.sendRedirect("main/userActivate.xhtml");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            response.sendRedirect("main/userActivateError.xhtml");
        }
    }
}
