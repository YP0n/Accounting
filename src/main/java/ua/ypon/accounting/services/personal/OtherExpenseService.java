package ua.ypon.accounting.services.personal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.repositories.PersonalExpensesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ua.ypon 23.02.2024
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OtherExpenseService {
    private final PersonalExpensesRepository personalExpensesRepository;
    private static final BigDecimal DEFAULT_EXPENSE = BigDecimal.ZERO;
    public BigDecimal sumExpenseOther() {

        try {
            return personalExpensesRepository.findAll()
                    .stream()
                    .map(PersonalExpenses::getOtherExpense)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Error calculating total other expenses: {}", e.getMessage());
            
            return DEFAULT_EXPENSE;
        }
    }
    
    public BigDecimal calculateTotalOtherExpenseForPeriod(LocalDate startDate, LocalDate endDate) {

        try {
            return personalExpensesRepository.findAllByDateExpensePersonalBetween(startDate, endDate)
                    .stream()
                    .map(PersonalExpenses::getOtherExpense)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Error calculating total other expenses for period: {}", e.getMessage());
            
            return DEFAULT_EXPENSE;
        }
    }
}
