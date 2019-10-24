package settlement;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.next;

public class SettlementDateCalculator {

    private final DayOfWeek firstDayOfWeek;

    public SettlementDateCalculator(DayOfWeek firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public LocalDate calculateSettlementDate(LocalDate localDate) {
        return isWeekend(localDate) ? localDate.with(next(firstDayOfWeek)) : localDate;
    }

    private boolean isWeekend(LocalDate localDate) {
        return localDate.getDayOfWeek() == firstDayOfWeek.minus(1) ||
                localDate.getDayOfWeek() == firstDayOfWeek.minus(2);
    }
}
