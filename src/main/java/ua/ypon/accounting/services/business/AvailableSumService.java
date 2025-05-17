package ua.ypon.accounting.services.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.ypon.accounting.dto.BusinessExpensesSumsDto;
import ua.ypon.accounting.dto.IncomeSumsDto;
import ua.ypon.accounting.repositories.BusinessExpensesRepository;
import ua.ypon.accounting.repositories.IncomesRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvailableSumService {
    
    private final BusinessExpensesRepository businessExpensesRepository;
    private final IncomesRepository incomesRepository;
    
    private BigDecimal getDaysInMonth(LocalDate date) {
        YearMonth ym = YearMonth.from(date);
        return BigDecimal.valueOf(ym.lengthOfMonth()); // 28, 29, 30 або 31
    }
    
    private IncomeSumsDto getIncomeSums(LocalDate from, LocalDate end) {
        
        return incomesRepository.findIncomeSumsByDateBetween(from, end)
                .orElse(new IncomeSumsDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    
    }
    
    private BusinessExpensesSumsDto getBusinessExpensesSums(LocalDate from, LocalDate end) {
        
        return businessExpensesRepository.findBusinessExpensesSumsByDateBetween(from, end)
                .orElse(new BusinessExpensesSumsDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                        BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                        BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    
    }
    
    private BigDecimal calculateTotalIncome(IncomeSumsDto income) {
        
        return income.totalCash()
                .add(income.totalCashless())
                .add(income.totalOther());
    }
    
    private BigDecimal calculateTotalExpenses(BusinessExpensesSumsDto expenses, long actualDays, LocalDate from) {
        
        return normalizeFixedExpenses(expenses.fuel(), actualDays, from)
                .add(normalizeFixedExpenses(expenses.maintenance(), actualDays, from))
                .add(normalizeFixedExpenses(expenses.salaryValya(), actualDays, from))
                .add(normalizeFixedExpenses(expenses.salaryIra(), actualDays, from))
                .add(normalizeFixedExpenses(expenses.utilityAndWater(), actualDays, from))
                .add(normalizeFixedExpenses(expenses.rent(), actualDays, from))
                .add(normalizeFixedExpenses(expenses.taxSingle(), actualDays, from))
                .add(normalizeFixedExpenses(expenses.taxPension(), actualDays, from))
                .add(normalizeFixedExpenses(expenses.taxWar(), actualDays, from))
                .add(expenses.owner())
                .add(expenses.suppliers());
    }
    
    private void validateDates(LocalDate from, LocalDate end) {
        
        if (from == null || end == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        if (from.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
    }
    
    private long countDaysInPeriod(LocalDate from, LocalDate end) {
        
        return from.datesUntil(end.plusDays(1)).count();
    }
    
    private BigDecimal normalizeFixedExpenses(BigDecimal expense, long actualDays, LocalDate from) {
        
        BigDecimal daysInMonth = getDaysInMonth(from);
        log.info("Days in month: {}", daysInMonth);
        log.info("Expense: {}", expense);
        log.info("Actual days: {}", actualDays);
        log.info("From date: {}", from);
        
        if(expense == null) {
            return BigDecimal.ZERO;
        }
        
        return expense.divide(daysInMonth, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(actualDays));
    }
    
    
    public BigDecimal calculateAvailableSum(LocalDate from, LocalDate end) {
        
        validateDates(from, end);
        
        long actualDays = countDaysInPeriod(from, end);
        
        IncomeSumsDto income = getIncomeSums(from, end);
        BusinessExpensesSumsDto expenses = getBusinessExpensesSums(from, end);
        
        BigDecimal totalIncome = calculateTotalIncome(income);
        BigDecimal totalExpenses = calculateTotalExpenses(expenses, actualDays, from);
        
        return totalIncome.subtract(totalExpenses);
    }
}
