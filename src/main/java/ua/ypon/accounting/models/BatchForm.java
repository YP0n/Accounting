package ua.ypon.accounting.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ua.ypon 25.02.2024
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BatchForm {
    @NotNull(message = "Початкова дата не може бути порожньою")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Кінцева дата не може бути порожньою")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private List<PersonalExpenses> expensesList;
    private List<BusinessExpenses> expensesBusinessList;
    private List<IncomeShop> incomeShopList;
}
