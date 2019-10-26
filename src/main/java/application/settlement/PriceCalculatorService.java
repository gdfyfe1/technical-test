package application.settlement;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceCalculatorService {

    public BigDecimal calculatePrice(BigDecimal pricePerUnit, int units, BigDecimal agreedFx) {
        return pricePerUnit
                .multiply(agreedFx)
                .multiply(BigDecimal.valueOf(units));
    }
}
