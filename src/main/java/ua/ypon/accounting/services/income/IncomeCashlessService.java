package ua.ypon.accounting.services.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.repositories.IncomesRepository;

import java.time.LocalDate;

/**
 * @author ua.ypon 04.03.2024
 */
@Service
@Transactional(readOnly = true)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class IncomeCashlessService {

    private final IncomesRepository incomesRepository;

    @Autowired
    public IncomeCashlessService(IncomesRepository incomesRepository) {
        this.incomesRepository = incomesRepository;
    }

    private final double DEFAULT_EXPENSE = 0.0;

    public double sumIncomeCashless() {
        try {
            return incomesRepository.findAll()
                    .stream()
                    .mapToDouble(IncomeShop::getIncomeCashless)
                    .sum();
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }


    public double calculateTotalIncomeCashlessForPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            return incomesRepository.findAllByDateIncomeBetween(startDate, endDate)
                    .stream()
                    .mapToDouble(IncomeShop::getIncomeCashless)
                    .sum();
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }
}
