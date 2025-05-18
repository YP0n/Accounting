package ua.ypon.accounting.controllers.personal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.personal.FoodExpensesService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 23.02.2024
 */
@Controller
@RequestMapping("/food_expenses")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class FoodExpensesController {
    private final FoodExpensesService foodExpensesService;
    
    @GetMapping("/api/sum_food_expenses")
    public ModelAndView getSumFoodExpenses() {
        
        ModelAndView modelAndView = new ModelAndView("personalExpenses/food/getSumExpensesFood");
        
        BigDecimal totalExpenses = foodExpensesService.sumExpenseFood();
        modelAndView.addObject("totalExpenses", totalExpenses);
        
        return modelAndView;
    }
    
    @GetMapping("/api/sum_expenses_food_between_date")
    public ModelAndView getSumExpensesFoodBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        
        ModelAndView modelAndView = new ModelAndView("personalExpenses/food/getSumExpensesFood");
        
        BigDecimal totalExpenses;
        
        if (startDate == null || endDate == null) {
            totalExpenses = foodExpensesService.calculateTotalFoodExpenseForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalExpenses = foodExpensesService.calculateTotalFoodExpenseForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);
        
        return modelAndView;
    }
}
