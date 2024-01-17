package ua.ypon.accounting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ypon.accounting.models.BusinessExpenses;

/**
 * @author ua.ypon 15.01.2024
 */
@Repository
public interface BusinessExpensesRepository extends JpaRepository<BusinessExpenses, Long> {

}
