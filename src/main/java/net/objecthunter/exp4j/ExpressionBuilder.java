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

import com.sun.istack.internal.NotNull;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.shuntingyard.ShuntingYard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionBuilder {
    private final String expression;
    private final Map<String, Function> customFunctions;
    private final Map<String, Operator> customOperators;

    public ExpressionBuilder(@NotNull String expression) {
        this.expression = expression;
        this.customFunctions = new HashMap<>(4);
        this.customOperators = new HashMap<>(4);
    }

    public ExpressionBuilder function(Function function) {
        this.customFunctions.put(function.getName(), function);
        return this;
    }

    public ExpressionBuilder functions(Function... functions) {
        for (Function f : functions) {
            this.customFunctions.put(f.getName(), f);
        }
        return this;
    }

    public ExpressionBuilder functions(List<Function> functions) {
        for (Function f : functions) {
            this.customFunctions.put(f.getName(), f);
        }
        return this;
    }

    public ExpressionBuilder operator(Operator operator) {
        this.customOperators.put(operator.getSymbol(), operator);
        return this;
    }

    public ExpressionBuilder operator(Operator... operators) {
        for (Operator o : operators) {
            this.customOperators.put(o.getSymbol(), o);
        }
        return this;
    }

    public ExpressionBuilder operator(List<Operator> operators) {
        for (Operator o : operators) {
            this.customOperators.put(o.getSymbol(), o);
        }
        return this;
    }

    public Expression build() throws Exp4jException {
        if (expression.isEmpty()) {
            throw new Exp4jException("The expression can not be empty");
        }
        return new Expression(ShuntingYard.convertToRPN(this.expression));
    }

}
