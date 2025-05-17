package ua.ypon.accounting.services.personal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.repositories.PersonalExpensesRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ua.ypon 23.02.2024
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UtilityExpenseService {
    private final PersonalExpensesRepository personalExpensesRepository;
    
    public double sumExpensesUtility() {
        List<PersonalExpenses> expensesList = personalExpensesRepository.findAll();
        
        double sum = 0.0;
        if (expensesList.isEmpty()) {
            return 0.0;
        }
        for (PersonalExpenses expenses : expensesList) {
            sum += expenses.getUtilityExpense();
        }
        return sum;
    }
    
    public double calculateTotalUtilityExpenseForPeriod(LocalDate startDate, LocalDate endDate) {
        List<PersonalExpenses> expenses = personalExpensesRepository.findAllByDateExpensePersonalBetween(startDate, endDate);
        double sum = 0.0;
        for (PersonalExpenses expense : expenses) {
            sum += expense.getUtilityExpense();
        }
        return sum;
    }
}
