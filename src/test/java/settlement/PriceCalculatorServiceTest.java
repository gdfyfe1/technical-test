package settlement;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PriceCalculatorServiceTest {

    private PriceCalculatorService testObject;

    @Before
    public void setUp() {
        testObject = new PriceCalculatorService();
    }

    @Test
    public void calculatePriceZero() {
        BigDecimal returnedBigDecimal = testObject.calculatePrice(BigDecimal.ZERO, 10, BigDecimal.TEN);

        assertEquals(BigDecimal.ZERO, returnedBigDecimal);
    }

    @Test
    public void calculatePriceOne() {
        int units = 10;
        BigDecimal returnedBigDecimal = testObject.calculatePrice(BigDecimal.ONE, units, BigDecimal.ONE);

        assertEquals(BigDecimal.valueOf(units), returnedBigDecimal);
    }

    @Test
    public void calculatePrice() {
        int units = 10;
        BigDecimal returnedBigDecimal = testObject.calculatePrice(BigDecimal.TEN, units, BigDecimal.TEN);

        BigDecimal expectedValue = BigDecimal.valueOf(1000);
        assertEquals(expectedValue, returnedBigDecimal);
    }
}