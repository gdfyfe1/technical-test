package parser.exception;

public class UnparsableLine extends Exception {

    public UnparsableLine(String message) {
        super(message);
    }

    public UnparsableLine(String message, Throwable cause) {
        super(message, cause);
    }
}
