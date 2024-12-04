package az.unibank.msauth.exception;

import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException {

    private final String code;

    public InvalidUserException(String message, String code) {
        super(message);
        this.code = code;
    }
}
