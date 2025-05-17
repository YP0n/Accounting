package ua.ypon.accounting.services.business.carExpenses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.BusinessExpenses;
import ua.ypon.accounting.repositories.BusinessExpensesRepository;
import ua.ypon.accounting.services.business.BusinessExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ua.ypon 01.03.2024
 */
@Service
@Transactional(readOnly = true)
public class CarExpensesService {

    private static final Logger log = LoggerFactory.getLogger(BusinessExpenseService.class);
    private static final BigDecimal DEFAULT_EXPENSE = BigDecimal.ZERO;
    private final BusinessExpensesRepository businessExpensesRepository;

    @Autowired
    public CarExpensesService(BusinessExpensesRepository businessExpensesRepository) {
        this.businessExpensesRepository = businessExpensesRepository;
    }

    /**
     * Обчислює загальні витрати на пальне для всіх записів про витрати.
     *
     * @return сума витрат на пальне
     */
    public BigDecimal sumExpensesFuel() {
        try {
            return businessExpensesRepository.findAll()
                    .stream()
                    .map(BusinessExpenses::getFuel)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Помилка при обчисленні витрат на паливо: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
    /**
     * Обчислює витрати на пальне за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на пальне за вибраний період
     */
    public BigDecimal calculateTotalFuelExpenseForPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream()
                    .map(BusinessExpenses::getFuel)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на пальне за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }


    /**
     * Обчислює загальні витрати на обслуговування авто для всіх записів про витрати.
     *
     * @return сума витрат на обслуговування авто
     */
    public BigDecimal sumExpensesMaintenance() {
        try {
            return businessExpensesRepository.findAll()
                    .stream()
                    .map(BusinessExpenses::getMaintenance)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Помилка при обчисленні витрат на обслуговування: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює витрати на обслуговування за вибраний період авто для всіх записів про витрати.
     *
     * @return сума витрат на обслуговування авто за вибраний період
     */
    public BigDecimal calculateTotalMaintenanceExpenseForPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream()
                    .map(BusinessExpenses::getMaintenance)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на пальне за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
}
