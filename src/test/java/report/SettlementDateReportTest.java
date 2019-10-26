package report;

import instruction.BuySell;
import instruction.Instruction;
import instruction.SettledInstruction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SettlementDateReportTest {

    private LocalDate localDateA;
    private LocalDate localDateB;

    private SettledInstruction settledInstructionBuyA;
    private SettledInstruction settledInstructionBuyB;
    private SettledInstruction settledInstructionBuyC;
    private SettledInstruction settledInstructionSellA;
    private SettledInstruction settledInstructionSellB;
    private SettledInstruction settledInstructionSellC;

    private List<SettledInstruction> settledSellInstructions;
    private List<SettledInstruction> settledBuyInstructions;
    private List<SettledInstruction> settledAllInstructions;

    private SettlementDateReport testObject;

    @Before
    public void setUp() {
        testObject = new SettlementDateReport();

        localDateA = LocalDate.of(2019, 5, 5);
        localDateB = LocalDate.of(2019, 5, 6);

        setUpInstructions();
    }

    private void setUpInstructions() {
        settledInstructionBuyA = new SettledInstruction(createInstruction(BuySell.B, localDateA), new BigDecimal(101));
        settledInstructionBuyB = new SettledInstruction(createInstruction(BuySell.B, localDateB), new BigDecimal(202));
        settledInstructionBuyC = new SettledInstruction(createInstruction(BuySell.B, localDateB), new BigDecimal(303));
        settledInstructionSellA = new SettledInstruction(createInstruction(BuySell.S, localDateA), new BigDecimal(11));
        settledInstructionSellB = new SettledInstruction(createInstruction(BuySell.S, localDateB), new BigDecimal(22));
        settledInstructionSellC = new SettledInstruction(createInstruction(BuySell.S, localDateB), new BigDecimal(33));

        settledSellInstructions = asList(
                settledInstructionSellA,
                settledInstructionSellB,
                settledInstructionSellC
        );

        settledBuyInstructions = asList(
                settledInstructionBuyA,
                settledInstructionBuyB,
                settledInstructionBuyC
        );

        settledAllInstructions = new ArrayList<>();
        settledAllInstructions.addAll(settledBuyInstructions);
        settledAllInstructions.addAll(settledSellInstructions);
    }

    @Test
    public void calculateSettledPriceByDatesEmpty() {
        Map<LocalDate, BigDecimal> returnedMap = testObject.calculateSettledPriceByDates(emptyList(), BuySell.B);

        assertTrue(returnedMap.isEmpty());
    }

    @Test
    public void calculateSettledPriceByDatesNoBuy() {
        Map<LocalDate, BigDecimal> returnedMap =
                testObject.calculateSettledPriceByDates(settledSellInstructions, BuySell.B);

        assertTrue(returnedMap.isEmpty());
    }

    @Test
    public void calculateSettledPriceByDatesNoSell() {
        Map<LocalDate, BigDecimal> returnedMap =
                testObject.calculateSettledPriceByDates(settledBuyInstructions, BuySell.S);

        assertTrue(returnedMap.isEmpty());
    }

    @Test
    public void calculateSettledPriceByDatesBuy() {
        Map<LocalDate, BigDecimal> returnedMap =
                testObject.calculateSettledPriceByDates(settledAllInstructions, BuySell.B);

        int expectedNumberOfEntries = 2;
        assertEquals(expectedNumberOfEntries, returnedMap.size());

        assertTotalForDate(returnedMap, localDateA, 101);
        assertTotalForDate(returnedMap, localDateB, 505);
    }

    @Test
    public void calculateSettledPriceByDatesSell() {
        Map<LocalDate, BigDecimal> returnedMap =
                testObject.calculateSettledPriceByDates(settledAllInstructions, BuySell.S);

        int expectedNumberOfEntries = 2;
        assertEquals(expectedNumberOfEntries, returnedMap.size());

        assertTotalForDate(returnedMap, localDateA, 11);
        assertTotalForDate(returnedMap, localDateB, 55);
    }

    @Test
    public void calculateRankByAmountEmpty() {
        List<SettledInstruction> returnedList = testObject.calculateRankByAmount(emptyList(), BuySell.B);

        assertTrue(returnedList.isEmpty());
    }

    @Test
    public void calculateRankByAmountBuy() {
        List<SettledInstruction> returnedList = testObject.calculateRankByAmount(settledAllInstructions, BuySell.S);

        int expectedSize = 3;
        assertEquals(expectedSize, returnedList.size());

        assertTopInstructionPrice(returnedList, 33);
    }

    @Test
    public void calculateRankByAmountSell() {
        List<SettledInstruction> returnedList = testObject.calculateRankByAmount(settledAllInstructions, BuySell.B);

        int expectedSize = 3;
        assertEquals(expectedSize, returnedList.size());

        assertTopInstructionPrice(returnedList, 303);
    }

    private void assertTopInstructionPrice(List<SettledInstruction> returnedList, int price) {
        SettledInstruction topInstruction = returnedList.get(0);
        assertEquals(topInstruction.getSettledPrice(), new BigDecimal(price));
    }

    private void assertTotalForDate(Map<LocalDate, BigDecimal> returnedMap, LocalDate localDateA, int i) {
        assertTrue(returnedMap.containsKey(localDateA));
        BigDecimal expectedTotalDateA = new BigDecimal(i);
        assertEquals(expectedTotalDateA, returnedMap.get(localDateA));
    }

    private Instruction createInstruction(BuySell buySell, LocalDate settleDate) {
        Instruction instruction = new Instruction();
        instruction.setBuySell(buySell);
        instruction.setSettlementDate(settleDate);
        return instruction;
    }
}