package pl.lodz.p.it.spjava.jee.ejb.interceptors;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Krzysiek
 */
@Interceptor
@Log
public class LoggingInterceptor {

    @Resource
    private SessionContext sctx;

    @AroundInvoke
    public Object loggingInvokeForMethod(InvocationContext invocation) throws Exception {
        boolean flag = true;
        StringBuilder sb = new StringBuilder();
        sb.append("METHOD: ").append(invocation.getTarget().getClass().getName()).append(".").append(invocation.getMethod().getName());
        sb.append("; CALLED BY: ").append(sctx.getCallerPrincipal().getName());
        try {
            Object[] params = invocation.getParameters();
            if (params != null) {
                for (Object param : params) {
                    if (param != null) {
                        if(param.getClass().getSimpleName().equalsIgnoreCase("string")){
                        sb.append(" PARAMETER: >STRING<");
                        }else
                        sb.append(" PARAMETERS: ").append(param.getClass().getName()).append("=").append(param.toString());
                    }else{
                        sb.append(" PARAMETER: >NULL<");
                    }
                }
            }
            Object result = invocation.proceed();
            
            if(result!=null){
                sb.append(". OBJECT RETURNED: ").append(result.getClass().getName()).append(" VALUE: ").append(result.toString());
            }else{
                sb.append(". RETURNED VALUE: >NULL<");
            }
            return result;
        }catch(Exception ex){
            flag = false;
            sb.append(". EXCEPTION OCCURED: ").append(ex.getClass().getName());
            throw ex;
        }finally{
            if(flag){
                Logger.getGlobal().log(Level.INFO,sb.toString());
            }else{
                Logger.getGlobal().log(Level.WARNING,sb.toString());
            }
        }
    }
}
