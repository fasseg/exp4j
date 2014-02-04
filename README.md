exp4j
=====

exp4j math expression evaluator for the Java programming language, capable of evaluating simple mathematical 
expression, small in size, without any external libraries and simple to use.

check out http://www.objecthunter.net/exp4j/

What's new in this fork?
==========================

Future variable support. This means that an expression can have unsolved variables that can be later solved during evaluation.

To use it, simply do the following:

```java
FutureResolver solver = new FutureResolver() {
    @Override
    public Double resolve(String future) {
        // Do stuff and return double value
    }
};
FuturesFactory.registerFutureResolver(solver);

Calculable calc = new ExpressionBuilder(expression).withVariable("X", 12).buildWithFutures();

calc.calculate();

// When done with the solver, unregister it
FuturesFactory.unregisterFutureResolver(solver);
```

Also, there's support for dotted variable names, to support object oriented variable notation:

```java
new ExpressionBuilder("object.value").buildWithFutures()
```

So you could use Java reflection on a `FutureResolver` to access an object's fields.
