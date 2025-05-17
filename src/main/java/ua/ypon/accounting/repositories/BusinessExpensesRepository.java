package ua.ypon.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ypon.accounting.dto.BusinessExpensesSumsDto;
import ua.ypon.accounting.models.BusinessExpenses;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author ua.ypon 15.01.2024
 */
@Repository
public interface BusinessExpensesRepository extends JpaRepository<BusinessExpenses, Long> {
    List<BusinessExpenses> findAllByDateExpensesBusinessBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("""
            SELECT new ua.ypon.accounting.dto.BusinessExpensesSumsDto(
                SUM(b.fuel),
                SUM(b.maintenance),
                SUM(b.salaryValya),
                SUM(b.salaryIra),
                SUM(b.utilityAndWater),
                SUM(b.rent),
                SUM(b.taxSingle),
                SUM(b.taxPension),
                SUM(b.taxWar),
                SUM(b.owner),
                SUM(b.suppliers)
            )
            FROM BusinessExpenses b
            WHERE b.dateExpensesBusiness BETWEEN :start AND :end
    """)
    Optional<BusinessExpensesSumsDto> findBusinessExpensesSumsByDateBetween(
            @Param("start") LocalDate startDate,
            @Param("end") LocalDate endDate);
}
