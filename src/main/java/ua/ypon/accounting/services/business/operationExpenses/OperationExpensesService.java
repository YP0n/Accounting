package ua.ypon.accounting.services.business.operationExpenses;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.BusinessExpenses;
import ua.ypon.accounting.repositories.BusinessExpensesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ua.ypon 01.03.2024
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OperationExpensesService {
    
    private final BusinessExpensesRepository businessExpensesRepository;
    
    private static final BigDecimal DEFAULT_EXPENSE = BigDecimal.ZERO;
    
    /**
     * Обчислює загальні витрати на зарплатню Валі для всіх записів про витрати.
     *
     * @return сума витрат на зарплатню Валі
     */
    public BigDecimal sumExpensesSalaryV() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .map(BusinessExpenses::getSalaryValya)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal sumExpensesSalaryI() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .map(BusinessExpenses::getSalaryIra)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal sumExpensesUtility() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .map(BusinessExpenses::getUtilityAndWater)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal sumExpensesRent() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .map(BusinessExpenses::getRent)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal sumExpensesTaxSingle() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .map(BusinessExpenses::getTaxSingle)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal sumExpensesTaxPension() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .map(BusinessExpenses::getTaxPension)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Помилка обчислення витрат на податок(ЄСВ): {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
    
    /**
     * Обчислює загальні витрати на податок(військовий) для всіх записів про витрати.
     *
     * @return сума витрат на податок(військовий)
     */
    public BigDecimal sumExpensesTaxWar() {
        try {
            return businessExpensesRepository.findAll().stream()
                    .map(BusinessExpenses::getTaxWar)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Помилка обчислення витрат на податок(військовий): {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
    
    /**
     * Обчислює витрати на зарплатню Валі за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на зарплатню Валі за вибраний період
     */
    public BigDecimal calculateTotalSalaryV(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().map(BusinessExpenses::getSalaryValya)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal calculateTotalSalaryI(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().map(BusinessExpenses::getSalaryIra)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal calculateTotalUtility(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().map(BusinessExpenses::getUtilityAndWater)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal calculateTotalRent(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().map(BusinessExpenses::getRent)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal calculateTotalTaxSingle(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().map(BusinessExpenses::getTaxSingle)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    public BigDecimal calculateTotalTaxPension(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().map(BusinessExpenses::getTaxPension)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на податок(ЄСВ) за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
    
    /**
     * Обчислює витрати на податок(військовий) за вибраний період для всіх записів про витрати.
     *
     * @return сума витрат на податок(військовий) за вибраний період
     */
    public BigDecimal calculateTotalTaxWar(LocalDate startDate, LocalDate endDate) {
        try {
            return businessExpensesRepository.findAllByDateExpensesBusinessBetween(startDate, endDate)
                    .stream().map(BusinessExpenses::getTaxWar)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Помилка при обчисленні загальних витрат на податок(військовий) за період: {}", e.getMessage());
            return DEFAULT_EXPENSE;
        }
    }
}
