package de.congrace.exp4j;

import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

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
					Calculable calc = new ExpressionBuilder(expr).build();
					if (expected == calc.calculate()){
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
