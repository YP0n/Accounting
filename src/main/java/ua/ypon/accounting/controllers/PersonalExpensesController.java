package ua.ypon.accounting.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.models.ExpenseType;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.services.PersonalExpenseService;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * @author ua.ypon 15.01.2024
 */
@Controller
@RequestMapping("/personal_expenses")
public class PersonalExpensesController {

    private static final Logger log = LoggerFactory.getLogger(PersonalExpenses.class);

    private final PersonalExpenseService service;

    @Autowired
    public PersonalExpensesController(PersonalExpenseService service) {
        this.service = service;
    }

    @GetMapping("/api/expenses")
    @ResponseBody
    public List<PersonalExpenses> getAllExpenses() {
        log.info("PersonalExpenses.getAllExpenses()");
        return service.findAll();
    }
    @PostMapping("/api/expenses")
    @ResponseBody
    public ResponseEntity<?> addExpense(@RequestBody PersonalExpenses personalExpenses) {
        log.info("PersonaExpenses.addExpense()");
        // Приведення типу поля дати до LocalDate з вказанням формату
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = String.valueOf(personalExpenses.getDateExpensePersonal());
try {
        LocalDate date = LocalDate.parse(dateString, format);
        personalExpenses.setDateExpensePersonal(date);// Встановити поле дати як LocalDate
        service.save(personalExpenses);
        return ResponseEntity.ok().build();
    }catch (DateTimeParseException e) {
    System.out.println("Не правильний формат дати: " + dateString);
    return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/show")
    public String showExpensesPage(Model model) {
        List<PersonalExpenses> expenses = service.findAll();
        model.addAttribute("expenses", expenses);
        log.info("Кількість витрат: {}", expenses.size());
        return "personalExpenses/viewExpenses";
    }

    @GetMapping("/new")
    public String createNewExpenses(Model model) {
        model.addAttribute("personalExpenses", new PersonalExpenses());
        log.info("PersonalExpenses.createNewExpenses()");
        return "personalExpenses/personalExpensesPOST";
    }

    @PutMapping("/api/expenses/{id}")
    public void updateExpense(@PathVariable("id") long id, @RequestBody PersonalExpenses expenses) {
        service.update(id, expenses);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable("id") long id) {
        log.info("Запит на видалення {}", id);
        service.delete(id);
    }

    @GetMapping("/api/sum-food-expenses")
    public double getSumFoodExpenses() {
        return service.sumExpenseFood();
    }

    @GetMapping("/api/sum-expenses-between-date")
    public ModelAndView getSumExpensesBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false)String expenseType) {
        ModelAndView modelAndView = new ModelAndView("personalExpenses/getSumExpenses");
        double totalExpenses;
        ExpenseType expenseTypeEnum = null;

        // Перевірка на null або порожній рядок для expenseType
        if (expenseType != null && !expenseType.isEmpty()) {
            try {
                // Якщо expenseType вказано, конвертуємо його в Enum
                expenseTypeEnum = ExpenseType.valueOf(expenseType);
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
                // Обробка випадку, коли значення expenseType не відповідає жодному елементу Enum
            }
        }

            if(startDate == null || endDate == null) {
            totalExpenses = service.getSumExpenses(LocalDate.now(), LocalDate.now(), expenseTypeEnum);
        } else {
            totalExpenses = service.getSumExpenses(startDate, endDate, expenseTypeEnum);
        }
        if (expenseType != null && !expenseType.isEmpty()) {
            modelAndView.addObject("expenseType", expenseType);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);

        return modelAndView;
    }
}
