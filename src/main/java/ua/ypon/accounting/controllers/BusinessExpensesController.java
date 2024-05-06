package ua.ypon.accounting.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.models.BusinessExpenses;
import ua.ypon.accounting.models.BusinessExpensesType;
import ua.ypon.accounting.services.BusinessExpenseService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ua.ypon 26.01.2024
 */
@RestController
@RequestMapping("business_expenses")
public class BusinessExpensesController {

    private static final Logger log = LoggerFactory.getLogger(BusinessExpenseService.class);

    private final BusinessExpenseService businessExpenseService;

    @Autowired
    public BusinessExpensesController(BusinessExpenseService businessExpenseService) {
        this.businessExpenseService = businessExpenseService;
    }

    @GetMapping("/api/business_expenses")
    public List<BusinessExpenses> getAllExpenses() {
        log.info("Expense.getAllExpenses()");
        return businessExpenseService.findAll();
    }

    @PostMapping("/api/business_expense")
    public BusinessExpenses addBusinessExpense(@RequestBody BusinessExpenses businessExpenses) {
        businessExpenses.setDateExpensesBusiness(businessExpenses.getDateExpensesBusiness());

        return businessExpenseService.save(businessExpenses);
    }

    @GetMapping("/api/get_sum_business_expenses_between_date")
    public ModelAndView getSumBusinessExpensesBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String businessExpensesType) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/getSumBusinessExpenses");
        double totalExpenses;
        BusinessExpensesType businessExpensesEnum = null;
        if(businessExpensesType != null && !businessExpensesType.isEmpty()) {
            try {
                businessExpensesEnum = BusinessExpensesType.valueOf(businessExpensesType);
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        if(startDate == null || endDate == null) {
            totalExpenses = businessExpenseService.getSumBusinessExpenses(LocalDate.now(), LocalDate.now(), businessExpensesEnum);
        }else {
            totalExpenses = businessExpenseService.getSumBusinessExpenses(startDate, endDate, businessExpensesEnum);
        }
        if(businessExpensesType != null && !businessExpensesType.isEmpty()) {
            modelAndView.addObject("businessExpensesType", businessExpensesType);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);
        return modelAndView;
    }
}
