package ua.ypon.accounting.services.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.repositories.IncomesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ua.ypon 04.03.2024
 */
@Service
@Transactional(readOnly = true)
public class IncomeOtherService {

    private final IncomesRepository incomesRepository;

    @Autowired
    public IncomeOtherService(IncomesRepository incomesRepository) {
        this.incomesRepository = incomesRepository;
    }

    private final BigDecimal DEFAULT_EXPENSE = BigDecimal.ZERO;

    public BigDecimal sumIncomeOther() {
        try {
            return incomesRepository.findAll()
                    .stream()
                    .map(IncomeShop::getIncomeOther)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }

    public BigDecimal calculateTotalIncomeOtherForPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            return incomesRepository.findAllByDateIncomeBetween(startDate, endDate)
                    .stream()
                    .map(IncomeShop::getIncomeOther)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }
}
