package ua.ypon.accounting.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 15.01.2024
 */
@Entity
@Table(name = "business_expenses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusinessExpenses {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fuel")
    private BigDecimal fuel;

    @Column(name = "maintenance")
    private BigDecimal maintenance;

    @Column(name = "salary_v")
    private BigDecimal salaryValya;

    @Column(name = "salary_i")
    private BigDecimal salaryIra;

    @Column(name = "utility_and_water")
    private BigDecimal utilityAndWater;

    @Column(name = "rent")
    private BigDecimal rent;

    @Column(name = "tax_single")
    private BigDecimal taxSingle;

    @Column(name = "tax_pension")
    private BigDecimal taxPension;
    
    @Column(name = "tax_war")
    private BigDecimal taxWar;

    @Column(name = "owner")
    private BigDecimal owner;

    @Column(name = "suppliers")
    private BigDecimal suppliers;

    @Column(name = "date_expense_business")
    private LocalDate dateExpensesBusiness;

}
