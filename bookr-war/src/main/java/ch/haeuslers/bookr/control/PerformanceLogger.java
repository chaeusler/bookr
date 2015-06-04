package ch.haeuslers.bookr.control;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@PerformanceLogged
public class PerformanceLogger {

    @Inject
    private transient Logger log;

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext invocationContext) throws Exception {
        if (log.isDebugEnabled()) {
            final String clazz = invocationContext.getMethod().getDeclaringClass().getName();
            final String method = invocationContext.getMethod().getName();

            final long start = System.currentTimeMillis();
            final Object o = invocationContext.proceed();
            final long duration = start - System.currentTimeMillis();

            log.debug("{}.{} - {} ms", clazz, method, duration);

            return o;
        }
        return invocationContext.proceed();
    }

}
