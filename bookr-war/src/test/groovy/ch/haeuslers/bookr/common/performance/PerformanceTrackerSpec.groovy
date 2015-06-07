package ch.haeuslers.bookr.common.performance

import spock.lang.Specification

class PerformanceTrackerSpec extends Specification {

    def "two method calls catched"() {
        setup:
        def tracker = new PerformanceTracker();

        when:
        tracker.registerMethodCall(PerformanceTrackerSpec.class, 'theMethod', new Class[0], 12);
        tracker.registerMethodCall(PerformanceTrackerSpec.class, 'theMethod', new Class[0], 14);

        then:
        def methodCallKey = 'ch.haeuslers.bookr.common.performance.PerformanceTrackerSpec.theMethod()'
        tracker.amountMethodCallsOfLastHour.get(methodCallKey) == 2L
        tracker.averageOfFiveMethodDurations.get(methodCallKey) == 13.0d
    }
}