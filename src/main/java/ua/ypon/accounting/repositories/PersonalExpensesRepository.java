package ua.ypon.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ypon.accounting.models.PersonalExpenses;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author ua.ypon 15.01.2024
 */
@Repository
public interface PersonalExpensesRepository extends JpaRepository<PersonalExpenses, Long> {
    List<PersonalExpenses> findAllByDateExpensePersonalBetween(LocalDate startDate, LocalDate endDate);
}
