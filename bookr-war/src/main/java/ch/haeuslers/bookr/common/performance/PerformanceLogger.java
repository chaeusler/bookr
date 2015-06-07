package ch.haeuslers.bookr.common.performance;

import org.slf4j.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 1)
@PerformanceLogged
public class PerformanceLogger {

    @Inject
    private transient Logger logger;

    @Inject
    private transient PerformanceTrackingService performanceTracking;

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext invocationContext) throws Exception {
        if (logger.isTraceEnabled()) {
            final Class clazz = invocationContext.getMethod().getDeclaringClass();
            final String methodName = invocationContext.getMethod().getName();
            final Class<?>[] parameterTypes = invocationContext.getMethod().getParameterTypes();

            final long start = System.currentTimeMillis();
            final Object o = invocationContext.proceed();
            final long duration = System.currentTimeMillis() - start;

            performanceTracking.registerMethodCall(clazz, methodName, parameterTypes, duration); // TODO use separate Property to enable
            logger.trace("{}.{}({}) {} ms", clazz.getName(), methodName, Arrays.toString(parameterTypes), duration);

            return o;
        }
        return invocationContext.proceed();
    }

}
