package application.settlement;

import application.instruction.Currency;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Map;

@Service
public class CurrencyToFirstDayService {

    private final DayOfWeek defaultFirstDayOfWeek;

    private final Map<Currency, DayOfWeek> firstDayOfWeekByCurrency;

    public CurrencyToFirstDayService(DayOfWeek defaultFirstDayOfWeek, Map<Currency, DayOfWeek> firstDayOfWeekByCurrency) {
        this.defaultFirstDayOfWeek = defaultFirstDayOfWeek;
        this.firstDayOfWeekByCurrency = firstDayOfWeekByCurrency;
    }

    public DayOfWeek getFirstDayOfTheWeekByCurrency(Currency currency) {
        return firstDayOfWeekByCurrency.getOrDefault(currency, defaultFirstDayOfWeek);
    }

}
