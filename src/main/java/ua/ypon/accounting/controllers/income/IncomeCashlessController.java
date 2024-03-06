package ua.ypon.accounting.controllers.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.income.IncomeCashlessService;

import java.time.LocalDate;

/**
 * @author ua.ypon 04.03.2024
 */
@Controller
@RequestMapping("/cashless_income")
public class IncomeCashlessController {

    private final IncomeCashlessService service;

    @Autowired
    public IncomeCashlessController(IncomeCashlessService service) {
        this.service = service;
    }

    @GetMapping("/api/total_sum_cashless")
    public ModelAndView getTotalSumIncomeCashless() {
        ModelAndView modelAndView = new ModelAndView("income/cashless/getSumIncomeCashless");
        double totalIncomeCashless;
        totalIncomeCashless = service.sumIncomeCashless();
        modelAndView.addObject("totalIncomeCashless", totalIncomeCashless);
        return modelAndView;
    }

    @GetMapping("/api/total_sum_cashless_between_date")
    public ModelAndView getTotalSumIncomeCashlessBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("income/cashless/getSumIncomeCashless");
        double totalIncomeCashless;
        if(startDate == null || endDate == null) {
            totalIncomeCashless = service.calculateTotalIncomeCashlessForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalIncomeCashless = service.calculateTotalIncomeCashlessForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalIncomeCashless", totalIncomeCashless);

        return modelAndView;
    }
}
