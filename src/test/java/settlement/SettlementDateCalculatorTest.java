package settlement;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class SettlementDateCalculatorTest {

    private SettlementDateCalculator testObject;

    private LocalDate localDateMonday;
    private LocalDate localDateTueday;
    private LocalDate localDateSaturday;
    private LocalDate localDateSunday;

    @Before
    public void setUp() throws Exception {
        localDateSaturday = LocalDate.of(2019, 1, 5);
        localDateSunday = LocalDate.of(2019, 1, 6);
        localDateMonday = LocalDate.of(2019, 1, 7);
        localDateTueday = LocalDate.of(2019, 1, 8);

    }

    @Test
    public void calculateSettlementDateDifferentWeekday() {
        testObject = new SettlementDateCalculator(DayOfWeek.MONDAY);

        LocalDate returnedDate = testObject.calculateSettlementDate(localDateTueday);
        assertEquals(localDateTueday, returnedDate);
    }

    @Test
    public void calculateSettlementDateStartOfWeek() {
        testObject = new SettlementDateCalculator(DayOfWeek.MONDAY);

        LocalDate returnedDate = testObject.calculateSettlementDate(localDateMonday);
        assertEquals(localDateMonday, returnedDate);
    }

    @Test
    public void calculateSettlementDateWeekend() {
        testObject = new SettlementDateCalculator(DayOfWeek.MONDAY);

        LocalDate returnedDate = testObject.calculateSettlementDate(localDateSunday);
        assertEquals(localDateMonday, returnedDate);
    }

    @Test
    public void calculateSettlementDateDifferentFirstDay() {
        testObject = new SettlementDateCalculator(DayOfWeek.SUNDAY);

        LocalDate returnedDate = testObject.calculateSettlementDate(localDateSaturday);
        assertEquals(localDateSunday, returnedDate);
    }
}