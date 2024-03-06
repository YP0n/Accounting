package ua.ypon.accounting.services.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.BusinessExpenses;
import ua.ypon.accounting.repositories.BusinessExpensesRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author ua.ypon 26.01.2024
 */
@Service
@Transactional(readOnly = true)
public class BusinessExpenseService {

    private final BusinessExpensesRepository businessExpensesRepository;

    private static final Logger log = LoggerFactory.getLogger(BusinessExpenseService.class);

    @Autowired
    public BusinessExpenseService(BusinessExpensesRepository businessExpensesRepository) {
        this.businessExpensesRepository = businessExpensesRepository;
    }
    public List<BusinessExpenses> index() {
        return businessExpensesRepository.findAll();
    }

    @Transactional
    public void save(BusinessExpenses businessExpenses) {
        try {
            log.info("Saving businessExpense: {}", businessExpenses);
            businessExpensesRepository.save(businessExpenses);
        }catch (Exception e) {
            log.error("Error saving expense: {}", e.getMessage());
        }
    }

    @Transactional
    public void saveBusinessExpenses(List<BusinessExpenses> businessExpenses) {
        businessExpensesRepository.saveAll(businessExpenses);
    }

    public Optional<BusinessExpenses> show(long id) {
        return businessExpensesRepository.findById(id);
    }

    @Transactional
    public void update(long id, BusinessExpenses businessExpenses) {
        Optional<BusinessExpenses> updateToBeExpense = show(id);
        if (updateToBeExpense.isPresent()) {
            BusinessExpenses expenses = updateToBeExpense.get();
            expenses.setDateExpensesBusiness(businessExpenses.getDateExpensesBusiness());
            expenses.setFuel(businessExpenses.getFuel());
            expenses.setMaintenance(businessExpenses.getMaintenance());
            expenses.setSalaryValya(businessExpenses.getSalaryValya());
            expenses.setSalaryIra(businessExpenses.getSalaryIra());
            expenses.setUtilityAndWater(businessExpenses.getUtilityAndWater());
            expenses.setRent(businessExpenses.getRent());
            expenses.setTaxSingle(businessExpenses.getTaxSingle());
            expenses.setTaxPension(businessExpenses.getTaxPension());
            expenses.setOwner(businessExpenses.getOwner());
            expenses.setSuppliers(businessExpenses.getSuppliers());
        }
    }

    public void delete(long id) {
        businessExpensesRepository.deleteById(id);
    }
}
