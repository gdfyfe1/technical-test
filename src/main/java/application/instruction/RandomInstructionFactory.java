package application.instruction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Random;
import java.util.function.Supplier;

public class RandomInstructionFactory implements Supplier<Instruction> {

    private final Random random;

    public RandomInstructionFactory() {
        this.random = new Random();
    }

    @Override
    public Instruction get() {
        Instruction instruction = new Instruction();

        instruction.setEntity("entity");
        instruction.setBuySell(randomEnum(BuySell.class));
        instruction.setAgreedFx(randomBigDecimal(1.0, 0.5));
        instruction.setCurrency(randomEnum(Currency.class));

        LocalDate randomDate = createRandomDate();
        instruction.setInstructionDate(randomDate);
        instruction.setSettlementDate(randomDate.plusDays(random.nextInt(5)));
        instruction.setUnits(random.nextInt(1000) + 100);
        instruction.setPricePerUnit(randomBigDecimal(200, 100));

        return instruction;
    }

    private LocalDate createRandomDate() {
        return LocalDate.of(2019, random.nextInt(12) + 1, random.nextInt(28) + 1);
    }

    private <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    private BigDecimal randomBigDecimal(double midValue, double range) {
        return new BigDecimal(random.nextDouble() * midValue + range)
                .setScale(2, RoundingMode.FLOOR);
    }
}
