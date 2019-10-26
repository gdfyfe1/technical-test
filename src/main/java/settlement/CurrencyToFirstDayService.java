package settlement;

import instruction.Currency;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Map;

@Service
public class CurrencyToFirstDayService {

    private final DayOfWeek defaultDayOfTheWeek;

    private final Map<Currency, DayOfWeek> firstDayOfWeekByCurrency;

    public CurrencyToFirstDayService(DayOfWeek defaultDayOfTheWeek, Map<Currency, DayOfWeek> firstDayOfWeekByCurrency) {
        this.defaultDayOfTheWeek = defaultDayOfTheWeek;
        this.firstDayOfWeekByCurrency = firstDayOfWeekByCurrency;
    }

    public DayOfWeek getFirstDayOfTheWeekByCurrency(Currency currency) {
        return firstDayOfWeekByCurrency.getOrDefault(currency, defaultDayOfTheWeek);
    }

}
