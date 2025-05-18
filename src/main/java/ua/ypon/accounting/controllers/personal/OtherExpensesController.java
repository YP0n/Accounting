package ua.ypon.accounting.controllers.personal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.personal.OtherExpenseService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 23.02.2024
 */
@Controller
@RequestMapping("/other_expenses")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class OtherExpensesController {
    private final OtherExpenseService otherExpenseService;
    
    @GetMapping("/api/sum-other-expenses")
    public ModelAndView getSumOtherExpenses() {
        
        ModelAndView modelAndView = new ModelAndView("personalExpenses/other/getSumExpensesOther");
        
        BigDecimal totalExpenses;
        totalExpenses = otherExpenseService.sumExpenseOther();
        modelAndView.addObject("totalExpenses", totalExpenses);
        
        return modelAndView;
    }
    
    @GetMapping("/api/sum_expenses_other_between_date")
    public ModelAndView getSumExpensesOtherBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        
        ModelAndView modelAndView = new ModelAndView("personalExpenses/other/getSumExpensesOther");
        
        BigDecimal totalExpenses;
        if (startDate == null || endDate == null) {
            totalExpenses = otherExpenseService.calculateTotalOtherExpenseForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalExpenses = otherExpenseService.calculateTotalOtherExpenseForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);
        
        return modelAndView;
    }
}
