package ua.ypon.accounting.services.business.purchasingManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.BusinessExpenses;
import ua.ypon.accounting.repositories.BusinessExpensesRepository;
import ua.ypon.accounting.services.business.BusinessExpenseService;

import java.time.LocalDate;

/**
 * @author ua.ypon 01.03.2024
 */
@Service
@Transactional(readOnly = true)
public class SuppliersPurchasingService {

    private static final Logger log = LoggerFactory.getLogger(BusinessExpenseService.class);

    private static final double DEFAULT_EXPENSE = 0.0;

    private final BusinessExpensesRepository businessExpensesRepository;

    @Autowired
    public SuppliersPurchasingService(BusinessExpensesRepository businessExpensesRepository) {
        this.businessExpensesRepository = businessExpensesRepository;
    }

    /**
     * Обчислює загальну суму поставлених товарів постачальниками.
     *
     * @return сума поставок доставки товару постачальниками
     */
    public double sumPurchasingSuppliers() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .mapToDouble(BusinessExpenses::getSuppliers)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальної суми доставки товару постачальниками: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює загальну суму поставлених товарів постачальниками за період.
     *
     * @return сума доставлених товарів постачальниками за період
     */
    public double calculateTotalPurchasingSuppliersForPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().mapToDouble(BusinessExpenses::getSuppliers)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальної суми доставки товару постачальниками за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
}
