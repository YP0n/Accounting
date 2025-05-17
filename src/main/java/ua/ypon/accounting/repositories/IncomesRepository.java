package ua.ypon.accounting.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ypon.accounting.dto.IncomeSumsDto;
import ua.ypon.accounting.models.IncomeShop;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * @author ua.ypon 15.01.2024
 */
@Repository
public interface IncomesRepository extends JpaRepository<IncomeShop, Long> {
    
    @Query("""
         SELECT new ua.ypon.accounting.dto.IncomeSumsDto(
            SUM(i.incomeCash),
            SUM(i.incomeCashless),
            SUM(i.incomeOther)
         )
        FROM IncomeShop i
        WHERE i.dateIncome BETWEEN :start AND :end
    """)
    Optional<IncomeSumsDto> findIncomeSumsByDateBetween(
            @Param("start") LocalDate startDate,
            @Param("end") LocalDate endDate);
    List<IncomeShop> findAllByDateIncomeBetween(LocalDate startDate, LocalDate endDate);

}
