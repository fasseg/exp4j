package de.congrace.exp4j;

import java.util.LinkedList;
import java.util.ListIterator;

public class FuturesFactory {

	private static LinkedList<FutureResolver> resolvers = new LinkedList<FutureResolver>();
	
	public static void registerFutureResolver(FutureResolver futRes) {
		resolvers.add(futRes);
	}
	
	public static void unregisterFutureResolver(FutureResolver futRes) {
		resolvers.remove(futRes);
	}
	
	public static Double obtainFutureValue(String value) {
		ListIterator<FutureResolver> lst = resolvers.listIterator();
		
		while (lst.hasNext()) {
			FutureResolver res = lst.next();
			
			Double solution = res.resolve(value);
			
			if (solution != null) {
				return solution;
			}
		}

		return null;
	}

}
