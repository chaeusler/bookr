package ch.haeuslers.bookr.common;

import org.slf4j.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.security.Principal;
import java.util.Arrays;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 2)
@Audited
public class Auditor {

    @Inject
    private transient Logger logger;

    @Inject
    private transient Principal principal;

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext invocationContext) throws Exception {
        final Object o = invocationContext.proceed();
        if (logger.isTraceEnabled()){
            final Class clazz = invocationContext.getMethod().getDeclaringClass();
            final String methodName = invocationContext.getMethod().getName();
            final Class<?>[] parameterTypes = invocationContext.getMethod().getParameterTypes();
            logger.trace("{}.{}({}) {}", clazz.getName(), methodName, Arrays.toString(parameterTypes), principal.getName());
        }
        return o;
    }
}
