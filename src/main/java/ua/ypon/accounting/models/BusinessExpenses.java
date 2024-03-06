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
    private double fuel;

    @Column(name = "maintenance")
    private double maintenance;

    @Column(name = "salary_v")
    private double salaryValya;

    @Column(name = "salary_i")
    private double salaryIra;

    @Column(name = "utility_and_water")
    private double utilityAndWater;

    @Column(name = "rent")
    private double rent;

    @Column(name = "tax_single")
    private double taxSingle;

    @Column(name = "tax_pension")
    private double taxPension;

    @Column(name = "owner")
    private double owner;

    @Column(name = "suppliers")
    private double suppliers;

    @Column(name = "date_expense_business")
    private LocalDate dateExpensesBusiness;

}
