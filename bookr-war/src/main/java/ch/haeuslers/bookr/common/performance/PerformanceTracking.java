package ch.haeuslers.bookr.common.performance;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
@Startup
public class PerformanceTracking implements PerformanceTrackingMXBean, PerformanceTrackingService {

    private MBeanServer platformMBeanServer;
    private ObjectName objectName = null;

    private final ConcurrentHashMap<MethodCallKey, List<MethodCall>> methodCallMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void registerInJMX() {
        try {
            objectName = new ObjectName("ch.haeuslers.bookr:type=" + this.getClass().getName());
            platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            platformMBeanServer.registerMBean(this, objectName);
        } catch (Exception e) {
            throw new IllegalStateException("Problem during registration of Monitoring into JMX:" + e);
        }
    }

    @PreDestroy
    public void unregisterFromJMX() {
        try {
            platformMBeanServer.unregisterMBean(this.objectName);
        } catch (Exception e) {
            throw new IllegalStateException("Problem during unregistration of Monitoring into JMX:" + e);
        }
    }

    @Override
    public Map<String, Double> getAverageOfFiveMethodDurations() {
        final Map<String, Double> collected = new HashMap<>();
        for (Map.Entry<MethodCallKey, List<MethodCall>> entry : methodCallMap.entrySet()) {
            Double duration = entry.getValue()
                .stream()
                .limit(5)
                .collect(Collectors.averagingLong(MethodCall::getDuration));
            collected.put(entry.getKey().toString(), duration);
        }
        return collected;
    }

    @Override
    public Map<String, Long> getAmountMethodCallsOfLastHour() {
        LocalDateTime now = LocalDateTime.now();
        final Map<String, Long> collected = new HashMap<>();
        for (Map.Entry<MethodCallKey, List<MethodCall>> entry : methodCallMap.entrySet()) {
            Long calls = entry.getValue()
                .stream()
                .filter(methodCall -> methodCall.getTime().plusHours(1).isAfter(now))
                .count();
            collected.put(entry.getKey().toString(), calls);
        }
        return collected;
    }

    @Override
    public void registerMethodCall(Class clazz, String methodName, Class<?>[] parameterTypes, long duration) {
        MethodCallKey key = new MethodCallKey(clazz, methodName, parameterTypes);
        List<MethodCall> methodCalls = methodCallMap.getOrDefault(key, new ArrayList<>());
        methodCalls.add(new MethodCall(LocalDateTime.now(), duration));
        methodCallMap.putIfAbsent(key, methodCalls);
    }

    private static class MethodCallKey {
        private final Class clazz;
        private final String methodName;
        private final Class<?>[] parameterTypes;

        public MethodCallKey(Class clazz, String methodName, Class<?>[] parameterTypes) {
            this.clazz = clazz;
            this.methodName = methodName;
            this.parameterTypes = parameterTypes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MethodCallKey)) return false;
            MethodCallKey that = (MethodCallKey) o;
            return Objects.equals(clazz, that.clazz) &&
                Objects.equals(methodName, that.methodName) &&
                Objects.equals(parameterTypes, that.parameterTypes);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clazz, methodName, parameterTypes);
        }

        @Override
        public String toString() {
            return clazz.getName() + "." + methodName + "(" + Arrays.toString(parameterTypes) + ")";
        }
    }

    private class MethodCall {
        private final LocalDateTime time;
        private final Long duration;

        public MethodCall(LocalDateTime time, Long duration) {
            this.time = time;
            this.duration = duration;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public Long getDuration() {
            return duration;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MethodCall)) return false;
            MethodCall that = (MethodCall) o;
            return Objects.equals(time, that.time) &&
                Objects.equals(duration, that.duration);
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, duration);
        }
    }
}
