package ch.haeuslers.bookr.common.performance;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

@Interceptor
@PerformanceLogged
public class PerformanceLogger {

    @Inject
    private transient Logger log;

    @Inject
    private transient PerformanceTrackingService performanceTracking;

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext invocationContext) throws Exception {
        if (log.isDebugEnabled()) {
            final Class clazz = invocationContext.getMethod().getDeclaringClass();
            final String methodName = invocationContext.getMethod().getName();
            final Class<?>[] parameterTypes = invocationContext.getMethod().getParameterTypes();

            final long start = System.currentTimeMillis();
            final Object o = invocationContext.proceed();
            final long duration = System.currentTimeMillis() - start;

            performanceTracking.registerMethodCall(clazz, methodName, parameterTypes, duration); // TODO use separate Property to enable
            log.debug("{}.{}({}) {} ms", clazz.getName(), methodName, Arrays.toString(parameterTypes), duration);

            return o;
        }
        return invocationContext.proceed();
    }

}
