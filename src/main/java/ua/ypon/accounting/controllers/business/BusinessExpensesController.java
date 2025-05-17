package ua.ypon.accounting.controllers.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ypon.accounting.models.BusinessExpenses;
import ua.ypon.accounting.services.business.BusinessExpenseService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author ua.ypon 26.01.2024
 */
@Controller
@RequestMapping("/business_expenses")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class BusinessExpensesController {
    
    private final BusinessExpenseService service;
    @GetMapping("/api/business_expenses")
    @ResponseBody
    public List<BusinessExpenses> getAllExpenses() {
        log.info("Expense.getAllExpenses()");
        return service.index();
    }

    @GetMapping("/newBusinessExpense")
    public String createNewExpenses(Model model) {
        model.addAttribute("businessExpenses", new BusinessExpenses());
        log.info("BusinessExpenses.createNewExpenses()");
        return "businessExpenses/businessExpensesPOST";
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<?> addBusinessExpense(@ModelAttribute("businessExpenses") BusinessExpenses businessExpenses) {
        log.info("BusinessExpenses.addBusinessExpense");
        service.save(businessExpenses);
        String redirectUrl = "/business_expenses/show";
        log.info("log " + redirectUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/show")
    public String showBusinessExpensesPage(Model model) {
        List<BusinessExpenses> expenses = service.index();
        model.addAttribute("expenses", expenses);
        log.info("Кількість витрат: {}", expenses.size());
        return "businessExpenses/viewBusinessExpenses";
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("business_expenses", service.index());
        return "businessExpenses/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Optional<BusinessExpenses> businessExpensesOptional = service.show(id);
        if(businessExpensesOptional.isPresent()) {
            BusinessExpenses businessExpenses = businessExpensesOptional.get();
            model.addAttribute("businessExpenses", businessExpenses);
            return "businessExpenses/show";
        } else {
            return "personalExpenses/error/404";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        Optional<BusinessExpenses> businessExpensesOptional = service.show(id);
        if (businessExpensesOptional.isPresent()) {
            BusinessExpenses businessExpenses = businessExpensesOptional.get();
            model.addAttribute("businessExpenses", businessExpenses);
        }
        return "businessExpenses/edit";
    }

    @PatchMapping("/{id}")
    public String updateExpense(@ModelAttribute("businessExpenses") BusinessExpenses businessExpenses, @PathVariable("id") long id) {
        service.update(id, businessExpenses);
        return "redirect:/business_expenses/show";
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable("id") long id) {
        log.info("Запит на видалення {}", id);
        service.delete(id);
        return "redirect:/business_expenses/show";
    }
}
