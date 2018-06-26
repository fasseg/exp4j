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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class ConcurrencyTest {
	
	private static final int NUM_TESTS = 10000;

    @Test
    public void testFutureEvaluation() throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        double[] correct1 = new double[NUM_TESTS];
        final List<Future<Double>> results1 = new ArrayList<Future<Double>>(NUM_TESTS);

        double[] correct2 = new double[NUM_TESTS];
        final List<Future<Double>> results2 = new ArrayList<Future<Double>>(NUM_TESTS);

        for (int i = 0; i < NUM_TESTS; i++) {
            correct1[i] = Math.sin(2*Math.PI/(i+1));
            final Future<Double> resultExpr1 = new ExpressionBuilder("sin(2pi/(n+1))")
                    .variables("pi", "n")
                    .build()
                    .setVariable("pi",Math.PI)
                    .setVariable("n", i)
                    .evaluateAsync(exec);
            
            results1.add(resultExpr1);

            correct2[i] = Math.log(Math.E * Math.PI * (i+1));
            final Future<Double> resultExpr2 = new ExpressionBuilder("log(epi(n+1))")
                    .variables("pi", "n", "e")
                    .build()
                    .setVariable("pi",Math.PI)
                    .setVariable("e", Math.E)
                    .setVariable("n", i)
                    .evaluateAsync(exec);
            results2.add(resultExpr2);
        }

        for (int i = 0; i < NUM_TESTS; i++) {
            assertEquals(correct1[i], (Double) results1.get(i).get(), 0d);
            assertEquals(correct2[i], (Double) results2.get(i).get(), 0d);
        }
    }
}
