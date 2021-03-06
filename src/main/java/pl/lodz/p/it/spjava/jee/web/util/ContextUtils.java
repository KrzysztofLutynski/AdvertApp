package pl.lodz.p.it.spjava.jee.web.util;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

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
            return ResourceBundle.getBundle(bundlePath, obtainLocale());
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
        return ResourceBundle.getBundle("i18n.mail", obtainLocale());
    }

    public static String i18NMessageMail(String key) {
        FacesMessage msg = new FacesMessage(ContextUtils.getMailBundle().getString(key));
        return msg.getDetail();
    }

    public static void dialogBox(String key) {
        emitI18NMessage(null, key);
        RequestContext.getCurrentInstance().execute("PF('dlg').show();");
    }

    public static void dialogMessage(String key) {
        FacesMessage msg = new FacesMessage(ContextUtils.getDefaultBundle().getString(key));
        msg.setSeverity(FacesMessage.SEVERITY_WARN);
        RequestContext.getCurrentInstance().showMessageInDialog(msg);
    }

    public static Locale obtainLocale() {
        if (FacesContext.getCurrentInstance() != null) {
            return FacesContext.getCurrentInstance().getViewRoot().getLocale();
        } else {
            return new Locale("pl");
        }
    }

}
