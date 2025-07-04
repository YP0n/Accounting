package ua.ypon.accounting.services.income.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidIncomeException extends RuntimeException {
    public InvalidIncomeException(String message) {
        super(message);
    }
}
