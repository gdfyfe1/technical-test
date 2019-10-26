package settlement;

import instruction.Instruction;
import instruction.SettledInstruction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InstructionSettlerService {

    private final SettlementDateService settlementDateService;
    private final PriceCalculatorService priceCalculatorService;

    public InstructionSettlerService(
            SettlementDateService settlementDateService,
            PriceCalculatorService priceCalculatorService) {
        this.settlementDateService = settlementDateService;
        this.priceCalculatorService = priceCalculatorService;
    }

    public SettledInstruction createSettledInstruction(Instruction instruction) {
        instruction.setSettlementDate(calculateSettlementDate(instruction));
        return new SettledInstruction(instruction, calculatePrice(instruction));
    }

    private LocalDate calculateSettlementDate(Instruction instruction) {
        return settlementDateService.calculateSettlementDate(
                instruction.getInstructionDate(),
                instruction.getCurrency()
        );
    }

    private BigDecimal calculatePrice(Instruction instruction) {
        return priceCalculatorService.calculatePrice(
                instruction.getPricePerUnit(),
                instruction.getUnits(),
                instruction.getAgreedFx()
        );
    }
}
