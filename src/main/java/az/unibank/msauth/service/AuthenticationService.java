package az.unibank.msauth.service;

import az.unibank.msauth.model.dto.LoginDto;
import az.unibank.msauth.model.enums.Attribute;
import az.unibank.msauth.model.enums.Language;
import az.unibank.msauth.model.view.AuthDetailsView;
import az.unibank.msauth.model.view.AuthenticationView;

public interface AuthenticationService {

    AuthenticationView signIn(LoginDto request, Language language);

    AuthDetailsView checkAuthentication(String token, Attribute attribute);
}