package instruction;

import java.math.BigDecimal;

public class SettledInstruction {

    private final Instruction instruction;
    private final BigDecimal settledPrice;

    public SettledInstruction(Instruction instruction, BigDecimal settledPrice) {
        this.instruction = instruction;
        this.settledPrice = settledPrice;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public BigDecimal getSettledPrice() {
        return settledPrice;
    }
}
