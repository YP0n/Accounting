package ua.ypon.accounting.controllers.personal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.personal.UtilityExpenseService;

import java.time.LocalDate;

/**
 * @author ua.ypon 23.02.2024
 */
@Controller
@RequestMapping("/utility_expenses")
public class UtilityExpensesController {

    private static final Logger log = LoggerFactory.getLogger(UtilityExpensesController.class);

    private final UtilityExpenseService utilityExpenseService;


    @Autowired
    public UtilityExpensesController(UtilityExpenseService utilityExpenseService) {
        this.utilityExpenseService = utilityExpenseService;
    }

    @GetMapping("/api/sum-utility-expenses")
    public double getSumUtilityExpenses() {
        return utilityExpenseService.sumExpensesUtility();
    }

    @GetMapping("/api/sum-expenses-utility-between-date")
    public ModelAndView getSumExpensesUtilityBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("personalExpenses/getSumExpensesUtility");
        double totalExpenses;
        if(startDate == null || endDate == null) {
            totalExpenses = utilityExpenseService.calculateTotalUtilityExpenseForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalExpenses = utilityExpenseService.calculateTotalUtilityExpenseForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);

        return modelAndView;
    }

}
