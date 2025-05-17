package ua.ypon.accounting.services.personal;

import lombok.RequiredArgsConstructor;
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
public class FoodExpensesService {
    private final PersonalExpensesRepository personalExpensesRepository;
    public double sumExpenseFood() {
        // Отримати всі витрати на їжу
        List<PersonalExpenses> expensesList = personalExpensesRepository.findAll();

        double sum = 0.0;
        if (expensesList.isEmpty()){
            return 0.0;
        }
        for (PersonalExpenses expenses : expensesList) {
            // Підсумувати лише витрати на їжу
            sum += expenses.getFoodExpense();
        }
        return sum;
    }

    public double calculateTotalFoodExpenseForPeriod(LocalDate startDate, LocalDate endDate) {
        List<PersonalExpenses> expenses = personalExpensesRepository.findAllByDateExpensePersonalBetween(startDate, endDate);

        double sum = 0.0;
        for(PersonalExpenses expense : expenses) {
            sum += expense.getFoodExpense();
        }
        return sum;
    }

}
