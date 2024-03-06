package ua.ypon.accounting.controllers.personal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.ypon.accounting.models.BatchForm;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.services.personal.PersonalExpenseService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ua.ypon 25.02.2024
 */
@Controller
@RequestMapping("/batch_update")
@SessionAttributes("batchForm")
public class BatchUpdateController {
    private static final Logger log = LoggerFactory.getLogger(BatchUpdateController.class);

    private final PersonalExpenseService service;

    @Autowired
    public BatchUpdateController(PersonalExpenseService service) {
        this.service = service;
    }

    @GetMapping("/new_batch")
    public String showBatchForm(Model model) {
        // Перевірка, чи існують дані форми у сесії
        if (!model.containsAttribute("batchForm")) {
            // Якщо немає, створюємо новий об'єкт форми і додаємо його до моделі
            model.addAttribute("batchForm", new BatchForm());
        }
        return "personalExpenses/personalExpensesPOSTBatch";
    }

    @PostMapping("/process_batch")
    public String processBatch(@Validated @ModelAttribute("batchForm") BatchForm batchForm, Model model) {
        LocalDate startDate = batchForm.getStartDate();
        LocalDate endDate = batchForm.getEndDate();

        log.info("Дата початку: " + startDate);
        log.info("Дата завершення: " + endDate);

        List<LocalDate> dateIsRange = new ArrayList<>();
        List<PersonalExpenses> expensesList = new ArrayList<>();

        // Логуємо початок обробки діапазону дат
        log.info("Обробка діапазону дат:");

        while (!startDate.isAfter(endDate)) {
            PersonalExpenses expenses = new PersonalExpenses();
            expenses.setDateExpensePersonal(startDate);
            expensesList.add(expenses);
            dateIsRange.add(startDate);

            // Логуємо кожну дату, яку додали до списку витрат
            log.info("Додано витрати на дату: {}", startDate);

            startDate = startDate.plusDays(1);
        }
        // Логуємо кінець обробки діапазону дат
        log.info("Завершено обробку діапазону дат");

        // Оновлюємо об'єкт batchForm з новим списком витрат
        batchForm.setExpensesList(expensesList);

        // Додаємо список дат для відображення на сторінці
        model.addAttribute("dates", dateIsRange);

        log.info("Дані об'єкта batchForm: {}", batchForm);
        return "personalExpenses/personalExpensesPOSTBatch";
    }

    @PostMapping("/add_expenses")
    public ResponseEntity<?> addExpenses(@Validated @ModelAttribute("batchForm") BatchForm batchForm) {
        try {
            List<PersonalExpenses> expensesList = batchForm.getExpensesList();
            log.info("Додавання витрат: " + expensesList.toString());
            service.saveExpenses(expensesList);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save expenses: " + e.getMessage());
        }
    }
}
