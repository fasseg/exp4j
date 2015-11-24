package net.objecthunter.exp4j;

import org.junit.Test;

import java.util.Random;

public class MemoryUsageTest {

    private static final long TIMEOUT = 1000L;

    @Test
    public void testBenches() throws Exception {
        final Random rnd = new Random(0xCAFEBABE);
        gc();
        final Expression expression = new ExpressionBuilder("log(x) - y * (sqrt(x^cos(y))) + pow(x, y)")
                .variables("x", "y")
                .build();

        final long endTime = System.currentTimeMillis() + TIMEOUT;
        final Runtime runtime = Runtime.getRuntime();
        long usedMemory = 0;
        while (System.currentTimeMillis() < endTime) {
            final long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
            expression.setVariable("x", rnd.nextDouble());
            expression.setVariable("y", rnd.nextDouble());
            expression.evaluate();
            final long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
            usedMemory += Math.max(0, usedMemoryAfter - usedMemoryBefore);
        }
        System.out.println("+------------------------+");
        System.out.println("Total memory used: " + (usedMemory / 1024L / 1024L) + "Mb");
        System.out.println("+------------------------+");
    }

    private void gc() {
        System.gc();
        System.gc();
    }
}
