package pl.sda.autokomis.exception;

public class PriviligeNotGrantedException extends RuntimeException {

    public PriviligeNotGrantedException(String message) {
        super(message);
    }
}
