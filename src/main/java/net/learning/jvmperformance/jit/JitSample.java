package net.learning.jvmperformance.jit;

/**
 * Created by Xuesong Mei on 10/11/15.
 */
public class JitSample {
    public static void main(String[] args) {
        for (int outer = 1; outer <= 100; outer++) {
            long start = System.nanoTime();
            testPerformance();
            long duration = System.nanoTime() - start;
            System.out.println("Loop # " + outer + " took " + ((duration) / 1000.0d) + " µs");
        }
    }

    private static void testPerformance() {
        long sum = 0;
        for (int i = 1; i <= 5000; i++) {
            sum = sum + doSomething(i);
        }
    }

    private static int doSomething(int i) {
        int x = (int) (i * 2.3d / 2.7d);
        int y = (int) (i * 2.36d);
        return x % y;
    }
}