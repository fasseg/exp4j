/* 
* Copyright 2014 Frank Asseg
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License. 
*/
package net.objecthunter.exp4j;

import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class ConcurrencyTests {

    @Test
    public void testFutureEvaluation() throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        int numTests = 10000;
        double[] correct1 = new double[numTests];
        Future[] results1 = new Future[numTests];

        double[] correct2 = new double[numTests];
        Future[] results2 = new Future[numTests];

        for (int i = 0; i < numTests;i++) {
            correct1[i] = Math.sin(2*Math.PI/(i+1));
            results1[i] = new ExpressionBuilder("sin(2pi/(n+1))")
                    .variables("pi", "n")
                    .build()
                    .setVariable("pi",Math.PI)
                    .setVariable("n", i)
                    .evaluateAsync(exec);

            correct2[i] = Math.log(Math.E * Math.PI * (i+1));
            results2[i] = new ExpressionBuilder("log(epi(n+1))")
                    .variables("pi", "n", "e")
                    .build()
                    .setVariable("pi",Math.PI)
                    .setVariable("e", Math.E)
                    .setVariable("n", i)
                    .evaluateAsync(exec);
        }

        for (int i = 0; i< numTests;i++) {
            assertEquals(correct1[i], (Double) results1[i].get(), 0d);
            assertEquals(correct2[i], (Double) results2[i].get(), 0d);
        }
    }

    @Test
    public void evaluateFamily() throws Exception {
        final Expression e = new ExpressionBuilder("sin(x)")
                .variable("x")
                .build();

        final ExecutorService executor = Executors.newFixedThreadPool(100);

        final List<Double[]> results = new CopyOnWriteArrayList<Double[]>();

        for (int i = 0 ; i < 100000; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    final double x = Math.random();
                    final double expected = Math.sin(x);
                    e.setVariable("x", x);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    results.add(new Double[] {x, e.evaluate()});
                }
            });
        }
        executor.shutdown();
        while (!executor.isShutdown()) {
            Thread.sleep(50);
        }
        for (Double[] result : results) {
            assertEquals(result[0], result[1], 0d);
        }
    }

}
