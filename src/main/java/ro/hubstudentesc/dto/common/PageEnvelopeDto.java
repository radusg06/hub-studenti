package ro.hubstudentesc.dto.common;

import java.util.List;

public record PageEnvelopeDto<T>(
        List<T> data,
        PaginationDto pagination
) {
}
