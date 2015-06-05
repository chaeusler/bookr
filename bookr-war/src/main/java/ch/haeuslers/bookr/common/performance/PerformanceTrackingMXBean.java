package ch.haeuslers.bookr.common.performance;

import java.util.Map;

public interface PerformanceTrackingMXBean {
    Map<String, Double> getAverageOfFiveMethodDurations();
    Map<String, Long> getAmountMethodCallsOfLastHour();

}
