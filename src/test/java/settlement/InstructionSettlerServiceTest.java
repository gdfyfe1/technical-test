package settlement;

import instruction.Currency;
import instruction.Instruction;
import instruction.SettledInstruction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class InstructionSettlerServiceTest {

    private InstructionSettlerService testObject;

    private LocalDate instructionDate;
    private Currency currency;
    private BigDecimal pricePerUnits;
    private int units;
    private BigDecimal agreedFx;
    private Instruction instruction;

    private LocalDate settleDate;
    private BigDecimal settlePrice;

    @Mock
    private SettlementDateService settlementDateServiceMock;

    @Mock
    private PriceCalculatorService priceCalculatorServiceMock;

    @Before
    public void setUp() {
        testObject = new InstructionSettlerService(settlementDateServiceMock, priceCalculatorServiceMock);

        instructionDate = LocalDate.of(2019, 10, 26);
        currency = Currency.USD;
        pricePerUnits = new BigDecimal(100.5);
        units = 20;
        agreedFx = new BigDecimal(1.5);

        instruction = createInstruction(instructionDate, currency, pricePerUnits, units, agreedFx);

        settleDate = LocalDate.of(2019, 10, 27);
        settlePrice = new BigDecimal(99);
    }

    @Test(expected = NullPointerException.class)
    public void createSettledInstructionNull() {
        testObject.createSettledInstruction(null);
    }

    @Test(expected = NullPointerException.class)
    public void createSettledInstruction() {
        when(settlementDateServiceMock.calculateSettlementDate(instructionDate, currency))
                .thenReturn(settleDate);

        when(priceCalculatorServiceMock.calculatePrice(pricePerUnits, units, agreedFx))
                .thenReturn(settlePrice);

        SettledInstruction settledInstruction = testObject.createSettledInstruction(instruction);

        assertNotNull(settledInstruction);
        assertEquals(settleDate, settledInstruction.getInstruction().getSettlementDate());
        assertEquals(settlePrice, settledInstruction.getSettledPrice());
    }

    private Instruction createInstruction(
            LocalDate instructionDate,
            Currency currency,
            BigDecimal pricePerUnits,
            int units,
            BigDecimal agreedFx
    ) {
        Instruction instruction = new Instruction();
        instruction.setInstructionDate(instructionDate);
        instruction.setCurrency(currency);
        instruction.setPricePerUnit(pricePerUnits);
        instruction.setUnits(units);
        instruction.setAgreedFx(agreedFx);
        return instruction;
    }
}