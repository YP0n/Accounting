package ua.ypon.accounting.services.income;

import org.springframework.beans.factory.annotation.Autowired;
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
public class IncomeCashService {
    private final IncomesRepository incomesRepository;

    @Autowired
    public IncomeCashService(IncomesRepository incomesRepository) {
        this.incomesRepository = incomesRepository;
    }

    private final double DEFAULT_EXPENSE = 0.0;

    public double sumIncomeCash() {
       try {
           return incomesRepository.findAll()
                   .stream()
                   .mapToDouble(IncomeShop::getIncomeCash)
                   .sum();
       } catch (Exception e) {
           return DEFAULT_EXPENSE;
       }
    }


    public double calculateTotalIncomeCashForPeriod(LocalDate startDate, LocalDate endDate) {
        try {
            return incomesRepository.findAllByDateIncomeBetween(startDate, endDate)
                    .stream()
                    .mapToDouble(IncomeShop::getIncomeCash)
                    .sum();
        } catch (Exception e) {
            return DEFAULT_EXPENSE;
        }
    }
}
