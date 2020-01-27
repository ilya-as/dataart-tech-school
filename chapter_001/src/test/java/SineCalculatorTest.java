
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SineCalculatorTest {

    @Test
    public void ifValueFromMathSinEqualsResult() {
        SineCalculator sineCalculator = new SineCalculator();
        assertThat(Math.sin(0.7), is(sineCalculator.calculateSine(0.7, 40)));
        assertThat(Math.sin(0.5), is(sineCalculator.calculateSine(0.5, 40)));
    }
}
