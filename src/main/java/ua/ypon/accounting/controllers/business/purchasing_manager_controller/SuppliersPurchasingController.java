package ua.ypon.accounting.controllers.business.purchasing_manager_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.services.business.purchasingManager.SuppliersPurchasingService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 02.03.2024
 */
@Controller
@RequestMapping("/suppliers_purchasing")
public class SuppliersPurchasingController {

    private final SuppliersPurchasingService service;

    @Autowired
    public SuppliersPurchasingController(SuppliersPurchasingService service) {
        this.service = service;
    }

    @GetMapping("/api/total_sum_purchasing_suppliers")
    public ModelAndView getTotalSumPurchasingSuppliers() {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/purchasingExpenses/getSumPurchasingSuppliers");
        BigDecimal totalPurchasingSuppliers;
        totalPurchasingSuppliers = service.sumPurchasingSuppliers();
        modelAndView.addObject("totalPurchasingSuppliers", totalPurchasingSuppliers);
        return modelAndView;
    }

    @GetMapping("/api/sum_expenses_purchasing_suppliers_between_date")
    public ModelAndView getSumPurchasingSuppliersBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        ModelAndView modelAndView = new ModelAndView("businessExpenses/purchasingExpenses/getSumPurchasingSuppliers");
        BigDecimal totalPurchasingSuppliers;
        if (startDate == null || endDate == null) {
            totalPurchasingSuppliers = service.calculateTotalPurchasingSuppliersForPeriod(LocalDate.now(), LocalDate.now());
        } else {
            totalPurchasingSuppliers = service.calculateTotalPurchasingSuppliersForPeriod(startDate, endDate);
        }
        modelAndView.addObject("totalPurchasingSuppliers", totalPurchasingSuppliers);

        return modelAndView;
    }
}
