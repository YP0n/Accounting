package ua.ypon.accounting.services.income;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.repositories.IncomesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ua.ypon 04.03.2024
 */
@Service
@RequiredArgsConstructor
public class IncomeCashService {
    private static final BigDecimal DEFAULT_EXPENSE = BigDecimal.ZERO;
    private final IncomesRepository incomesRepository;
    
    public BigDecimal sumIncomeCash() {
        try {
            return incomesRepository.findAll()
                    .stream()
                    .map(IncomeShop::getIncomeCash)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }
    
    
    public BigDecimal calculateTotalIncomeCashForPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            return incomesRepository.findAllByDateIncomeBetween(startDate, endDate)
                    .stream()
                    .map(IncomeShop::getIncomeCash)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }
}
