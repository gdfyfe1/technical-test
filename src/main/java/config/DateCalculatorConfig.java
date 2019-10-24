package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import settlement.SettlementDateCalculator;

import java.time.DayOfWeek;

@Configuration
public class DateCalculatorConfig {

    @Bean
    public SettlementDateCalculator settlementDateCalculatorMonday() {
        return new SettlementDateCalculator(DayOfWeek.MONDAY);
    }

    @Bean
    public SettlementDateCalculator settlementDateCalculatorSunday() {
        return new SettlementDateCalculator(DayOfWeek.SUNDAY);
    }
}
