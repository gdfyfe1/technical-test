package parser;

import instruction.BuySell;
import instruction.Currency;
import instruction.Instruction;
import org.springframework.stereotype.Service;
import parser.exception.UnparsableLine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InstructionParser {

    private static final int numFields = 8;

    private final DateTimeFormatter dateFormatter;

    public InstructionParser(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public Instruction parseInstruction(String input) throws UnparsableLine {
        List<String> values = Stream.of(input.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        if (values.size() != 8) {
            throw new UnparsableLine(createTooManyFieldsMessage(values.size()));
        } else {
            return parserValues(values);
        }
    }

    private Instruction parserValues(List<String> values) throws UnparsableLine {
        Instruction instruction = new Instruction();
        instruction.setEntity(values.get(0));
        instruction.setBuySell(parseBuySellEnum(values.get(1)));
        instruction.setAgreedFx(parseBigDecimal(values.get(2)));
        instruction.setCurrency(parseCurrency(values.get(3)));
        instruction.setInstructionDate(parseDate(values.get(4)));
        instruction.setSettlementDate(parseDate(values.get(5)));
        instruction.setUnits(parseUnits(values.get(6)));
        instruction.setPricePerUnit(parseBigDecimal(values.get(7)));
        return instruction;
    }

    private BuySell parseBuySellEnum(String value) throws UnparsableLine {
        try {
            return BuySell.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new UnparsableLine(String.format("Could not parse %s to buy/sell.", value), e);
        }
    }

    private BigDecimal parseBigDecimal(String value) throws UnparsableLine {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new UnparsableLine(String.format("Could not parse %s to big decimal.", value), e);
        }
    }

    private Currency parseCurrency(String currency) throws UnparsableLine {
        try {
            return Currency.valueOf(currency);
        } catch (IllegalArgumentException e) {
            throw new UnparsableLine(String.format("Could not parse %s to buy/sell.", currency), e);
        }
    }

    private LocalDate parseDate(String date) throws UnparsableLine {
        try {
            return LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new UnparsableLine(String.format("Could not parse %s into a date.", date), e);
        }
    }

    private int parseUnits(String units) throws UnparsableLine {
        try {
            return Integer.parseInt(units);
        } catch (NumberFormatException e) {
            throw new UnparsableLine(String.format("Could not parse %s into an integer.", units), e);
        }
    }

    private String createTooManyFieldsMessage(int numValues) {
        return String.format(
                "Input line had %s comma seperated values. %s were expected.",
                numValues,
                numFields
        );
    }
}
