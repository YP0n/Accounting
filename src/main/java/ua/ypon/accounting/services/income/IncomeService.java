package ua.ypon.accounting.services.income;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.repositories.IncomesRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author ua.ypon 25.01.2024
 */
@Service
@Transactional(readOnly = true)
public class IncomeService {

    private final IncomesRepository incomesRepository;
    private static final Logger log = LoggerFactory.getLogger(IncomeService.class);

    @Autowired
    public IncomeService(IncomesRepository incomesRepository) {
        this.incomesRepository = incomesRepository;
    }

    public List<IncomeShop> index() { return incomesRepository.findAll(); }

    @Transactional
    public void save(IncomeShop incomeShop) {
        try {
            log.info("Saving income: {}", incomeShop);
            incomesRepository.save(incomeShop);
            log.info("Income saved successfully with ID: {}", incomeShop);
        }catch (Exception e) {
            log.error("Error saving income: {}", e.getMessage());
            throw new RuntimeException("Error saving income", e);
        }
    }

    @Transactional
    public void saveIncome(List<IncomeShop> incomeShopList) {
        incomesRepository.saveAll(incomeShopList);
    }

    public Optional<IncomeShop> show(long id) {
        return incomesRepository.findById(id);
    }

    @Transactional
    public void update(long id, IncomeShop incomeShop) {
        Optional<IncomeShop> updateToBeIncome = show(id);
        if(updateToBeIncome.isPresent()) {
            IncomeShop income = updateToBeIncome.get();
            income.setDateIncome(incomeShop.getDateIncome());
            income.setIncomeCash(incomeShop.getIncomeCash());
            income.setIncomeCashless(incomeShop.getIncomeCashless());
            income.setIncomeOther(incomeShop.getIncomeOther());
        }
    }

    @Transactional
    public void delete(long id) { incomesRepository.deleteById(id); }

}
