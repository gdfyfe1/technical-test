package application.config;

import application.instruction.Instruction;
import application.instruction.RandomInstructionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class ControllerConfig {

    @Bean
    public Supplier<Instruction> randomInstructionSupplier() {
        return new RandomInstructionFactory();
    }
}
