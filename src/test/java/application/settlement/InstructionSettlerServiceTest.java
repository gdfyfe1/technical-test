package application.settlement;

import application.instruction.Currency;
import application.instruction.Instruction;
import application.instruction.SettledInstruction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class InstructionSettlerServiceTest {

    private InstructionSettlerService testObject;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private Currency currency;
    private BigDecimal pricePerUnits;
    private int units;
    private BigDecimal agreedFx;
    private Instruction instruction;
    private LocalDate settleDate;
    private BigDecimal settlePrice;
    private LocalDate newSettleDate;

    @Mock
    private SettlementDateService settlementDateServiceMock;

    @Mock
    private PriceCalculatorService priceCalculatorServiceMock;

    @Before
    public void setUp() {
        testObject = new InstructionSettlerService(settlementDateServiceMock, priceCalculatorServiceMock);

        settleDate = LocalDate.of(2019, 10, 26);
        currency = Currency.USD;
        pricePerUnits = new BigDecimal(100.5);
        units = 20;
        agreedFx = new BigDecimal(1.5);

        instruction = createInstruction(settleDate, currency, pricePerUnits, units, agreedFx);

        newSettleDate = LocalDate.of(2019, 10, 27);
        settlePrice = new BigDecimal(99);
    }

    @Test(expected = NullPointerException.class)
    public void createSettledInstructionNull() {
        testObject.settleInstruction(null);
    }

    @Test
    public void createSettledInstruction() {
        when(settlementDateServiceMock.calculateSettlementDate(settleDate, currency))
                .thenReturn(newSettleDate);

        when(priceCalculatorServiceMock.calculatePrice(instruction))
                .thenReturn(settlePrice);

        SettledInstruction settledInstruction = testObject.settleInstruction(instruction);

        assertNotNull(settledInstruction);
        assertEquals(newSettleDate, settledInstruction.getInstruction().getSettlementDate());
        assertEquals(settlePrice, settledInstruction.getSettledPriceUsd());
    }

    private Instruction createInstruction(
            LocalDate settleDate,
            Currency currency,
            BigDecimal pricePerUnits,
            int units,
            BigDecimal agreedFx
    ) {
        Instruction instruction = new Instruction();
        instruction.setSettlementDate(settleDate);
        instruction.setCurrency(currency);
        instruction.setPricePerUnit(pricePerUnits);
        instruction.setUnits(units);
        instruction.setAgreedFx(agreedFx);
        return instruction;
    }
}