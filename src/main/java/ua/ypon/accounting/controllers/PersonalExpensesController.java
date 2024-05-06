package ua.ypon.accounting.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.models.ExpenseType;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.services.PersonalExpenseService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ua.ypon 15.01.2024
 */
@RestController
@RequestMapping("personal_expenses")
public class PersonalExpensesController {

    private static final Logger log = LoggerFactory.getLogger(PersonalExpenses.class);

    private final PersonalExpenseService service;

    @Autowired
    public PersonalExpensesController(PersonalExpenseService service) {
        this.service = service;
    }

    @GetMapping("/api/expenses")
    public List<PersonalExpenses> getAllExpenses() {
        log.info("PersonalExpenses.getAllExpenses()");
        return service.findAll();
    }

    @PostMapping("/api/expenses")
    public PersonalExpenses addExpense(@RequestBody PersonalExpenses personalExpenses) {
        personalExpenses.setDateExpensePersonal(personalExpenses.getDateExpensePersonal());

        return service.save(personalExpenses);
    }

    @PutMapping("/api/expenses/{id}")
    public void updateExpense(@PathVariable("id") long id, @RequestBody PersonalExpenses expenses) {
        service.update(id, expenses);
    }

    @DeleteMapping("/api/expenses/{id}")
    public void deleteExpense(@PathVariable("id") long id) {
        service.delete(id);
    }

    @GetMapping("/api/sum-food-expenses")
    public double getSumFoodExpenses() {
        return service.sumExpenseFood();
    }

    @GetMapping("/api/sum-expenses-between-date")
    public Double getSumExpensesBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        double totalExpenses;

            if(startDate == null || endDate == null) {
            totalExpenses = service.getSumExpenses(LocalDate.now(), LocalDate.now());
        } else {
            totalExpenses = service.getSumExpenses(startDate, endDate);
        }
        return totalExpenses;
    }
}
