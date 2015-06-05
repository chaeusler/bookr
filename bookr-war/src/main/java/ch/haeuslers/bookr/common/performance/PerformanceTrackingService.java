package ch.haeuslers.bookr.common.performance;

import javax.ejb.Local;

@Local
public interface PerformanceTrackingService {

    void registerMethodCall(Class clazz, String methodName, Class<?>[] parameterTypes, long duration);
}
