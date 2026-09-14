package ro.hubstudentesc.dto.common;

public record PaginationDto(
        int currentPage,
        int totalPages,
        long totalItems
) {
}
