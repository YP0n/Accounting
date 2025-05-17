package ua.ypon.accounting.dto;

import java.math.BigDecimal;

public record IncomeSumsDto(
        
        BigDecimal totalCash,
        BigDecimal totalCashless,
        BigDecimal totalOther
) {
}
