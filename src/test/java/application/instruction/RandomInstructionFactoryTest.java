package application.instruction;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class RandomInstructionFactoryTest {

    private Supplier<Instruction> randomInstructionFactory;

    @Before
    public void setUp() throws Exception {
        randomInstructionFactory = new RandomInstructionFactory();
    }

    @Test
    public void createRandomInstruction() {
        List<Instruction> randomInstructions = IntStream.range(0, 1000)
                .mapToObj(i -> randomInstructionFactory.get())
                .collect(Collectors.toList());

        assertEquals(1000, randomInstructions.size());
    }
}