import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

/**
 *
 * @author Leo Gutierrez R. <leogutierrezramirez@gmail.com>
 */
public class Expr4Main {

    public static void main(String[] args) {
        /*Function logb = new Function("logb", 2) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(args[1]);
            }
        };*/
        double result = new ExpressionBuilder("pow(2, 3)")
                // .function(logb)
                .build()
                .evaluate();

        System.out.println(result);

    }
}
