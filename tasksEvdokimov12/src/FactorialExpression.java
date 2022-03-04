import java.math.BigDecimal;
import java.math.RoundingMode;

public class FactorialExpression {

    public static BigDecimal calculateExpression(Long n) {
        if (n <= 1)
            throw new IllegalArgumentException("N is not positive integer");    //throw exception if the data isn't positive
                                                                                //and <= 1
        BigDecimal result = new BigDecimal(0);              //initialization of expression for sum
        BigDecimal factorial = new BigDecimal(1);           //initialization of factorial
        for (int step = 1; step <= n; step++) {
            factorial = factorial.multiply(BigDecimal.valueOf(step));
            result = result.add(factorial);
        }
        result = result.divide(factorial,10, RoundingMode.CEILING);
        return result;
    }
}
