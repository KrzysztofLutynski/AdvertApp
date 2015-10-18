package pl.lodz.p.it.spjava.jee.web.util;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.context.RequestContext;
import pl.lodz.p.it.spjava.jee.exception.BaseException;

/**
 *
 * @author Krzysiek
 */
public class ContextUtils {

    public ContextUtils() {
    }

    public static void invalidateSession() {
        ((HttpSession) getContext().getSession(true)).invalidate();
    }

    public static boolean isI18NKeyExist(String key) {
        return ContextUtils.getDefaultBundle().getString(key) != null
                && !"".equals(ContextUtils.getDefaultBundle().getString(key));
    }

    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static String getContextParameter(String paramName) {
        return getContext().getInitParameter(paramName);
    }

    public static ResourceBundle getDefaultBundle() {
        String bundlePath = getContextParameter("resourceBundle.path");
        if (bundlePath == null) {
            return null;
        } else {
            return ResourceBundle.getBundle(bundlePath);
        }
    }

    public static void emitI18NMessage(String id, String key) {
        FacesMessage msg = new FacesMessage(ContextUtils.getDefaultBundle().getString(key));
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    public static String i18NMessage(String key) {
        FacesMessage msg = new FacesMessage(ContextUtils.getDefaultBundle().getString(key));
        return msg.getDetail();
    }

    public static ResourceBundle getMailBundle() {
        return ResourceBundle.getBundle("i18n.mail");
    }

    public static String i18NMessageMail(String key) {
        FacesMessage msg = new FacesMessage(ContextUtils.getMailBundle().getString(key));
        return msg.getDetail();
    }

    public static void dialogShow(BaseException be) {
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        msg.setSummary(ContextUtils.i18NMessage(be.getMessage()));
        RequestContext context = RequestContext.getCurrentInstance();
        context.showMessageInDialog(msg);
    }
    
    public static void dialogBox() {
        RequestContext context = RequestContext.getCurrentInstance();
//        context.execute("PF('dialogBox').show();");
        context.openDialog("dialogBox");
    }
    
}
