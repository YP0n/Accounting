package ua.ypon.accounting.controllers.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.business.carExpenses.CarExpensesService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 02.03.2024
 */
@Controller
@RequestMapping("/car_expenses")
@RequiredArgsConstructor
public class CarExpensesController {
    private final CarExpensesService service;
    @GetMapping("/api/total_sum_fuel")
    public ModelAndView getTotalSumExpensesFuel() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/carExpenses/getSumExpensesFuel");
        BigDecimal totalExpenses;
        totalExpenses = service.sumExpensesFuel();
        modelAndView.addObject("totalExpenses", totalExpenses);
        return modelAndView;
    }
    @GetMapping("/api/total_sum_maintenance")
    public ModelAndView getTotalSumExpensesMaintenance() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/carExpenses/getSumExpensesMaintenance");
        BigDecimal totalExpenses = service.sumExpensesMaintenance();
        modelAndView.addObject("totalExpenses", totalExpenses);
        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_fuel_between_date")
    public ModelAndView getSumExpensesFuelBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/carExpenses/getSumExpensesFuel");
        BigDecimal totalExpenses;
        if (startDate == null || endDate == null) {
            totalExpenses = service.calculateTotalFuelExpenseForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalExpenses = service.calculateTotalFuelExpenseForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);

        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_maintenance_between_date")
    public ModelAndView getSumExpensesMaintenanceBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/carExpenses/getSumExpensesMaintenance");
        BigDecimal totalExpenses;
        if (startDate == null || endDate == null) {
            totalExpenses = service.calculateTotalMaintenanceExpenseForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalExpenses = service.calculateTotalMaintenanceExpenseForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalExpenses", totalExpenses);

        return modelAndView;
    }
}
