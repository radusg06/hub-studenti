package ro.hubstudentesc.dto.faculties;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import ro.hubstudentesc.enums.facultiesEnums.DegreeLevel;

import java.math.BigDecimal;
import java.util.List;

public record AIRankingRequestDto(
        @DecimalMin("0.00")
        BigDecimal budgetLimit,

        List<String> preferredCities,

        List<String> interests,

        @DecimalMin("1.00")
        @DecimalMax("10.00")
        BigDecimal highschoolGrade,

        DegreeLevel preferredDegree
) {
}