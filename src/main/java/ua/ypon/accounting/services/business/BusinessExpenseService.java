package ua.ypon.accounting.services.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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
@RequiredArgsConstructor
@Slf4j
public class BusinessExpenseService {

    private final BusinessExpensesRepository businessExpensesRepository;

    public List<BusinessExpenses> index() {
        return businessExpensesRepository.findAll(Sort.by("dateExpensesBusiness"));
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
            expenses.setTaxWar(businessExpenses.getTaxWar());
            expenses.setOwner(businessExpenses.getOwner());
            expenses.setSuppliers(businessExpenses.getSuppliers());
        }
    }

    @Transactional
    public void delete(long id) {
        businessExpensesRepository.deleteById(id);
    }
}
