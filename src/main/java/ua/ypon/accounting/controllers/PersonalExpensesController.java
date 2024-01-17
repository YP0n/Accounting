package ua.ypon.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    private final PersonalExpenseService service;

    @Autowired
    public PersonalExpensesController(PersonalExpenseService service) {
        this.service = service;
    }

    @GetMapping("api/expenses")
    public List<PersonalExpenses> getAllExpenses() {
        return service.findAll();
    }

    @GetMapping("api/expenses/{date}")
    public List<PersonalExpenses> getExpensesByDate(@PathVariable("date")LocalDate date) {
        return service.findPersonalExpensesByDateExpensePersonal(date);
    }

    @PostMapping("/api/expenses")
    public PersonalExpenses addExpense(@RequestBody PersonalExpenses personalExpenses) {
        return service.save(personalExpenses);
    }

//    @GetMapping("/api/total-expenses")
//    public Double getTotalExpensesByDateRange(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
//       LocalDate startDateLocal = LocalDate.parse(startDate);
//       LocalDate endDateLocal = LocalDate.parse(endDate);
//       return service.getTotalExpensesByDateRange(startDateLocal, endDateLocal);
//     }

    @GetMapping("/api/average-expenses")
    public Double getAverageExpensesByCategory(@RequestParam("category") String category) {
        return service.getAverageExpensesByCategory(category);
    }

    @PutMapping("/api/expenses/{id}")
    public void updateExpense(@PathVariable("id") long id, @RequestBody PersonalExpenses expenses) {
        service.update(id, expenses);
    }

    @DeleteMapping("/api/expenses/{id}")
    public void deleteExpense(@PathVariable("id") long id) {
        service.delete(id);
    }
}
