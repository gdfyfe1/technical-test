package application.settlement;

import application.instruction.Currency;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CurrencyToFirstDayServiceTest {

    private CurrencyToFirstDayService testObject;

    private DayOfWeek defaultDay = DayOfWeek.FRIDAY;
    private Map<Currency, DayOfWeek> simpleCurrencyMap;

    @Before
    public void setUp() {
        simpleCurrencyMap = new HashMap<>();
        simpleCurrencyMap.put(Currency.USD, DayOfWeek.MONDAY);
        simpleCurrencyMap.put(Currency.AED, DayOfWeek.SUNDAY);

        testObject = new CurrencyToFirstDayService(defaultDay, simpleCurrencyMap);
    }

    @Test
    public void getFirstDayOfTheWeekByCurrencyNull() {
        DayOfWeek returnedDay = testObject.getFirstDayOfTheWeekByCurrency(null);

        assertEquals(defaultDay, returnedDay);
    }

    @Test
    public void getFirstDayOfTheWeekByCurrencyUnknownCurrency() {
        DayOfWeek returnedDay = testObject.getFirstDayOfTheWeekByCurrency(Currency.GPB);

        assertEquals(defaultDay, returnedDay);
    }

    @Test
    public void getFirstDayOfTheWeekByCurrencyKnownMonday() {
        Currency passedCurrency = Currency.USD;
        DayOfWeek returnedDay = testObject.getFirstDayOfTheWeekByCurrency(passedCurrency);

        DayOfWeek expectedDay = simpleCurrencyMap.get(passedCurrency);
        assertEquals(expectedDay, returnedDay);
    }

    @Test
    public void getFirstDayOfTheWeekByCurrencyKnownSunday() {
        Currency passedCurrency = Currency.AED;
        DayOfWeek returnedDay = testObject.getFirstDayOfTheWeekByCurrency(passedCurrency);

        DayOfWeek expectedDay = simpleCurrencyMap.get(passedCurrency);
        assertEquals(expectedDay, returnedDay);
    }
}