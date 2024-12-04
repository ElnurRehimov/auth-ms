package az.unibank.msauth.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Language {
    AZE("az"),
    RU("ru"),
    ENG("en");

    private final String propertyName;
}
