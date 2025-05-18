package ua.ypon.accounting.services.personal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.repositories.PersonalExpensesRepository;
import ua.ypon.accounting.services.personal.exception.InvalidPersonalException;
import ua.ypon.accounting.services.personal.exception.NotFoundPersonalExpenseException;

import java.util.List;

import static ua.ypon.accounting.services.personal.exception.PersonalResponseMessage.*;

/**
 * @author ua.ypon 15.01.2024
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PersonalExpenseService {
    private final PersonalExpensesRepository personalExpensesRepository;
    
    public List<PersonalExpenses> index() {
        
        return personalExpensesRepository.findAll(Sort.by("dateExpensePersonal"));
    }
    
    @Transactional
    public void save(PersonalExpenses personalExpenses) {
        
        try {
            log.info("Saving personal expense: {}", personalExpenses);
            personalExpensesRepository.save(personalExpenses);
        } catch (Exception e) {
            log.error("Error saving personal expense: {}", e.getMessage());
            throw new InvalidPersonalException(ERROR_SAVE_PERSONAL_EXPENSES);
        }
    }
    
    @Transactional
    public void saveExpenses(List<PersonalExpenses> expensesList) {
        personalExpensesRepository.saveAll(expensesList);
    }
    
    public PersonalExpenses show(long id) {
        
        return personalExpensesRepository.findById(id)
                .orElseThrow(() -> new NotFoundPersonalExpenseException(ERROR_GET_PERSONAL_EXPENSES_BY_ID + id));
    }
    
    @Transactional
    public void update(long id, PersonalExpenses expensesUpdate) {
        
        PersonalExpenses expenses = (show(id));
        
            expenses.setDateExpensePersonal(expensesUpdate.getDateExpensePersonal());
            expenses.setFoodExpense(expensesUpdate.getFoodExpense());
            expenses.setUtilityExpense(expensesUpdate.getUtilityExpense());
            expenses.setOtherExpense(expensesUpdate.getOtherExpense());
    }
    
    @Transactional
    public void delete(long id) {
        
        try {
            personalExpensesRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting personal expense: {}", e.getMessage());
            throw new InvalidPersonalException(ERROR_DELETE_PERSONAL_EXPENSES + id);
        }
    }
}
