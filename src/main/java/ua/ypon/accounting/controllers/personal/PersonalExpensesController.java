package ua.ypon.accounting.controllers.personal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ypon.accounting.models.PersonalExpenses;
import ua.ypon.accounting.services.personal.PersonalExpenseService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author ua.ypon 15.01.2024
 */
@Controller
@RequestMapping("/personal_expenses")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class PersonalExpensesController {
    public static final String PERSONAL_EXPENSES_ATTRIBUTE = "personalExpenses";
    private final PersonalExpenseService service;
    
    @GetMapping("/api/expenses")
    @ResponseBody
    public List<PersonalExpenses> getAllExpenses() {
        log.info("PersonalExpenses.getAllExpenses()");
        return service.index();
    }
    
    @GetMapping("/new")
    public String createNewExpenses(Model model) {
        model.addAttribute(PERSONAL_EXPENSES_ATTRIBUTE, new PersonalExpenses());
        log.info("PersonalExpenses.createNewExpenses()");
        return "personalExpenses/personalExpensesPOST";
    }
    
    @PostMapping()
    @ResponseBody
    public ResponseEntity<Void> addExpense(@ModelAttribute(PERSONAL_EXPENSES_ATTRIBUTE) PersonalExpenses personalExpenses) {
        log.info("PersonalExpenses.addExpense()");
        service.save(personalExpenses);
        String redirectUrl = "/personal_expenses/show";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
    
    @GetMapping("/show")
    public String showExpensesPage(Model model) {
        List<PersonalExpenses> expenses = service.index();
        model.addAttribute("expenses", expenses);
        log.info("Кількість витрат: {}", expenses.size());
        return "personalExpenses/viewExpenses";
    }
    
    @GetMapping()
    public String index(Model model) {
        model.addAttribute(PERSONAL_EXPENSES_ATTRIBUTE, service.index());
        return "personalExpenses/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Optional<PersonalExpenses> personalExpensesOptional = service.show(id);
        if (personalExpensesOptional.isPresent()) {
            PersonalExpenses personalExpenses = personalExpensesOptional.get();
            model.addAttribute(PERSONAL_EXPENSES_ATTRIBUTE, personalExpenses);
            return "personalExpenses/show";
        } else {
            return "personalExpenses/error/404";
        }
    }
    
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        Optional<PersonalExpenses> personalExpensesOptional = service.show(id);
        if (personalExpensesOptional.isPresent()) {
            PersonalExpenses personalExpenses = personalExpensesOptional.get();
            model.addAttribute(PERSONAL_EXPENSES_ATTRIBUTE, personalExpenses);
        }
        return "personalExpenses/edit";
    }
    
    @PatchMapping("/{id}")
    public String updateExpense(@ModelAttribute(PERSONAL_EXPENSES_ATTRIBUTE) PersonalExpenses personalExpenses, @PathVariable("id") long id) {
        service.update(id, personalExpenses);
        return "redirect:/personal_expenses/show";
    }
    
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable("id") long id) {
        log.info("Запит на видалення {}", id);
        service.delete(id);
        return "redirect:/personal_expenses/show";
    }
}
