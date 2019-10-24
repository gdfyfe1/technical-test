package parser;

import instruction.BuySell;
import instruction.Currency;
import instruction.Instruction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parser.exception.UnparsableLine;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Integer.parseInt;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;

public class InstructionParserTest {

    private static final String DATE_FORMAT_STRING = "dd MMM yyyy";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private InstructionParser testObject;
    private DateTimeFormatter dateTimeFormatter;

    @Before
    public void setUp() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING);
        testObject = new InstructionParser(dateTimeFormatter);
    }

    @Test(expected = UnparsableLine.class)
    public void parseInstructionEmptyString() throws UnparsableLine {
        testObject.parseInstruction("");
    }

    @Test(expected = UnparsableLine.class)
    public void parseInstructionNotEnoughValues() throws UnparsableLine {
        testObject.parseInstruction("a,b");
    }

    @Test(expected = UnparsableLine.class)
    public void parseInstructionTooManyValues() throws UnparsableLine {
        testObject.parseInstruction("a,b,c,d,e,f,g,h");
    }

    @Test
    public void parseInstructionInvalidBuySell() throws UnparsableLine {
        setExpectedException(IllegalArgumentException.class);

        testObject.parseInstruction("foo, invalid, 0.50, USD, 01 Jan 2016, 02 Jan 2016, 200, 100.25");
    }

    @Test
    public void parseInstructionInvalidFxRate() throws UnparsableLine {
        setExpectedException(NumberFormatException.class);

        testObject.parseInstruction("foo, B,  invalid, USD, 01 Jan 2016, 02 Jan 2016, 200, 100.25");
    }

    @Test
    public void parseInstructionInvalidCurrency() throws UnparsableLine {
        setExpectedException(IllegalArgumentException.class);

        testObject.parseInstruction("foo, B, 0.50, invalid, 01 Jan 2016, 02 Jan 2016, 200, 100.25");
    }

    @Test
    public void parseInstructionInvalidInstructionDate() throws UnparsableLine {
        setExpectedException(DateTimeException.class);

        testObject.parseInstruction("foo, B, 0.50, USD, invalid, 02 Jan 2016, 200, 100.25");
    }

    @Test
    public void parseInstructionInvalidSettlementDate() throws UnparsableLine {
        setExpectedException(DateTimeException.class);

        testObject.parseInstruction("foo, B, 0.50, USD, 01 Jan 2016, invalid, 200, 100.25");
    }

    @Test
    public void parseInstructionInvalidUnits() throws UnparsableLine {
        setExpectedException(NumberFormatException.class);

        testObject.parseInstruction("foo, B, 0.50, USD, 01 Jan 2016, 02 Jan 2016, invalid, 100.25");
    }

    @Test
    public void parseInstructionInvalidPricePerUnit() throws UnparsableLine {
        setExpectedException(NumberFormatException.class);

        testObject.parseInstruction("foo, B, 0.50, USD, 01 Jan 2016, 02 Jan 2016, 200, invalid");
    }

    @Test
    public void parseInstruction() throws UnparsableLine {
        String entity = "foo";
        String buy = "B";
        String agreedFx = "0.50";
        String currency = "USD";
        String instructionDate = "01 Jan 2016";
        String settlementDate = "02 Jan 2016";
        String units = "200";
        String pricePerUnit = "100.25";

        Instruction returnedInstruction = testObject.parseInstruction(
                String.format(
                        "%s, %s, %s, %s, %s, %s, %s, %s",
                        entity,
                        buy,
                        agreedFx,
                        currency,
                        instructionDate,
                        settlementDate,
                        units,
                        pricePerUnit
                )
        );

        assertEquals(entity, returnedInstruction.getEntity());
        assertEquals(BuySell.B, returnedInstruction.getBuySell());
        assertEquals(new BigDecimal(agreedFx), returnedInstruction.getAgreedFx());
        assertEquals(Currency.USD, returnedInstruction.getCurrency());

        LocalDate expectedInstructionDate = LocalDate.parse(instructionDate, dateTimeFormatter);
        assertEquals(expectedInstructionDate, returnedInstruction.getInstructionDate());

        LocalDate expectedSettlementDate = LocalDate.parse(settlementDate, dateTimeFormatter);
        assertEquals(expectedSettlementDate, returnedInstruction.getSettlementDate());

        assertEquals(parseInt(units), returnedInstruction.getUnits());
        assertEquals(new BigDecimal(pricePerUnit), returnedInstruction.getPricePerUnit());
    }

    private void setExpectedException(Class causeClass) {
        exception.expect(UnparsableLine.class);
        exception.expectCause(instanceOf(causeClass));
    }
}