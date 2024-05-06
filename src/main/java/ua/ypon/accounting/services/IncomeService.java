package ua.ypon.accounting.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.models.IncomeType;
import ua.ypon.accounting.repositories.IncomesRepository;

import java.time.LocalDate;
import java.util.List;

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

    public List<IncomeShop> findAll() {return incomesRepository.findAll();}

    @Transactional
    public IncomeShop save(IncomeShop incomeShop) {
        try {
            log.info("Saving income: {}", incomeShop);
            IncomeShop savedIncome = incomesRepository.save(incomeShop);
            log.info("Income saved successfully with ID: {}", savedIncome);
        }catch (Exception e) {
            log.error("Error saving income: {}", e.getMessage());
            throw new RuntimeException("Error saving income", e);
        }
        return incomeShop;
    }

    public double getSumIncome(LocalDate startDate, LocalDate endDate, IncomeType incomeType) {
        List<IncomeShop> incomeShopList = incomesRepository.findAllByDateIncomeBetween(startDate, endDate);
        double sum = 0.0;
        for(IncomeShop income : incomeShopList) {
            sum += income.getAmount(incomeType);
        }
        return sum;
    }
}
