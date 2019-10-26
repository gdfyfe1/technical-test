package application.instruction;

import java.math.BigDecimal;

public class SettledInstruction {

    private final Instruction instruction;
    private final BigDecimal settledPriceUsd;

    public SettledInstruction(Instruction instruction, BigDecimal settledPriceUsd) {
        this.instruction = instruction;
        this.settledPriceUsd = settledPriceUsd;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public BigDecimal getSettledPriceUsd() {
        return settledPriceUsd;
    }

    @Override
    public String toString() {
        return "SettledInstruction{" +
                " instruction=" + instruction.toString() +
                ", settledPrice=" + settledPriceUsd +
                '}';
    }
}
