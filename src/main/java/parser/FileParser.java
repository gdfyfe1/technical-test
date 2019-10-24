package parser;

import instruction.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import parser.exception.UnparsableLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);

    private final InstructionParser instructionParser;

    public FileParser(InstructionParser instructionParser) {
        this.instructionParser = instructionParser;
    }

    public Stream<Instruction> parseFileToInstructions(Path path) {
        try (Stream<String> stream = Files.lines(path)) {
            return getInstructionStream(stream);
        } catch (IOException e) {
            LOGGER.error("Could not parse file at the following path: " + path);
            return Stream.empty();
        }
    }

    private Stream<Instruction> getInstructionStream(Stream<String> stream) {
        return stream.map(this::parseInstruction)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Optional<Instruction> parseInstruction(String line) {
        try {
            return Optional.of(instructionParser.parseInstruction(line));
        } catch (UnparsableLine e) {
            LOGGER.warn(String.format("Unable to parse line:%n%s", line), e);
            return Optional.empty();
        }
    }
}
