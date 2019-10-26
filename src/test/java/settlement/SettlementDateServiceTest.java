package settlement;

import instruction.Currency;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SettlementDateServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private LocalDate localDateMonday;
    private LocalDate localDateTueday;
    private LocalDate localDateSaturday;
    private LocalDate localDateSunday;
    private SettlementDateService testObject;
    @Mock
    private CurrencyToFirstDayService currencyToFirstDayMock;

    @Captor
    private ArgumentCaptor<Currency> currencyCaptor;

    @Before
    public void setUp() {
        localDateSaturday = LocalDate.of(2019, 1, 5);
        localDateSunday = LocalDate.of(2019, 1, 6);
        localDateMonday = LocalDate.of(2019, 1, 7);
        localDateTueday = LocalDate.of(2019, 1, 8);
        testObject = new SettlementDateService(currencyToFirstDayMock);
    }

    @Test
    public void calculateSettlementDateDifferentWeekday() {
        mockDayOfTheWeekService(Currency.USD, DayOfWeek.MONDAY);

        LocalDate returnedDate = testObject.calculateSettlementDate(localDateTueday, Currency.USD);

        verifyCurrencyServiceUsage(Currency.USD);
        assertEquals(localDateTueday, returnedDate);
    }

    @Test
    public void calculateSettlementDateStartOfWeek() {
        mockDayOfTheWeekService(Currency.USD, DayOfWeek.MONDAY);

        LocalDate returnedDate = testObject.calculateSettlementDate(localDateMonday, Currency.USD);

        verifyCurrencyServiceUsage(Currency.USD);
        assertEquals(localDateMonday, returnedDate);
    }

    @Test
    public void calculateSettlementDateWeekend() {
        mockDayOfTheWeekService(Currency.USD, DayOfWeek.MONDAY);

        LocalDate returnedDate = testObject.calculateSettlementDate(localDateSunday, Currency.USD);

        verifyCurrencyServiceUsage(Currency.USD);
        assertEquals(localDateMonday, returnedDate);
    }

    @Test
    public void calculateSettlementDateDifferentFirstDay() {
        mockDayOfTheWeekService(Currency.AED, DayOfWeek.SUNDAY);

        LocalDate returnedDate = testObject.calculateSettlementDate(localDateSaturday, Currency.AED);

        verifyCurrencyServiceUsage(Currency.AED);
        assertEquals(localDateSunday, returnedDate);
    }

    private void mockDayOfTheWeekService(Currency usd, DayOfWeek dayOfWeek) {
        when(currencyToFirstDayMock.getFirstDayOfTheWeekByCurrency(usd))
                .thenReturn(dayOfWeek);
    }

    private void verifyCurrencyServiceUsage(Currency expectedCurrency) {
        verify(currencyToFirstDayMock, times(1))
                .getFirstDayOfTheWeekByCurrency(currencyCaptor.capture());

        Currency capturedCurrency = currencyCaptor.getValue();
        assertEquals(expectedCurrency, capturedCurrency);
    }
}