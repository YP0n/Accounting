package ua.ypon.accounting.controllers.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.business.operationExpenses.OperationExpensesService;

import java.time.LocalDate;

/**
 * @author ua.ypon 02.03.2024
 */
@Controller
@RequestMapping("/operation_expenses")
public class OperationExpensesController {

    private final OperationExpensesService service;

    @Autowired
    public OperationExpensesController(OperationExpensesService service) {
        this.service = service;
    }

    @GetMapping("/api/total_sum_salary_v")
    public ModelAndView getTotalSumExpensesSalaryV() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesSalaryV");
        double totalExpensesSalaryV;
        totalExpensesSalaryV = service.sumExpensesSalaryV();
        modelAndView.addObject("totalExpensesSalaryV", totalExpensesSalaryV);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_salary_i")
    public ModelAndView getTotalSumExpensesSalaryI() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesSalaryI");
        double totalExpensesSalaryI;
        totalExpensesSalaryI = service.sumExpensesSalaryI();
        modelAndView.addObject("totalExpensesSalaryI", totalExpensesSalaryI);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_utility")
    public ModelAndView getTotalSumExpensesUtility() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesUtility");
        double totalExpensesUtility;
        totalExpensesUtility = service.sumExpensesUtility();
        modelAndView.addObject("totalExpensesUtility", totalExpensesUtility);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_rent")
    public ModelAndView getTotalSumExpensesRent() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesRent");
        double totalExpensesRent;
        totalExpensesRent = service.sumExpensesRent();
        modelAndView.addObject("totalExpensesRent", totalExpensesRent);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_tax_single")
    public ModelAndView getTotalSumExpensesTaxSingle() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesTaxSingle");
        double totalExpensesTaxSingle;
        totalExpensesTaxSingle = service.sumExpensesTaxSingle();
        modelAndView.addObject("totalExpensesTaxSingle", totalExpensesTaxSingle);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_tax_pension")
    public ModelAndView getTotalSumExpensesTaxPension() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesTaxPension");
        double totalExpensesTaxPension;
        totalExpensesTaxPension = service.sumExpensesTaxPension();
        modelAndView.addObject("totalExpensesTaxPension", totalExpensesTaxPension);
        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_salary_v_between_date")
    public ModelAndView getSumExpensesSalaryVBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesSalaryV");
        double totalExpensesSalaryV;
        if (startDate == null || endDate == null) {
            totalExpensesSalaryV = service.calculateTotalSalaryV(LocalDate.now(), LocalDate.now());
        } else {
            totalExpensesSalaryV = service.calculateTotalSalaryV(startDate, endDate);
        }
        modelAndView.addObject("totalExpensesSalaryV", totalExpensesSalaryV);

        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_salary_i_between_date")
    public ModelAndView getSumExpensesSalaryIBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesSalaryI");
        double totalExpensesSalaryI;
        if (startDate == null || endDate == null) {
            totalExpensesSalaryI = service.calculateTotalSalaryI(LocalDate.now(), LocalDate.now());
        } else {
            totalExpensesSalaryI = service.calculateTotalSalaryI(startDate, endDate);
        }
        modelAndView.addObject("totalExpensesSalaryI", totalExpensesSalaryI);

        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_utility_between_date")
    public ModelAndView getSumExpensesUtilityBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesUtility");
        double totalExpensesUtility;
        if (startDate == null || endDate == null) {
            totalExpensesUtility = service.calculateTotalUtility(LocalDate.now(), LocalDate.now());
        } else {
            totalExpensesUtility = service.calculateTotalUtility(startDate, endDate);
        }
        modelAndView.addObject("totalExpensesUtility", totalExpensesUtility);

        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_rent_between_date")
    public ModelAndView getSumExpensesRentBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesRent");
        double totalExpensesRent;
        if (startDate == null || endDate == null) {
            totalExpensesRent = service.calculateTotalRent(LocalDate.now(), LocalDate.now());
        } else {
            totalExpensesRent = service.calculateTotalRent(startDate, endDate);
        }
        modelAndView.addObject("totalExpensesRent", totalExpensesRent);

        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_tax_single_between_date")
    public ModelAndView getSumExpensesTaxSingleBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesTaxSingle");
        double totalExpensesTaxSingle;
        if (startDate == null || endDate == null) {
            totalExpensesTaxSingle = service.calculateTotalTaxSingle(LocalDate.now(), LocalDate.now());
        } else {
            totalExpensesTaxSingle = service.calculateTotalTaxSingle(startDate, endDate);
        }
        modelAndView.addObject("totalExpensesTaxSingle", totalExpensesTaxSingle);

        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_tax_pension_between_date")
    public ModelAndView getSumExpensesTaxPensionBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesTaxPension");
        double totalExpensesTaxPension;
        if (startDate == null || endDate == null) {
            totalExpensesTaxPension = service.calculateTotalTaxPension(LocalDate.now(), LocalDate.now());
        } else {
            totalExpensesTaxPension = service.calculateTotalTaxPension(startDate, endDate);
        }
        modelAndView.addObject("totalExpensesTaxPension", totalExpensesTaxPension);

        return modelAndView;
    }
}
