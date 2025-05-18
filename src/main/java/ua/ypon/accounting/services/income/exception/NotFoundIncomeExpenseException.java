package ua.ypon.accounting.services.income.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundIncomeExpenseException extends RuntimeException {
    
    public NotFoundIncomeExpenseException(String message) {
        super(message);
    }
}
