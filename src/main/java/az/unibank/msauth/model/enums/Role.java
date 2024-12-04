package az.unibank.msauth.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(List.of(Attribute.GET_CARD,Attribute.GET_CARD_USER_ID, Attribute.INSERT_CARD, Attribute.DELETE_CARD, Attribute.UPDATE_CARD)),
    USER(List.of(Attribute.GET_CARD));

    private final List<Attribute> attributes;
}
