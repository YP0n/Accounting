package ua.ypon.accounting.controllers.personal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.personal.FoodExpensesService;

import java.time.LocalDate;

/**
 * @author ua.ypon 23.02.2024
 */
@Controller
@RequestMapping("/food_expenses")
public class FoodExpensesController {
    private static final Logger log = LoggerFactory.getLogger(FoodExpensesController.class);

    private final FoodExpensesService foodExpensesService;

    @Autowired
    public FoodExpensesController(FoodExpensesService foodExpensesService) {
        this.foodExpensesService = foodExpensesService;
    }

    @GetMapping("/api/sum_food_expenses")
    public ModelAndView getSumFoodExpenses() {
        ModelAndView modelAndView = new ModelAndView("personalExpenses/food/getSumExpensesFood");
        double totalExpenses;
        totalExpenses = foodExpensesService.sumExpenseFood();
        modelAndView.addObject("totalExpenses", totalExpenses);
        return modelAndView;
    }


    @GetMapping("/api/sum_expenses_food_between_date")
    public ModelAndView getSumExpensesFoodBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("personalExpenses/food/getSumExpensesFood");
        double totalExpenses;
        if(startDate == null || endDate == null) {
            totalExpenses = foodExpensesService.calculateTotalFoodExpenseForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalExpenses = foodExpensesService.calculateTotalFoodExpenseForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);

        return modelAndView;
    }
}
