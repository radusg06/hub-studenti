package ro.hubstudentesc.dto.jobs;

import java.math.BigDecimal;

public record JobSalaryDto(
        BigDecimal min,
        BigDecimal max,
        String currency
) {
}
