package settlement;

import instruction.Currency;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.next;

@Service
public class SettlementDateService {

    private final CurrencyToFirstDayService currencyToFirstDayService;

    public SettlementDateService(CurrencyToFirstDayService currencyToFirstDayService) {
        this.currencyToFirstDayService = currencyToFirstDayService;
    }

    public LocalDate calculateSettlementDate(LocalDate localDate, Currency currency) {
        DayOfWeek firstDayOfWeek = currencyToFirstDayService.getFirstDayOfTheWeekByCurrency(currency);
        return isWeekend(localDate, firstDayOfWeek) ? localDate.with(next(firstDayOfWeek)) : localDate;
    }

    private boolean isWeekend(LocalDate localDate, DayOfWeek firstDayOfWeek) {
        return localDate.getDayOfWeek() == firstDayOfWeek.minus(1) ||
                localDate.getDayOfWeek() == firstDayOfWeek.minus(2);
    }
}
