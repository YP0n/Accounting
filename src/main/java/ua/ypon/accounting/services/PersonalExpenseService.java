package ua.ypon.accounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.ExpenseType;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.repositories.PersonalExpensesRepository;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ua.ypon 15.01.2024
 */
@Service
@Transactional(readOnly = true)
public class PersonalExpenseService {

    private final PersonalExpensesRepository personalExpensesRepository;
    private static final Logger log = LoggerFactory.getLogger(PersonalExpenseService.class);


    @Autowired
    public PersonalExpenseService(PersonalExpensesRepository personalExpensesRepository) {
        this.personalExpensesRepository = personalExpensesRepository;
    }

    public List<PersonalExpenses> findAll() {
        return personalExpensesRepository.findAll();
    }

    @Transactional
    public PersonalExpenses save(PersonalExpenses personalExpenses) {
        try {
            log.info("Saving personal expense: {}", personalExpenses);
            personalExpensesRepository.save(personalExpenses);
        } catch (Exception e) {
            log.error("Error saving personal expense: {}", e.getMessage());
        }
        return personalExpenses;
    }

    @Transactional
    public void update(long id, PersonalExpenses expenses) {
        expenses.setId(id);
        personalExpensesRepository.save(expenses);
    }
    @Transactional
    public void delete(long id) {
        personalExpensesRepository.deleteById(id);
    }

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

    public double getSumExpenses(LocalDate startDate, LocalDate endDate) {
        List<PersonalExpenses> expenses = personalExpensesRepository.findAllByDateExpensePersonalBetween(startDate, endDate);

        double sum = 0.0;
        for(PersonalExpenses expense : expenses) {
                sum += expense.getFoodExpense();
        }
        return sum;
    }

}
