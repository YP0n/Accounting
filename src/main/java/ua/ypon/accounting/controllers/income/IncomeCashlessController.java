package ua.ypon.accounting.controllers.income;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.income.IncomeCashlessService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 04.03.2024
 */
@Controller
@RequestMapping("/cashless_income")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class IncomeCashlessController {

    private final IncomeCashlessService service;
    @GetMapping("/api/total_sum_cashless")
    public ModelAndView getTotalSumIncomeCashless() {
        ModelAndView modelAndView = new ModelAndView("income/cashless/getSumIncomeCashless");
        BigDecimal totalIncomeCashless;
        totalIncomeCashless = service.sumIncomeCashless();
        modelAndView.addObject("totalIncomeCashless", totalIncomeCashless);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_cashless_between_date")
    public ModelAndView getTotalSumIncomeCashlessBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("income/cashless/getSumIncomeCashless");
        BigDecimal totalIncomeCashless;
        if(startDate == null || endDate == null) {
            totalIncomeCashless = service.calculateTotalIncomeCashlessForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalIncomeCashless = service.calculateTotalIncomeCashlessForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalIncomeCashless", totalIncomeCashless);

        return modelAndView;
    }
}
