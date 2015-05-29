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
    private Logger logger;

    @AroundInvoke
    public Object aroundInvoke(InvocationContext invocationContext) throws Exception {
        if (logger.isDebugEnabled()) {
            String clazz = invocationContext.getMethod().getDeclaringClass().getName();
            String method = invocationContext.getMethod().getName();

            long start = System.currentTimeMillis();
            Object o = invocationContext.proceed();
            long duration = start - System.currentTimeMillis();

            logger.debug("{}.{} - {} ms", clazz, method, duration);

            return o;
        }
        return invocationContext.proceed();
    }

}
