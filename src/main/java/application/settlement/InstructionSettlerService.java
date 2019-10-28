package application.settlement;

import application.instruction.Instruction;
import application.instruction.SettledInstruction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InstructionSettlerService {

    private final SettlementDateService settlementDateService;
    private final PriceCalculatorService priceCalculatorService;

    public InstructionSettlerService(
            SettlementDateService settlementDateService,
            PriceCalculatorService priceCalculatorService) {
        this.settlementDateService = settlementDateService;
        this.priceCalculatorService = priceCalculatorService;
    }

    public SettledInstruction settleInstruction(Instruction instruction) {
        instruction.setSettlementDate(calculateSettlementDate(instruction));
        return new SettledInstruction(instruction, priceCalculatorService.calculatePrice(instruction));
    }

    private LocalDate calculateSettlementDate(Instruction instruction) {
        return settlementDateService.calculateSettlementDate(
                instruction.getSettlementDate(),
                instruction.getCurrency()
        );
    }
}
