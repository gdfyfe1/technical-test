package application.settlement;

import application.instruction.Instruction;
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
        Instruction instruction = createInstruction(BigDecimal.ZERO, 10, BigDecimal.TEN);
        BigDecimal returnedBigDecimal = testObject.calculatePrice(instruction);

        assertEquals(BigDecimal.ZERO, returnedBigDecimal);
    }

    @Test
    public void calculatePriceOne() {
        int units = 10;
        Instruction instruction = createInstruction(BigDecimal.ONE, units, BigDecimal.ONE);
        BigDecimal returnedBigDecimal = testObject.calculatePrice(instruction);

        assertEquals(BigDecimal.valueOf(units), returnedBigDecimal);
    }

    @Test
    public void calculatePrice() {
        int units = 10;
        Instruction instruction = createInstruction(BigDecimal.TEN, units, BigDecimal.TEN);
        BigDecimal returnedBigDecimal = testObject.calculatePrice(instruction);

        BigDecimal expectedValue = BigDecimal.valueOf(1000);
        assertEquals(expectedValue, returnedBigDecimal);
    }

    private Instruction createInstruction(BigDecimal agreedFx, int units, BigDecimal pricePerUnits) {
        Instruction instruction = new Instruction();
        instruction.setAgreedFx(agreedFx);
        instruction.setUnits(units);
        instruction.setPricePerUnit(pricePerUnits);
        return instruction;
    }
}