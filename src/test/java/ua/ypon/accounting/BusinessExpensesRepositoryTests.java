package ua.ypon.accounting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ua.ypon.accounting.models.BusinessExpenses;
import ua.ypon.accounting.repositories.BusinessExpensesRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ua.ypon 03.03.2024
 */
    @DataJpaTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Вимкнути заміну бази даних
    @TestPropertySource(locations = "classpath:application.properties") // Задати властивості для тесту
    public class BusinessExpensesRepositoryTests {

        @Autowired
        private BusinessExpensesRepository repository;

        @Test
        public void testFindAllByDateExpensesBusinessBetween() {
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate endDate = LocalDate.of(2024, 1, 31);

            List<BusinessExpenses> expensesList = repository.findAllByDateExpensesBusinessBetween(startDate, endDate);

            // Перевірка, чи повернута список не є порожнім
            assertTrue(!expensesList.isEmpty());

            // Додаткові перевірки за необхідності
            // Наприклад, перевірка кількості повернутих записів
            // assertEquals(очікувана_кількість_записів, expensesList.size());
        }

        @Test
        public void testFindAllByDateExpensesBusinessBetweenWithLazyLoading() {
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate endDate = LocalDate.of(2024, 1, 31);

            List<BusinessExpenses> expensesList = repository.findAllByDateExpensesBusinessBetweenWithLazyLoading(startDate, endDate);

            // Перевірка, чи повернута список не є порожнім
            assertTrue(!expensesList.isEmpty());

            // Додаткові перевірки за необхідності
            // Наприклад, перевірка кількості повернутих записів
            // assertEquals(очікувана_кількість_записів, expensesList.size());
        }

        // Додайте тестові методи для інших методів репозиторія за аналогією з вищенаведеними
    }
