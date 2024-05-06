package ua.ypon.accounting.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.models.IncomeType;
import ua.ypon.accounting.services.IncomeService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ua.ypon 25.01.2024
 */
@RestController
@RequestMapping("income")
public class IncomeController {

    private static final Logger log = LoggerFactory.getLogger(IncomeService.class);

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/api/income")
    public List<IncomeShop> getAllIncome() {
        log.info("Income.getAllIncome()");
        return incomeService.findAll();
    }

    @PostMapping("/api/income")
    public IncomeShop addIncome(@RequestBody IncomeShop incomeShop) {
        incomeShop.setDateIncome(incomeShop.getDateIncome());

        return incomeService.save(incomeShop);
    }

    @GetMapping("/api/sum_income_between_date")
    public ModelAndView getSumIncomeBetweenDate(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String incomeType) {
        ModelAndView modelAndView = new ModelAndView("income/getSumIncome");
        double totalIncome;
        IncomeType incomeTypeEnum = null;
        if(incomeType != null && !incomeType.isEmpty()) {
            try {
                // Якщо incomeType вказано, конвертуємо його в Enum
                incomeTypeEnum = IncomeType.valueOf(incomeType);
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
                // Обробка випадку, коли значення incomeType не відповідає жодному елементу Enum
            }
        }
        if(startDate == null || endDate == null) {
            totalIncome = incomeService.getSumIncome(LocalDate.now(), LocalDate.now(), incomeTypeEnum);
        } else {
            totalIncome = incomeService.getSumIncome(startDate, endDate, incomeTypeEnum);
        }
        if(incomeType != null && !incomeType.isEmpty()) {
            modelAndView.addObject("incomeType", incomeType);
        }
        modelAndView.addObject("totalIncome", totalIncome);

        return modelAndView;
    }
}
