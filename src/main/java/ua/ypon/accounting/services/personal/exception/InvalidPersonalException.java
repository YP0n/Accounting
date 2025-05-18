package ua.ypon.accounting.services.personal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidPersonalException extends RuntimeException {
    public InvalidPersonalException(String message) {
        super(message);
    }
}
