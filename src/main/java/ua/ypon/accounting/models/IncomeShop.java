package ua.ypon.accounting.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 15.01.2024
 */
@Entity
@Table(name = "Income_shop")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncomeShop {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "income_cash")
    private BigDecimal incomeCash;

    @Column(name = "income_cashless")
    private BigDecimal incomeCashless;

    @Column(name = "income_other")
    private BigDecimal incomeOther;

    @Column(name = "date_income")
    private LocalDate dateIncome;

}
