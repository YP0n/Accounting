package ua.ypon.accounting.services.business.operationExpenses;

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
public class OperationExpensesService {

    private static final Logger log = LoggerFactory.getLogger(BusinessExpenseService.class);

    private final BusinessExpensesRepository businessExpensesRepository;

    private static final double DEFAULT_EXPENSE = 0.0;

    @Autowired
    public OperationExpensesService(BusinessExpensesRepository businessExpensesRepository) {
        this.businessExpensesRepository = businessExpensesRepository;
    }

    /**
     * Обчислює загальні витрати на зарплатню Валі для всіх записів про витрати.
     *
     * @return сума витрат на зарплатню Валі
     */
    public double sumExpensesSalaryV() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .mapToDouble(BusinessExpenses::getSalaryValya)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка обчислення витрат на зарплатню Валі: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
    /**
     * Обчислює загальні витрати на зарплатню Ірі для всіх записів про витрати.
     *
     * @return сума витрат на зарплатню Ірі
     */
    public double sumExpensesSalaryI() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .mapToDouble(BusinessExpenses::getSalaryIra)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка обчислення витрат на зарплатню Ірі: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює загальні витрати на комуналку для всіх записів про витрати.
     *
     * @return сума витрат на комуналку
     */
    public double sumExpensesUtility() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .mapToDouble(BusinessExpenses::getUtilityAndWater)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка обчислення витрат на комуналку: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює загальні витрати на оренду для всіх записів про витрати.
     *
     * @return сума витрат на оренду
     */
    public double sumExpensesRent() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .mapToDouble(BusinessExpenses::getRent)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка обчислення витрат на зарплатню оренду: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює загальні витрати на податок(єдиний) для всіх записів про витрати.
     *
     * @return сума витрат на податок(єдиний)
     */
    public double sumExpensesTaxSingle() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .mapToDouble(BusinessExpenses::getTaxSingle)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка обчислення витрат на податок(єдиний): {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює загальні витрати на податок(ЄСВ) для всіх записів про витрати.
     *
     * @return сума витрат на податок(ЄСВ)
     */
    public double sumExpensesTaxPension() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .mapToDouble(BusinessExpenses::getTaxPension)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка обчислення витрат на податок(ЄСВ): {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює витрати на зарплатню Валі за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на зарплатню Валі за вибраний період
     */
    public double calculateTotalSalaryV(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().mapToDouble(BusinessExpenses::getSalaryValya)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на зарплатню Валі за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює витрати на зарплатню Ірі за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на зарплатню Ірі за вибраний період
     */
    public double calculateTotalSalaryI(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().mapToDouble(BusinessExpenses::getSalaryIra)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на зарплатню Ірі за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює витрати комуналку за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на комуналку за вибраний період
     */
    public double calculateTotalUtility(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().mapToDouble(BusinessExpenses::getUtilityAndWater)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на комуналку за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює витрати на оренду за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на оренду за вибраний період
     */
    public double calculateTotalRent(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().mapToDouble(BusinessExpenses::getRent)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на оренду за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює витрати на податок(єдиний) за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на податок(єдиний) за вибраний період
     */
    public double calculateTotalTaxSingle(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().mapToDouble(BusinessExpenses::getTaxSingle)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на податок(єдиний) за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }

    /**
     * Обчислює витрати на податок(ЄСВ) за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на податок(ЄСВ) за вибраний період
     */
    public double calculateTotalTaxPension(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().mapToDouble(BusinessExpenses::getTaxPension)
                    .sum();
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на податок(ЄСВ) за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
}
