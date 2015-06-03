package ch.haeuslers.bookr;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DependencyMatrixTest {

    public static final String DEPENDENCY_MATRIX_CSV = "/dependencyMatrix.csv";

    @Test
    @Ignore
    public void testArchitectureLayeringConstraints() throws Exception {
        JDepend jdepend = new JDepend();
        jdepend.addDirectory("build/classes/main");

        @SuppressWarnings("unchecked")
        Collection<JavaPackage> packages = jdepend.analyze();

        DependencyMatrix matrix = new DependencyMatrix();
        InputStream inputStream = getClass().getResourceAsStream(DEPENDENCY_MATRIX_CSV);
        matrix.parse(inputStream);
        inputStream.close();

        String violations = matrix.violationReport(packages);

        if (!"[]".equals(violations)) {
            fail(violations);
        }
    }

    @Test
    @Ignore
    public void testCycles() throws Exception {
        JDepend jdepend = new JDepend();
        jdepend.addDirectory("build/classes/main");
        @SuppressWarnings("unchecked")
        Collection<JavaPackage> packages = jdepend.analyze();
        ArrayList<String> cycles = new ArrayList<>();
        for (JavaPackage p : packages) {
            if(p.containsCycle()) {
                cycles.add("Cycle exists: " + p.getName());
            }
        }
        assertTrue(String.valueOf(cycles), cycles.isEmpty());

    }
}
