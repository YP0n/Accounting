package ua.ypon.accounting.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ua.ypon 15.01.2024
 */
@Entity
@Table(name = "personal_expenses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalExpenses {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_expense_personal")
    private LocalDate dateExpensePersonal;

    @Column(name = "food_expense")
    private BigDecimal foodExpense;

    @Column(name = "utility_expense")
    private BigDecimal utilityExpense;

    @Column(name = "other_expense")
    private BigDecimal otherExpense;

}
