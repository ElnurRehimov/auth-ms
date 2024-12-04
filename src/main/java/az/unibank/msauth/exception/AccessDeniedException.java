package az.unibank.msauth.exception;

import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException {

    private final String code;

    public AccessDeniedException(String message, String code) {
        super(message);
        this.code = code;
    }

}
