package ua.ypon.accounting.services.income;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.repositories.IncomesRepository;
import ua.ypon.accounting.services.income.exception.InvalidIncomeException;
import ua.ypon.accounting.services.income.exception.NotFoundIncomeExpenseException;

import java.util.List;

import static ua.ypon.accounting.services.income.exception.IncomeResponseMessage.*;

/**
 * @author ua.ypon 25.01.2024
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class IncomeService {
    private final IncomesRepository incomesRepository;
    
    public List<IncomeShop> index() {
        return incomesRepository.findAll(Sort.by("dateIncome"));
    }
    
    @Transactional
    public void save(IncomeShop incomeShop) {
        try {
            log.info("Saving income: {}", incomeShop);
            incomesRepository.save(incomeShop);
            log.info("Income saved successfully with ID: {}", incomeShop);
        } catch (Exception e) {
            log.error("Error saving income: {}", e.getMessage());
            throw new InvalidIncomeException(ERROR_SAVE_INCOME);
        }
    }
    
    @Transactional
    public void saveIncome(List<IncomeShop> incomeShopList) {
        incomesRepository.saveAll(incomeShopList);
    }
    
    public IncomeShop show(long id) {
        
        return incomesRepository.findById(id)
                .orElseThrow(() -> new NotFoundIncomeExpenseException(ERROR_INCOME_NOT_FOUND_ID + id));
    }
    
    @Transactional
    public void update(long id, IncomeShop incomeShop) {
        
        IncomeShop income = show(id);
        
        income.setDateIncome(incomeShop.getDateIncome());
        income.setIncomeCash(incomeShop.getIncomeCash());
        income.setIncomeCashless(incomeShop.getIncomeCashless());
        income.setIncomeOther(incomeShop.getIncomeOther());
    }
    
    @Transactional
    public void delete(long id) {
        
        try {
            incomesRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting income: {}", e.getMessage());
            throw new InvalidIncomeException(ERROR_DELETE_INCOME + id);
        }
    }
}
