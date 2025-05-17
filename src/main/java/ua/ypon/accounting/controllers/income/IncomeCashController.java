package ua.ypon.accounting.controllers.income;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.income.IncomeCashService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 04.03.2024
 */
@Controller
@RequestMapping("/cash_income")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class IncomeCashController {

    private final IncomeCashService service;
    
    @GetMapping("/api/total_sum_cash")
    public ModelAndView getTotalSumIncomeCash() {
        ModelAndView modelAndView = new ModelAndView("income/cash/getSumIncomeCash");
        BigDecimal totalIncomeCash;
        totalIncomeCash = service.sumIncomeCash();
        modelAndView.addObject("totalIncomeCash", totalIncomeCash);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_cash_between_date")
    public ModelAndView getTotalSumIncomeCashBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("income/cash/getSumIncomeCash");
        BigDecimal totalIncomeCash;
        if(startDate == null || endDate == null) {
            totalIncomeCash = service.calculateTotalIncomeCashForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalIncomeCash = service.calculateTotalIncomeCashForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalIncomeCash", totalIncomeCash);

        return modelAndView;
    }
}
