package application;

import application.controller.SettlementController;
import application.instruction.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Autowired
    private SettlementController settlementController;

    @Autowired
    private Supplier<Instruction> instructionFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Spring application.Application booted successfully. Running application.report...\n");
        settlementController.runReport(createInstructions(20000));
    }

    private Stream<Instruction> createInstructions(int numberOfInstruction) {
        return IntStream.range(0, numberOfInstruction)
                .mapToObj(i -> instructionFactory.get());
    }
}
