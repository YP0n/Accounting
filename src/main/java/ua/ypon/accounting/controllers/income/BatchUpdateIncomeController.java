package ua.ypon.accounting.controllers.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.ypon.accounting.models.BatchForm;
import ua.ypon.accounting.models.IncomeShop;
import ua.ypon.accounting.services.income.IncomeService;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ua.ypon 04.03.2024
 */
@Controller
@RequestMapping("/batch_income_update")
@SessionAttributes("batchForm")
public class BatchUpdateIncomeController {

    private final IncomeService service;

    @Autowired
    public BatchUpdateIncomeController(IncomeService service) {
        this.service = service;
    }

    @GetMapping("/new_batch")
    public String showBatchForm(Model model) {
        if(!model.containsAttribute("batchForm")) {
            model.addAttribute("batchForm", new BatchForm());
        }
        return "income/incomePOSTBatch";
    }

    @PostMapping ("/process_batch")
    public String processBatch(@Validated @ModelAttribute("batchForm") BatchForm batchForm, Model model) {
        LocalDate startDate = batchForm.getStartDate();
        LocalDate endDate = batchForm.getEndDate();

        List<LocalDate> dateIsRange = new ArrayList<>();
        List<IncomeShop> incomeShopList = new ArrayList<>();

        while (!startDate.isAfter(endDate)) {
            IncomeShop incomeShop = new IncomeShop();
            incomeShop.setDateIncome(startDate);
            incomeShopList.add(incomeShop);
            dateIsRange.add(startDate);
            startDate = startDate.plusDays(1);
        }
        batchForm.setIncomeShopList(incomeShopList);
        model.addAttribute("dates", dateIsRange);

        return "income/incomePOSTBatch";
    }

    @PostMapping("add_income")
    public ResponseEntity<?> addIncome(@Validated @ModelAttribute("batchForm") BatchForm batchForm) {
        try {
            List<IncomeShop> incomeShopList = batchForm.getIncomeShopList();
            service.saveIncome(incomeShopList);
            String redirectUrl = "/income_shop/show";
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(redirectUrl));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save income: " + e.getMessage());
        }
    }
}
