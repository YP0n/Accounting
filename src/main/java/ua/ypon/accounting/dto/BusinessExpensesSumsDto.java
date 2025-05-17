package ua.ypon.accounting.dto;

import java.math.BigDecimal;

public record BusinessExpensesSumsDto(
        BigDecimal fuel,
        BigDecimal maintenance,
        BigDecimal salaryValya,
        BigDecimal salaryIra,
        BigDecimal utilityAndWater,
        BigDecimal rent,
        BigDecimal taxSingle,
        BigDecimal taxPension,
        BigDecimal taxWar,
        BigDecimal owner,
        BigDecimal suppliers
) {
}
