package ro.hubstudentesc.dto.common;

public record ErrorResponseDto(
        ErrorCodeDto error
) {
    public static ErrorResponseDto of(String code) {
        return new ErrorResponseDto(new ErrorCodeDto(code));
    }

    public record ErrorCodeDto(String code) {
    }
}
