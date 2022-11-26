package net.objecthunter.exp4j.function;

import java.util.concurrent.atomic.AtomicInteger;

public class Factorial {
    public static double factorial(final double x) {
        if (x >= 0 && Math.floor(x) == x) {
            long n = (long) x;
            if (x == 1) {
                return 1;
            }
            int p = 1, r = 1, h = 0, shift = 0, high = 1;
            int log2 = floorLog2(n);
            final AtomicInteger current = new AtomicInteger(1);
            while (h != n) {
                shift += h;
                h = h >> log2--;
                int len = high;
                high = (h - 1) | 1;
                len = (high - len) / 2;
                if (len > 0) {
                    p *= product(len, current);
                    r *= p;
                }
            }
            return r << shift;
        }
        throw new FunctionException(String.format("Unable to calculate factorial of a non natural number %d", x));
    }

    private static int product(int n, final AtomicInteger current) {
        int m = n/2;
        if (m == 0) {
            return current.addAndGet(2);
        }
        if (n == 2) {
            return current.addAndGet(2) * current.addAndGet(2);
        }
        return product(n -m, current);
    }

    public static int floorLog2(long n) {
        if (n < 1) {
            throw new RuntimeException(String.format("Unable to calculate floor log2: Integer %d can not be smaller than 1"));
        }
        int i = 0;
        while (n >= (2 << i)) {
            i++;
        }
        return i;
    }
}


