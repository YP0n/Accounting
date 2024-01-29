package ua.ypon.accounting.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.BusinessExpenses;
import ua.ypon.accounting.models.BusinessExpensesType;
import ua.ypon.accounting.repositories.BusinessExpensesRepository;

import java.time.LocalDate;
import java.util.List;

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

    public List<BusinessExpenses> findAll() {
        return businessExpensesRepository.findAll();
    }

    @Transactional
    public BusinessExpenses save(BusinessExpenses businessExpenses) {
        try {
            log.info("Saving businessExpense: {}", businessExpenses);
            businessExpensesRepository.save(businessExpenses);
        }catch (Exception e) {
            log.error("Error saving expense: {}", e.getMessage());
        }
        return businessExpenses;
    }

    public double getSumBusinessExpenses(LocalDate startDate, LocalDate endDate, BusinessExpensesType businessExpensesType) {
        List<BusinessExpenses> businessExpensesList = businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate);

        double sum = 0.0;
        for(BusinessExpenses expense : businessExpensesList) {
            sum += expense.getAmount(businessExpensesType);
        }
        return sum;
    }
}
