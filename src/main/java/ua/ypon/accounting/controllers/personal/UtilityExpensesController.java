package ua.ypon.accounting.controllers.personal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.personal.UtilityExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 23.02.2024
 */
@Controller
@RequestMapping("/utility_expenses")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class UtilityExpensesController {
    private final UtilityExpenseService utilityExpenseService;
    
    @GetMapping("/api/sum-utility-expenses")
    public ModelAndView getSumUtilityExpenses() {
        
        ModelAndView modelAndView = new ModelAndView("personalExpenses/utility/getSumExpensesUtility");
        
        BigDecimal totalExpenses = utilityExpenseService.sumExpensesUtility();
        modelAndView.addObject("totalExpenses", totalExpenses);
        
        return modelAndView;
    }
    
    @GetMapping("/api/sum_expenses_utility_between_date")
    public ModelAndView getSumExpensesUtilityBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        
        ModelAndView modelAndView = new ModelAndView("personalExpenses/utility/getSumExpensesUtility");
        
        BigDecimal totalExpenses;
        
        if (startDate == null || endDate == null) {
            totalExpenses = utilityExpenseService.calculateTotalUtilityExpenseForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalExpenses = utilityExpenseService.calculateTotalUtilityExpenseForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);
        
        return modelAndView;
    }
}
