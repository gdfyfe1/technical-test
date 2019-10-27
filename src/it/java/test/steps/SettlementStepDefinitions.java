package test.steps;

import application.instruction.BuySell;
import application.instruction.Currency;
import application.instruction.Instruction;
import application.instruction.SettledInstruction;
import application.settlement.InstructionSettlerService;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import test.SpringIntegrationTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SettlementStepDefinitions extends SpringIntegrationTest {

    @Autowired
    private InstructionSettlerService settlerService;

    private Map<String, LocalDate> dateMap;

    private Instruction instruction;
    private SettledInstruction settledInstruction;

    @Before
    public void setUp() {
        dateMap = new HashMap<>();
        dateMap.put("Monday", LocalDate.of(2019, 10, 21));
        dateMap.put("Tuesday", LocalDate.of(2019, 10, 22));
        dateMap.put("Wednesday", LocalDate.of(2019, 10, 23));
        dateMap.put("Thursday", LocalDate.of(2019, 10, 24));
        dateMap.put("Friday", LocalDate.of(2019, 10, 25));
        dateMap.put("Saturday", LocalDate.of(2019, 10, 26));
        dateMap.put("Sunday", LocalDate.of(2019, 10, 27));
        dateMap.put("NextMonday", LocalDate.of(2019, 10, 28));

        instruction = createInstruction();
    }

    @Given("^an instruction with (.*) settlement date and a currency of (.*)$")
    public void setUpInstructionDate(String day, Currency currency) {
        assertTrue(dateMap.containsKey(day));
        instruction.setSettlementDate(dateMap.get(day));
        instruction.setCurrency(currency);
    }

    @Given("^an instruction with (.*) fx rate, (.*) pricePerUnit and a (.*) units$")
    public void setUpInstructionPrice(BigDecimal fxRate, BigDecimal pricePerUnit, int units) {
        instruction.setAgreedFx(fxRate);
        instruction.setPricePerUnit(pricePerUnit);
        instruction.setUnits(units);
    }

    @When("^the instruction is settled$")
    public void settleInstruction() {
        settledInstruction = settlerService.settleInstruction(instruction);
    }

    @Then("^the new settlement date will be (.*)$")
    public void assertNewSettlementDate(String day) {
        assertTrue(dateMap.containsKey(day));
        LocalDate expectedDate = dateMap.get(day);
        assertEquals(expectedDate, settledInstruction.getInstruction().getSettlementDate());
    }

    @Then("^the new settlement price will be (.*)$")
    public void assertNewSettlementDate(BigDecimal expectedPrice) {
        expectedPrice = expectedPrice.setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal actualPrice = settledInstruction.getSettledPriceUsd()
                .setScale(2, BigDecimal.ROUND_DOWN);

        assertEquals(expectedPrice, actualPrice);
    }

    private Instruction createInstruction() {
        Instruction instruction = new Instruction();
        instruction.setEntity("entity");
        instruction.setBuySell(BuySell.B);
        instruction.setAgreedFx(BigDecimal.valueOf(1.0));
        instruction.setCurrency(Currency.USD);
        instruction.setInstructionDate(LocalDate.of(2019, 1, 1));
        instruction.setSettlementDate(LocalDate.of(2019, 1, 1));
        instruction.setUnits(200);
        instruction.setPricePerUnit(BigDecimal.valueOf(1.0));
        return instruction;
    }
}
