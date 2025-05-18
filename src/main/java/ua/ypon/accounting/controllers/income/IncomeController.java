package ua.ypon.accounting.controllers.income;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.services.income.IncomeService;

import java.net.URI;
import java.util.List;

/**
 * @author ua.ypon 25.01.2024
 */
@Controller
@RequestMapping("/income_shop")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class IncomeController {
    private final IncomeService incomeService;
    
    @GetMapping("/api/income")
    @ResponseBody
    public List<IncomeShop> getAllIncome() {
        
        log.info("Income.getAllIncome()");
        
        return incomeService.index();
    }
    
    @GetMapping("/new_income")
    public String createNewIncome(Model model) {
        
        model.addAttribute("income", new IncomeShop());
        log.info("IncomeShop.createNewIncome()");
        
        return "income/incomePOST";
    }
    
    @PostMapping()
    public ResponseEntity<Void> addIncome(@ModelAttribute("income") IncomeShop incomeShop) {
        
        log.info("IncomeShop.addIncome()");
        incomeService.save(incomeShop);
        
        String redirectUrl = "/income_shop/show";
        log.info("log " + redirectUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
    
    @GetMapping("/show")
    public String showIncomePage(Model model) {
        
        List<IncomeShop> incomes = incomeService.index();
        model.addAttribute("incomes", incomes);
        log.info("Дохід: {}", incomes.size());
        
        return "income/viewIncome";
    }
    
    @GetMapping()
    public String index(Model model) {
        
        model.addAttribute("income_shop", incomeService.index());
        
        return "income/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        
        IncomeShop incomeShop = incomeService.show(id);
        
        if (incomeShop != null) {
            model.addAttribute("incomeShop", incomeShop);
            
            return "income/show";
        } else {
            
            return "personalExpenses/error/404";
        }
    }
    
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        
        IncomeShop incomeShop = incomeService.show(id);
        
        if (incomeShop != null) {
            model.addAttribute("incomeShop", incomeShop);
        }
        
        return "income/edit";
    }
    
    @PatchMapping("/{id}")
    public String updateIncome(@ModelAttribute("income") IncomeShop incomeShop, @PathVariable("id") long id) {
        
        incomeService.update(id, incomeShop);
        
        return "redirect:/income_shop/show";
    }
    
    @DeleteMapping("/{id}")
    public String deleteIncome(@PathVariable("id") long id) {
        
        log.info("Запит на видалення {}", id);
        incomeService.delete(id);
        
        return "redirect:/income_shop/show";
    }
}
