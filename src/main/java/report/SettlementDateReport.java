package report;

import instruction.BuySell;
import instruction.SettledInstruction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SettlementDateReport {

    public Map<LocalDate, BigDecimal> calculateSettledPriceByDates(
            List<SettledInstruction> settledInstructions,
            BuySell buySell
    ) {
        return settledInstructions.stream()
                .filter(filterBuySell(buySell))
                .collect(Collectors.groupingBy(
                        settledInstruction -> settledInstruction.getInstruction().getSettlementDate(),
                        createSettledPriceSummer()
                ));
    }

    private Predicate<SettledInstruction> filterBuySell(BuySell buySell) {
        return settledInstruction -> buySell == settledInstruction.getInstruction().getBuySell();
    }

    private Collector<SettledInstruction, ?, BigDecimal> createSettledPriceSummer() {
        return Collectors.mapping(
                SettledInstruction::getSettledPrice,
                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    public List<SettledInstruction> calculateRankByAmount(
            List<SettledInstruction> settledInstructions,
            BuySell buySell
    ) {
        return settledInstructions.stream()
                .filter(filterBuySell(buySell))
                .sorted(Comparator.comparing(SettledInstruction::getSettledPrice).reversed())
                .collect(Collectors.toList());
    }


}
