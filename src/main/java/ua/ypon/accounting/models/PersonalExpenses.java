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
@Table(name = "Personal_expenses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalExpenses {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_expense_personal")
    private LocalDate dateExpensePersonal;

    @Column(name = "food_expense")
    private double foodExpense;

    @Column(name = "utility_expense")
    private double utilityExpense;

    @Column(name = "other_expense")
    private double otherExpense;

    }

