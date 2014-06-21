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
package net.objecthunter.exp4j.expression;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.*;
import static org.junit.Assert.fail;

public class ConcurrentExpressionBuilderTest {
    @Test
    public void testConcurrency1() throws Exception {
        List<Runnable> threads=new ArrayList<Runnable>();

        for (int i=0;i<100;i++){
            threads.add(new EvaluationThread("sin(14)^-cos(17)",pow(sin(14),-cos(17))));
        }
        for (int i=0;i<100;i++){
            threads.add(new EvaluationThread("log(199)-floor(19.9928)",log(199) - floor(19.9928)));
        }
        for (int i=0;i<100;i++){
            threads.add(new EvaluationThread("15 * 45^3 - log(33^2) + sqrt(17.463453)",(15*45*45*45) - log(33*33) + sqrt(17.463453)));
        }
        Collections.shuffle(threads);
        for (Runnable r:threads){
            new Thread(r).start();
        }
        while (!threads.isEmpty()){
            Thread.sleep(20);
            Iterator<Runnable> it=threads.iterator();
            while(it.hasNext()){
                EvaluationThread eval=(EvaluationThread) it.next();
                if (eval.isFinished()){
                    if (!eval.isSuccess()){
                        fail("errors in evaluation!");
                    }
                    it.remove();
                }
            }
        }
    }

    static class EvaluationThread implements Runnable {
        private final String expr;
        private final double expected;
        private boolean success=false;
        private boolean finished = false;

        public synchronized boolean isSuccess(){
            return success;
        }

        public synchronized boolean isFinished(){
            return finished;
        }

        public EvaluationThread(String expr,double expected) {
            this.expr = expr;
            this.expected=expected;
        }

        public void run() {
            try {
                for (int i = 0; i < 100; i++) {
                    Expression<Double> calc = new ExpressionBuilder(expr).buildDouble();
                    if (expected == calc.evaluate()){
                        success=true;
                    }
                }
                finished=true;
            } catch (Exception e) {
                e.printStackTrace();
                finished=true;
            }
        }
    }
}
