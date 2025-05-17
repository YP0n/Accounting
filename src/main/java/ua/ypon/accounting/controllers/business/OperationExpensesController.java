package ua.ypon.accounting.controllers.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.business.AvailableSumService;
import ua.ypon.accounting.services.business.operationExpenses.OperationExpensesService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 02.03.2024
 */
@Controller
@RequestMapping("/operation_expenses")
@RequiredArgsConstructor
public class OperationExpensesController {
    private final OperationExpensesService service;
    private final AvailableSumService availableSumService;
    
    @GetMapping("/api/total_sum_salary_v")
    public ModelAndView getTotalSumExpensesSalaryV() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesSalaryV");
        BigDecimal totalExpensesSalaryV = service.sumExpensesSalaryV();
        modelAndView.addObject("totalExpensesSalaryV", totalExpensesSalaryV);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_salary_i")
    public ModelAndView getTotalSumExpensesSalaryI() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesSalaryI");
        BigDecimal totalExpensesSalaryI = service.sumExpensesSalaryI();
        modelAndView.addObject("totalExpensesSalaryI", totalExpensesSalaryI);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_utility")
    public ModelAndView getTotalSumExpensesUtility() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesUtility");
        BigDecimal totalExpensesUtility = service.sumExpensesUtility();
        modelAndView.addObject("totalExpensesUtility", totalExpensesUtility);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_rent")
    public ModelAndView getTotalSumExpensesRent() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesRent");
        BigDecimal totalExpensesRent = service.sumExpensesRent();
        modelAndView.addObject("totalExpensesRent", totalExpensesRent);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_tax_single")
    public ModelAndView getTotalSumExpensesTaxSingle() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesTaxSingle");
        BigDecimal totalExpensesTaxSingle = service.sumExpensesTaxSingle();
        modelAndView.addObject("totalExpensesTaxSingle", totalExpensesTaxSingle);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_tax_pension")
    public ModelAndView getTotalSumExpensesTaxPension() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesTaxPension");
        BigDecimal totalExpensesTaxPension = service.sumExpensesTaxPension();
        modelAndView.addObject("totalExpensesTaxPension", totalExpensesTaxPension);
        return modelAndView;
    }
    
    @GetMapping("/api/total_sum_tax_war")
    public ModelAndView getTotalSumExpensesTaxWar() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesTaxWar");
        BigDecimal totalExpensesTaxWar = service.sumExpensesTaxWar();
        modelAndView.addObject("totalExpensesTaxWar", totalExpensesTaxWar);
        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_salary_v_between_date")
    public ModelAndView getSumExpensesSalaryVBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesSalaryV");
        BigDecimal totalExpensesSalaryV;
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
        BigDecimal totalExpensesSalaryI;
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
        BigDecimal totalExpensesUtility;
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
        BigDecimal totalExpensesRent;
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
        BigDecimal totalExpensesTaxSingle;
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
        BigDecimal totalExpensesTaxPension;
        if (startDate == null || endDate == null) {
            totalExpensesTaxPension = service.calculateTotalTaxPension(LocalDate.now(), LocalDate.now());
        } else {
            totalExpensesTaxPension = service.calculateTotalTaxPension(startDate, endDate);
        }
        modelAndView.addObject("totalExpensesTaxPension", totalExpensesTaxPension);

        return modelAndView;
    }
    
    @GetMapping("/api/sum_expenses_tax_war_between_date")
    public ModelAndView getSumExpensesTaxWarBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/operationExpenses/getSumExpensesTaxWar");
        BigDecimal totalExpensesTaxWar;
        if (startDate == null || endDate == null) {
            totalExpensesTaxWar = service.calculateTotalTaxWar(LocalDate.now(), LocalDate.now());
        } else {
            totalExpensesTaxWar = service.calculateTotalTaxWar(startDate, endDate);
        }
        modelAndView.addObject("totalExpensesTaxWar", totalExpensesTaxWar);
    
        return modelAndView;
    }
    
    @GetMapping("/available_sum")
    public String getTotalSumAvailable(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            Model model) {
        
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(8);
        }
        
        if (endDate == null) {
            endDate = LocalDate.now().minusDays(1);
        }
        
        BigDecimal totalAvailableSum = availableSumService.calculateAvailableSum(startDate, endDate);
        
        model.addAttribute("totalAvailableSum", totalAvailableSum);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "businessExpenses/purchasingExpenses/getAvailableSumForPurchases";
    }
    
}
