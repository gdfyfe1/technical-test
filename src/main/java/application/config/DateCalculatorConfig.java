package application.config;

import application.instruction.Currency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DateCalculatorConfig {

    @Bean
    public DayOfWeek defaultFirstDayOfWeek() {
        return DayOfWeek.MONDAY;
    }

    @Bean
    public Map<Currency, DayOfWeek> firstDayOfWeekByCurrency() {
        Map<Currency, DayOfWeek> map = new HashMap<>();
        map.put(Currency.USD, DayOfWeek.MONDAY);
        map.put(Currency.GPB, DayOfWeek.MONDAY);
        map.put(Currency.SGP, DayOfWeek.MONDAY);
        map.put(Currency.SAR, DayOfWeek.SUNDAY);
        map.put(Currency.AED, DayOfWeek.SUNDAY);
        return map;
    }
}
