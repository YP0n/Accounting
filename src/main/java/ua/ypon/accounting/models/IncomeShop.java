package ua.ypon.accounting.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author ua.ypon 15.01.2024
 */
@Entity
@Table(name = "Income_shop")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IncomeShop {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "income_cash")
    private double incomeCash;

    @Column(name = "income_cashless")
    private double incomeCashless;

    @Column(name = "income_other")
    private double incomeOther;

    @Column(name = "date_income")
    private LocalDate dateIncome;

}
