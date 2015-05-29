package ch.haeuslers.bookr;

import jdepend.framework.JavaPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class DependencyMatrix {
    private static final String COL_SEPARATOR = ",";
    private static final String NO_DEP_ALLOWED = "no";

    private final List<InvalidOutgoingDependencyPattern> invalidDependencies = new LinkedList<>();


    public void parse(InputStream inputStream) {
        try {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(inputStream))) {
                parse(r);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void parse(BufferedReader r) throws IOException {
        LinkedList<String> outgoingDependencies = readColumns(r.readLine());
        outgoingDependencies.removeFirst();

        for (String line = r.readLine(); line != null; line = r.readLine()) {
            LinkedList<String> columnValues = readColumns(line);
            String dependentPackage = columnValues.removeFirst();

            for (int i = 0; i < columnValues.size(); i++) {
                if (NO_DEP_ALLOWED.equals(columnValues.get(i))) {
                    addInvalidOutgoingDependencyPattern(dependentPackage, outgoingDependencies.get(i));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String violationReport(Collection<JavaPackage> packages) {
        Set<String> violations = new HashSet<>();

        for (JavaPackage dependentPackage : packages) {
            for (InvalidOutgoingDependencyPattern pattern : patternsForDependencPackage(dependentPackage)) {
                violations.addAll(((Collection<JavaPackage>) dependentPackage.getEfferents())
                    .stream()
                    .filter(pattern::outgoingDependencyPatternMatch)
                    .map(outgoingDependency -> "Violation: " + pattern.getDependentPackagePattern() + " is dependent on " + outgoingDependency.getName() + "\n")
                    .collect(Collectors.toList()));
            }
        }

        return violations.toString();
    }

    private List<InvalidOutgoingDependencyPattern> patternsForDependencPackage(JavaPackage dependentPackage) {
        return invalidDependencies
            .stream()
            .filter(pattern -> pattern.dependentPackagePatternMatch(dependentPackage))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private void addInvalidOutgoingDependencyPattern(String dependentPackage, String outgoingDependency) {
        invalidDependencies.add(new InvalidOutgoingDependencyPattern(dependentPackage, outgoingDependency));
    }

    private LinkedList<String> readColumns(String line) {
        LinkedList<String> columnValues = new LinkedList<>();
        StringTokenizer st = new StringTokenizer(line, COL_SEPARATOR);
        while (st.hasMoreTokens()) {
            String columnValue = st.nextToken();
            columnValues.add(columnValue.trim());
        }
        return columnValues;
    }

    public List<InvalidOutgoingDependencyPattern> getInvalidDependencies() {
        return invalidDependencies;
    }

    public static class InvalidOutgoingDependencyPattern {
        private final String dependentPackagePattern;
        private final String outgoingDependencyPattern;

        /**
         * @param dependentPackagePattern like "com.acme" or "com.acme.*"
         * @param outgoingDependencyPattern like "com.acme" or "com.acme.*"
         */
        public InvalidOutgoingDependencyPattern(String dependentPackagePattern, String outgoingDependencyPattern) {
            this.dependentPackagePattern = dependentPackagePattern;
            this.outgoingDependencyPattern = outgoingDependencyPattern;
        }

        public boolean dependentPackagePatternMatch(JavaPackage dependentPackage) {
            return match(dependentPackagePattern, dependentPackage);
        }

        public boolean outgoingDependencyPatternMatch(JavaPackage outgoingDependency) {
            return match(outgoingDependencyPattern, outgoingDependency);
        }

        public String getDependentPackagePattern() {
            return dependentPackagePattern;
        }

        public String getOutgoingDependencyPattern() {
            return outgoingDependencyPattern;
        }

        public static boolean match(String pattern, JavaPackage javaPackage) {
            if (pattern.endsWith(".*")) {
                return javaPackage.getName().startsWith(pattern.substring(0, pattern.length() - 1))
                        || javaPackage.getName().equals(pattern.substring(0, pattern.length() - 2));
            }
            else {
                return javaPackage.getName().equals(pattern);
            }
        }
    }
}
