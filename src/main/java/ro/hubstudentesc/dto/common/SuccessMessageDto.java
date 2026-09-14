package ro.hubstudentesc.dto.common;

public record SuccessMessageDto(
        boolean success,
        String message
) {
}
