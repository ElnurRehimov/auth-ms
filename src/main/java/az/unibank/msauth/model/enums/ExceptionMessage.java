package az.unibank.msauth.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {

    USER_NOT_FOUND(
           "USER_NOT_FOUND",
    "Istifadəçi tapılmadı",
    "User not found",
    "пользователь не найден"
    ),

    INVALID_USER(
            "INVALID_USER",
            "Istifadəçi məlumatı yanlışdır",
            "User credentials are wrong",
            "Учетные данные пользователя неверны"
    ),

    INTERNAL_ERROR("INTERNAL_ERROR",
            "Sistem xətası.",
            "System error.",
            "Сбой системы."
    );

    private final String code;
    private final String translationAz;
    private final String translationEn;
    private final String translationRu;

    public static String getExceptionTranslation(String code, Language language) {
        return Arrays.stream(ExceptionMessage.values())
                .filter(exception -> exception.getCode().equals(code))
                .findFirst()
                .map(exception -> getTranslationByLanguage(exception, language))
                .orElse(getTranslationByLanguage(INTERNAL_ERROR, language));
    }

    private static String getTranslationByLanguage(ExceptionMessage exception, Language language) {
        return switch (language) {
            case AZE -> exception.getTranslationAz();
            case ENG -> exception.getTranslationEn();
            default -> exception.getTranslationRu();
        };
    }
}
