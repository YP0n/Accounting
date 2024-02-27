package ua.ypon.accounting.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.ExpenseType;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.repositories.PersonalExpensesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public void save(PersonalExpenses personalExpenses) {
        try {
            log.info("Saving personal expense: {}", personalExpenses);
            personalExpensesRepository.save(personalExpenses);
        } catch (Exception e) {
            log.error("Error saving personal expense: {}", e.getMessage());
        }
    }


    @Transactional
    public void update(long id, PersonalExpenses expensesUpdate) {
        Optional<PersonalExpenses> updateToBeExpense = show(id);
        if(updateToBeExpense.isPresent()) {
            PersonalExpenses expenses = updateToBeExpense.get();
            expenses.setDateExpensePersonal(expensesUpdate.getDateExpensePersonal());
            expenses.setFoodExpense(expensesUpdate.getFoodExpense());
            expenses.setUtilityExpense(expensesUpdate.getUtilityExpense());
            expenses.setOtherExpense(expensesUpdate.getOtherExpense());
        }
    }

    @Transactional
    public void delete(long id) {
        personalExpensesRepository.deleteById(id);
    }

    public Optional<PersonalExpenses> show(long id) {
        return personalExpensesRepository.findById(id);
    }

    public List<PersonalExpenses> index() {
        return personalExpensesRepository.findAll();
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

    public double getSumExpenses(LocalDate startDate, LocalDate endDate, ExpenseType expenseType) {
        List<PersonalExpenses> expenses = personalExpensesRepository.findAllByDateExpensePersonalBetween(startDate, endDate);

        double sum = 0.0;
        for(PersonalExpenses expense : expenses) {
                sum += expense.getAmount(expenseType);
        }
        return sum;
    }

}
