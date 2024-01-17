package ua.ypon.accounting.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ypon.accounting.models.IncomeShop;


/**
 * @author ua.ypon 15.01.2024
 */
@Repository
public interface IncomesRepository extends JpaRepository<IncomeShop, Long> {

}
