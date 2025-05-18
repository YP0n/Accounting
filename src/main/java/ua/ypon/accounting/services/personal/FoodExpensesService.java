package ua.ypon.accounting.services.personal;

import lombok.RequiredArgsConstructor;
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
public class FoodExpensesService {
    private static final BigDecimal DEFAULT_EXPENSE = BigDecimal.ZERO;
    private final PersonalExpensesRepository personalExpensesRepository;
    
    public BigDecimal sumExpenseFood() {
        
        try {
            return personalExpensesRepository.findAll()
                    .stream()
                    .map(PersonalExpenses::getFoodExpense)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }
    
    public BigDecimal calculateTotalFoodExpenseForPeriod(LocalDate startDate, LocalDate endDate) {
        
        try {
            return personalExpensesRepository.findAllByDateExpensePersonalBetween(startDate, endDate)
                    .stream()
                    .map(PersonalExpenses::getFoodExpense)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }
}
