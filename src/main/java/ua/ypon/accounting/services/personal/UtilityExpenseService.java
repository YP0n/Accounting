package ua.ypon.accounting.services.personal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UtilityExpenseService {

    private final PersonalExpensesRepository personalExpensesRepository;

    private static final Logger log = LoggerFactory.getLogger(UtilityExpenseService.class);


    @Autowired
    public UtilityExpenseService(PersonalExpensesRepository personalExpensesRepository) {
        this.personalExpensesRepository = personalExpensesRepository;
    }

    public double sumExpensesUtility() {
        List<PersonalExpenses> expensesList = personalExpensesRepository.findAll();

        double sum = 0.0;
        if(expensesList.isEmpty()) {
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
        for(PersonalExpenses expense : expenses) {
            sum += expense.getUtilityExpense();
        }
        return sum;
    }
}
