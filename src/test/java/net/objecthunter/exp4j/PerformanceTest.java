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

import java.util.Formatter;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

public class PerformanceTest {

    private static final long BENCH_TIME = 2l;
    private static final String EXPRESSION = "log(x) - y * (sqrt(x^cos(y)))";

    @Test
    public void testBenches() throws Exception {
        StringBuffer sb = new StringBuffer();
        Formatter fmt = new Formatter(sb);
        fmt.format("+------------------------+---------------------------+--------------------------+%n");
        fmt.format("| %-22s | %-25s | %-24s |%n", "Implementation", "Calculations per Second", "Percentage of Math");
        fmt.format("+------------------------+---------------------------+--------------------------+%n");
        System.out.print(sb.toString());
        sb.setLength(0);

        int math = benchJavaMath();
        double mathRate = (double) math / (double) BENCH_TIME;
        fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "Java Math", mathRate, 100f);
        System.out.print(sb.toString());
        sb.setLength(0);

         int db = benchDouble();
        double dbRate = (double) db / (double) BENCH_TIME;
        fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "exp4j", dbRate, dbRate * 100 / mathRate);
        System.out.print(sb.toString());
        sb.setLength(0);

         int js = benchJavaScript();
        double jsRate = (double) js / (double) BENCH_TIME;
        fmt.format("| %-22s | %25.2f | %22.2f %% |%n", "JSR-223 (Java Script)", jsRate, jsRate * 100 / mathRate);
        fmt.format("+------------------------+---------------------------+--------------------------+%n");
        System.out.print(sb.toString());
    }

    private int benchDouble() {
        final Expression expression = new ExpressionBuilder(EXPRESSION)
                .variables("x", "y")
                .build();
        double val;
        Random rnd = new Random();
        long timeout = BENCH_TIME;
        long time = System.currentTimeMillis() + (1000 * timeout);
        int count = 0;
        while (time > System.currentTimeMillis()) {
            expression.setVariable("x", rnd.nextDouble());
            expression.setVariable("y", rnd.nextDouble());
            val = expression.evaluate();
            count++;
        }
        double rate = count / timeout;
        return count;
    }

    private int benchJavaMath() {
        long timeout = BENCH_TIME;
        long time = System.currentTimeMillis() + (1000 * timeout);
        double x, y, val, rate;
        int count = 0;
        Random rnd = new Random();
        while (time > System.currentTimeMillis()) {
            x = rnd.nextDouble();
            y = rnd.nextDouble();
            val = Math.log(x) - y * (Math.sqrt(Math.pow(x, Math.cos(y))));
            count++;
        }
        rate = count / timeout;
        return count;
    }

    private int benchJavaScript() throws Exception {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        long timeout = BENCH_TIME;
        long time = System.currentTimeMillis() + (1000 * timeout);
        double x, y, val, rate;
        int count = 0;
        Random rnd = new Random();
        if (engine == null) {
            System.err.println("Unable to instantiate javascript engine. skipping naive JS bench.");
            return -1;
        } else {
            time = System.currentTimeMillis() + (1000 * timeout);
            count = 0;
            while (time > System.currentTimeMillis()) {
                x = rnd.nextDouble();
                y = rnd.nextDouble();
                engine.eval("Math.log(" + x + ") - " + y + "* (Math.sqrt(" + x + "^Math.cos(" + y + ")))");
                count++;
            }
            rate = count / timeout;
        }
        return count;
    }

}
