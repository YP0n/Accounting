package ua.ypon.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.ypon.accounting.models.BusinessExpenses;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ua.ypon 15.01.2024
 */
@Repository
public interface BusinessExpensesRepository extends JpaRepository<BusinessExpenses, Long> {
    List<BusinessExpenses> findAllByDateExpensesBusinessBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT b FROM BusinessExpenses b WHERE b.dateExpensesBusiness BETWEEN :startDate AND :endDate")
    List<BusinessExpenses> findAllByDateExpensesBusinessBetweenWithLazyLoading(LocalDate startDate, LocalDate endDate);

    @Query("SELECT b FROM BusinessExpenses b LEFT JOIN FETCH b.fuel")
    List<BusinessExpenses> findAllFuelWithLazyLoading();

    @Query("SELECT b FROM BusinessExpenses b LEFT JOIN FETCH b.maintenance")
    List<BusinessExpenses> findAllMaintenanceWithLazyLoading();
}
