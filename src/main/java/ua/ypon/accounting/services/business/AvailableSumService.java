package ua.ypon.accounting.services.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.ypon.accounting.dto.BusinessExpensesSumsDto;
import ua.ypon.accounting.dto.IncomeSumsDto;
import ua.ypon.accounting.repositories.BusinessExpensesRepository;
import ua.ypon.accounting.repositories.IncomesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AvailableSumService {
    
    private final BusinessExpensesRepository businessExpensesRepository;
    private final IncomesRepository incomesRepository;
    
    private IncomeSumsDto getIncomeSums(LocalDate from, LocalDate end) {
        
        validateDates(from, end);
        
        return incomesRepository.findIncomeSumsByDateBetween(from, end)
                .orElse(new IncomeSumsDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    
    }
    
    private BusinessExpensesSumsDto getBusinessExpensesSums(LocalDate from, LocalDate end) {
       
        validateDates(from, end);
        
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
    
    private BigDecimal calculateTotalExpenses(BusinessExpensesSumsDto expenses) {
        
        return expenses.fuel()
                .add(expenses.maintenance())
                .add(expenses.salaryValya())
                .add(expenses.salaryIra())
                .add(expenses.utilityAndWater())
                .add(expenses.rent())
                .add(expenses.taxSingle())
                .add(expenses.taxPension())
                .add(expenses.taxWar())
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
    
    public BigDecimal calculateAvailableSum(LocalDate from, LocalDate end) {
        
        IncomeSumsDto income = getIncomeSums(from, end);
        BusinessExpensesSumsDto expenses = getBusinessExpensesSums(from, end);
        
        BigDecimal totalIncome = calculateTotalIncome(income);
        BigDecimal totalExpenses = calculateTotalExpenses(expenses);
        
        return totalIncome.subtract(totalExpenses);
    }
}
