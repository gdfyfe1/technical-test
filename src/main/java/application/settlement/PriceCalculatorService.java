package application.settlement;

import application.instruction.Instruction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceCalculatorService {

    public BigDecimal calculatePrice(Instruction instruction) {
        return instruction.getPricePerUnit()
                .multiply(instruction.getAgreedFx())
                .multiply(BigDecimal.valueOf(instruction.getUnits()));
    }
}
