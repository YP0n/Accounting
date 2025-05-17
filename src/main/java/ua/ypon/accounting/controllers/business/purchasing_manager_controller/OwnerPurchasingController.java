package ua.ypon.accounting.controllers.business.purchasing_manager_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.business.purchasingManager.OwnerPurchasingService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 02.03.2024
 */
@Controller
@RequestMapping("/owner_purchasing")
@RequiredArgsConstructor
public class OwnerPurchasingController {

    private final OwnerPurchasingService service;
    
    @GetMapping("/api/total_sum_purchasing_owner")
    public ModelAndView getTotalSumPurchasingOwner() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/purchasingExpenses/getSumPurchasingOwner");
        BigDecimal totalPurchasingOwner;
        totalPurchasingOwner = service.sumPurchasingOwner();
        modelAndView.addObject("totalPurchasingOwner", totalPurchasingOwner);
        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_purchasing_owner_between_date")
    public ModelAndView getSumPurchasingOwnerBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/purchasingExpenses/getSumPurchasingOwner");
        BigDecimal totalPurchasingOwner;
        if (startDate == null || endDate == null) {
            totalPurchasingOwner = service.calculateTotalPurchasingOwnerForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalPurchasingOwner = service.calculateTotalPurchasingOwnerForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalPurchasingOwner", totalPurchasingOwner);

        return modelAndView;
    }
}
