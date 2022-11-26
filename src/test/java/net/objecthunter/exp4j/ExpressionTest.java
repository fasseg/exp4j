package net.objecthunter.exp4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ExpressionTest {

    @Test
    public void shouldEvaluateNumber() {
        final Expression e = new Expression("1");
        assertThat(e.evaluate()).isEqualTo(1D);
    }

    @Test()
    public void shouldNotEvaluateDanglingPlus() {
        final Expression e = new Expression("1+");
        assertThatExceptionOfType(ExpressionSyntaxError.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateAddition() {
        final Expression e = new Expression("1+2");
        assertThat(e.evaluate()).isEqualTo(1D+2D);
    }

    @Test
    public void shouldEvaluateAdditionUsingDecimals() {
        final Expression e = new Expression("1.1+2.4");
        assertThat(e.evaluate()).isEqualTo(1.1D + 2.4D);
    }

    @Test
    public void shouldEvaluateSubtractionUsingDecimals() {
        final Expression e = new Expression("1.1-2.4");
        assertThat(e.evaluate()).isEqualTo(1.1D - 2.4D);
    }

    @Test
    public void shouldEvaluateSubtractionUsingNegativeDecimals() {
        final Expression e = new Expression("-1.1-2.4");
        assertThat(e.evaluate()).isEqualTo(-1.1D - 2.4D);
    }

    @Test
    public void shouldEvaluateAdditionUsingNegativeAndPositiveDecimals() {
        final Expression e = new Expression("-1.1+2.4");
        assertThat(e.evaluate()).isEqualTo(-1.1D + 2.4D);
    }

    @Test
    public void shouldEvaluateAdditionUsingNegativeDecimals() {
        final Expression e = new Expression("-1.1+-2.4");
        assertThat(e.evaluate()).isEqualTo(-1.1D + -2.4D);
    }

    @Test
    public void shouldEvaluatePositiveDecimal() {
        final Expression e = new Expression("+1.1");
        assertThat(e.evaluate()).isEqualTo(1.1D);
    }

    @Test
    public void shouldEvaluateNegativeDecimal() {
        final Expression e = new Expression("-1.1");
        assertThat(e.evaluate()).isEqualTo(-1.1D);
    }

    @Test
    public void shouldEvaluateMultiplication() {
        final Expression e = new Expression("1*2");
        assertThat(e.evaluate()).isEqualTo(1D*2D);
    }

    @Test
    public void shouldEvaluateMultiplicationUsingDecimals() {
        final Expression e = new Expression("1.1*2.4");
        assertThat(e.evaluate()).isEqualTo(1.1D * 2.4D);
    }

    @Test
    public void shouldEvaluateMultiplicationUsingNegativeAndPositiveDecimals() {
        final Expression e = new Expression("-1.1*2.4");
        assertThat(e.evaluate()).isEqualTo(-1.1D * 2.4D);
    }

    @Test
    public void shouldEvaluateMultiplicationUsingNegativeDecimals() {
        final Expression e = new Expression("-1.1*-2.4");
        assertThat(e.evaluate()).isEqualTo(-1.1D * -2.4D);
    }
    @Test
    public void shouldEvaluateDivision() {
        final Expression e = new Expression("1/2");
        assertThat(e.evaluate()).isEqualTo(1D/2D);
    }

    @Test
    public void shouldEvaluateDivisionUsingDecimals() {
        final Expression e = new Expression("1.1/2.4");
        assertThat(e.evaluate()).isEqualTo(1.1D / 2.4D);
    }

    @Test
    public void shouldEvaluateDivisionUsingNegativeAndPositiveDecimals() {
        final Expression e = new Expression("-1.1/2.4");
        assertThat(e.evaluate()).isEqualTo(-1.1D / 2.4D);
    }

    @Test
    public void shouldEvaluateDivisionUsingNegativeDecimals() {
        final Expression e = new Expression("-1.1/-2.4");
        assertThat(e.evaluate()).isEqualTo(-1.1D / -2.4D);
    }

    @Test
    public void shouldEvaluateTwoAdditions() {
        final Expression e = new Expression("1+2+3");
        assertThat(e.evaluate()).isEqualTo(1D+2D+3D);
    }

    @Test
    public void shouldEvaluateTwoSubtractions() {
        final Expression e = new Expression("1-2-3");
        assertThat(e.evaluate()).isEqualTo(1D-2D-3D);
    }

    @Test
    public void shouldEvaluateTwoMultiplications() {
        final Expression e = new Expression("2*3*4");
        assertThat(e.evaluate()).isEqualTo(2D*3D*4D);
    }

    @Test
    public void shouldEvaluateTwoDivisions() {
        final Expression e = new Expression("2/3/4");
        assertThat(e.evaluate()).isEqualTo(2D/3D/4D);
    }

    @Test
    public void shouldEvaluateAdditionAndMultiplication() {
        final Expression e = new Expression("2+3*4");
        assertThat(e.evaluate()).isEqualTo(2D+3D*4D);
    }

    @Test
    public void shouldEvaluateExponentiation() {
        final Expression e = new Expression("2^3");
        assertThat(e.evaluate()).isEqualTo(Math.pow(2D, 3D));
    }

    @Test
    public void shouldEvaluateModulo() {
        final Expression e = new Expression("3.3%2");
        assertThat(e.evaluate()).isEqualTo(3.3D % 2D);
    }

    @Test
    public void shouldEvaluateFactorial() {
        final Expression e = new Expression("3!");
        assertThat(e.evaluate()).isEqualTo(6D);
    }

    @Test
    public void shouldEvaluateSine() {
        final Expression e = new Expression("sin(0)");
        assertThat(e.evaluate()).isEqualTo(Math.sin(0D));
    }

    @Test
    public void shouldEvaluateNegativeSine() {
        final Expression e = new Expression("-sin(1.3)");
        assertThat(e.evaluate()).isEqualTo(-Math.sin(1.3D));
    }

    @Test
    public void shouldEvaluateSineOfNegativeNumber() {
        final Expression e = new Expression("sin(-1.3)");
        assertThat(e.evaluate()).isEqualTo(Math.sin(-1.3D));
    }

    @Test
    public void shouldEvaluateNegativeSineOfNegativeNumber() {
        final Expression e = new Expression("-sin(-1.3)");
        assertThat(e.evaluate()).isEqualTo(-Math.sin(-1.3D));
    }

    @Test
    public void shouldEvaluateSineOfAddition() {
        final Expression e = new Expression("sin(1+2)");
        assertThat(e.evaluate()).isEqualTo(Math.sin(1D+2D));
    }

    @Test
    public void shouldEvaluateCos() {
        final Expression e = new Expression("cos(0)");
        assertThat(e.evaluate()).isEqualTo(Math.cos(0D));
    }

    @Test
    public void shouldEvaluateTan() {
        final Expression e = new Expression("tan(1.7)");
        assertThat(e.evaluate()).isEqualTo(Math.tan(1.7D));
    }

    @Test
    public void shouldEvaluateCot() {
        final Expression e = new Expression("cot(16.43)");
        assertThat(e.evaluate()).isEqualTo(1D / Math.tan(16.43D));
    }

    @Test
    public void shouldNotEvaluateLogof0() {
        final Expression e = new Expression("log(0)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateLog() {
        final Expression e = new Expression("log(43.22232)");
        assertThat(e.evaluate()).isEqualTo(Math.log(43.22232D));
    }

    @Test
    public void shouldNotEvaluateLog2of0() {
        final Expression e = new Expression("log2(0)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateLog2() {
        final Expression e = new Expression("log2(43.22232)");
        assertThat(e.evaluate()).isEqualTo(Math.log(43.22232D) / Math.log(2D));
    }

    @Test
    public void shouldNotEvaluateLog10of0() {
        final Expression e = new Expression("log2(0)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateLog10() {
        final Expression e = new Expression("log10(43.22232)");
        assertThat(e.evaluate()).isEqualTo(Math.log10(43.22232D));
    }

    @Test
    public void shouldNotEvaluateLog1pofNegative1() {
        final Expression e = new Expression("log1p(-1)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateLog1p() {
        final Expression e = new Expression("log1p(43.22232)");
        assertThat(e.evaluate()).isEqualTo(Math.log1p(43.22232D));
    }

    @Test
    public void shouldEvaluateAbs() {
        final Expression e = new Expression("abs(43.22232)");
        assertThat(e.evaluate()).isEqualTo(Math.abs(43.22232D));
    }

    @Test
    public void shouldEvaluateAbsOfNegativeNumber() {
        final Expression e = new Expression("abs(-43.22232)");
        assertThat(e.evaluate()).isEqualTo(Math.abs(-43.22232D));
    }

    @Test
    public void shouldNotEvaluateAcosOfNumberGreaterThanOne() {
        final Expression e = new Expression("acos(1.4332)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateAcos() {
        final Expression e = new Expression("acos(0.4332)");
        assertThat(e.evaluate()).isEqualTo(Math.acos(0.4332D));
    }

    @Test
    public void shouldNotEvaluateAsinOfNumberGreaterThanOne() {
        final Expression e = new Expression("asin(1.4332)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateAsin() {
        final Expression e = new Expression("asin(0.4332)");
        assertThat(e.evaluate()).isEqualTo(Math.asin(0.4332D));
    }

    @Test
    public void shouldEvaluateAtan() {
        final Expression e = new Expression("atan(42)");
        assertThat(e.evaluate()).isEqualTo(Math.atan(42D));
    }

    @Test
    public void shouldEvaluateCubicRoot() {
        final Expression e = new Expression("cbrt(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.cbrt(7.3434D));
    }

    @Test
    public void shouldEvaluateFloor() {
        final Expression e = new Expression("floor(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.floor(7.3434D));
    }

    @Test
    public void shouldEvaluateSinh() {
        final Expression e = new Expression("sinh(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.sinh(7.3434D));
    }

    @Test
    public void shouldNotEvaluateSquareRootOfNegativeOne() {
        final Expression e = new Expression("sqrt(-1)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateSquareRoot() {
        final Expression e = new Expression("sqrt(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.sqrt(7.3434D));
    }

    @Test
    public void shouldEvaluateTanh() {
        final Expression e = new Expression("tanh(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.tanh(7.3434D));
    }

    @Test
    public void shouldEvaluateCosh() {
        final Expression e = new Expression("cosh(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.cosh(7.3434D));
    }

    @Test
    public void shouldEvaluateCeil() {
        final Expression e = new Expression("ceil(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.ceil(7.3434D));
    }

    @Test
    public void shouldEvaluatePower() {
        final Expression e = new Expression("pow(7.3434,3)");
        assertThat(e.evaluate()).isEqualTo(Math.pow(7.3434D, 3));
    }

    @Test
    public void shouldEvaluatePowerOfZeroToZero() {
        final Expression e = new Expression("pow(0, 0)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateExp() {
        final Expression e = new Expression("exp(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.exp(7.3434D));
    }

    @Test
    public void shouldEvaluateExpm1() {
        final Expression e = new Expression("expm1(7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.expm1(7.3434D));
    }

    @Test
    public void shouldEvaluateSignum() {
        final Expression e = new Expression("signum(-7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.signum(-7.3434D));
    }

    @Test
    public void shouldEvaluateCosecant() {
        final Expression e = new Expression("csc(-7.3434)");
        assertThat(e.evaluate()).isEqualTo(1d / Math.sin(-7.3434D));
    }

    @Test
    public void shouldEvaluateSecant() {
        final Expression e = new Expression("sec(-7.3434)");
        assertThat(e.evaluate()).isEqualTo(1d / Math.cos(7.3434D));
    }

    @Test
    public void shouldEvaluateHyperbolicCosecant() {
        final Expression e = new Expression("csch(-7.3434)");
        assertThat(e.evaluate()).isEqualTo(1d / Math.sinh(-7.3434D));
    }

    @Test
    public void shouldEvaluateHyperbolicSecant() {
        final Expression e = new Expression("sech(-7.3434)");
        assertThat(e.evaluate()).isEqualTo(1d / Math.cosh(7.3434D));
    }

    @Test
    public void shouldEvaluateHyperbolicCotangent() {
        final Expression e = new Expression("coth(-7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.cosh(-7.3434D) / Math.sinh(-7.3434D));
    }

    @Test
    public void shouldEvaluateLogb() {
        final Expression e = new Expression("logb(2, 7.3434)");
        assertThat(e.evaluate()).isEqualTo(Math.log(7.3434D) / Math.log(2));
    }

    @Test
    public void shouldNotEvaluateLogbOfBase0() {
        final Expression e = new Expression("logb(0, 7.3434)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldNotEvaluateLogbOf0() {
        final Expression e = new Expression("logb(2, 0)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldNotEvaluateLogbOfNegativeBase() {
        final Expression e = new Expression("logb(-2, 7.3434)");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(e::evaluate);
    }

    @Test
    public void shouldEvaluateToDegree() {
        final Expression e = new Expression("todegree(1)");
        assertThat(e.evaluate()).isEqualTo(Math.toDegrees(1D));
    }

    @Test
    public void shouldEvaluateToRadians() {
        final Expression e = new Expression("toradian(1)");
        assertThat(e.evaluate()).isEqualTo(Math.toRadians(1D));
    }



}
