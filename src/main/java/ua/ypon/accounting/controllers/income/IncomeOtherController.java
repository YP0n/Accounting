package ua.ypon.accounting.controllers.income;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.income.IncomeOtherService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 04.03.2024
 */
@Controller
@RequestMapping("/other_income")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class IncomeOtherController {

    private final IncomeOtherService service;
    @GetMapping("/api/total_sum_other")
    public ModelAndView getTotalSumIncomeOther() {
        
        ModelAndView modelAndView = new ModelAndView("income/other/getSumIncomeOther");
        
        BigDecimal totalIncomeOther;
        totalIncomeOther = service.sumIncomeOther();
        modelAndView.addObject("totalIncomeOther", totalIncomeOther);
        
        return modelAndView;
    }

    @GetMapping("/api/total_sum_other_between_date")
    public ModelAndView getSumIncomeOtherBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        
        ModelAndView modelAndView = new ModelAndView("income/other/getSumIncomeOther");
        
        BigDecimal totalIncomeOther;
        
        if(startDate == null || endDate == null) {
            totalIncomeOther = service.calculateTotalIncomeOtherForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalIncomeOther = service.calculateTotalIncomeOtherForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalIncomeOther", totalIncomeOther);

        return modelAndView;
    }
}
