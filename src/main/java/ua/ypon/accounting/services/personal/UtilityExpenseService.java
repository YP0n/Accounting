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
public class UtilityExpenseService {
    private static final BigDecimal DEFAULT_EXPENSE = BigDecimal.ZERO;
    private final PersonalExpensesRepository personalExpensesRepository;
    
    public BigDecimal sumExpensesUtility() {
        
        try {
            return personalExpensesRepository.findAll()
                    .stream()
                    .map(PersonalExpenses::getUtilityExpense)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Error calculating total utility expenses: {}", e.getMessage());
            
            return DEFAULT_EXPENSE;
        }
    }
    
    public BigDecimal calculateTotalUtilityExpenseForPeriod(LocalDate startDate, LocalDate endDate) {
        
        try {
            return personalExpensesRepository.findAllByDateExpensePersonalBetween(startDate, endDate)
                    .stream()
                    .map(PersonalExpenses::getUtilityExpense)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Error calculating utility expenses for period {} to {}: {}", startDate, endDate, e.getMessage());
            
            return DEFAULT_EXPENSE;
        }
    }
}
