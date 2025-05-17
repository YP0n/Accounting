package ua.ypon.accounting.controllers.personal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ua.ypon.accounting.models.BatchForm;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.services.personal.PersonalExpenseService;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ua.ypon 25.02.2024
 */
@Controller
@RequestMapping("/batch_update")
@SessionAttributes("batchForm")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class BatchUpdateController {
    private final PersonalExpenseService service;
    
    @GetMapping("/new_batch")
    public String showBatchForm(Model model) {
        log.info("Отримано запит на відображення форми нового пакету");
        // Перевірка, чи існують дані форми у сесії
        if (!model.containsAttribute("batchForm")) {
            // Якщо немає, створюємо новий об'єкт форми і додаємо його до моделі
            model.addAttribute("batchForm", new BatchForm());
        }
        return "personalExpenses/personalExpensesPOSTBatch";
    }
    
    @PostMapping("/process_batch")
    public String processBatch(@Validated @ModelAttribute("batchForm") BatchForm batchForm, Model model) {
        log.info("Дані форми перед обробкою: {}", batchForm);
        log.info("Отримано запит на обробку пакету даних");
        
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
    public ResponseEntity<String> addExpenses(@Validated @ModelAttribute("batchForm") BatchForm batchForm,
                                              SessionStatus status) {
        log.info("Отримано запит на додавання витрат");
        try {
            List<PersonalExpenses> expensesList = batchForm.getExpensesList();
            log.info("Додавання витрат: " + expensesList.toString());
            service.saveExpenses(expensesList);
            
            status.setComplete(); // Завершуємо сесію, щоб видалити атрибути з моделі
            
            String redirectUrl = "/personal_expenses/show";
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(redirectUrl));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save expenses: " + e.getMessage());
        }
    }
}
