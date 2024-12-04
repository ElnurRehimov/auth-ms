package az.unibank.msauth.service.impl;

import az.unibank.msauth.exception.AccessDeniedException;
import az.unibank.msauth.exception.InvalidUserException;
import az.unibank.msauth.exception.UserNotFoundException;
import az.unibank.msauth.model.UserClaims;
import az.unibank.msauth.model.dto.LoginDto;
import az.unibank.msauth.model.entity.User;
import az.unibank.msauth.model.enums.Attribute;
import az.unibank.msauth.model.enums.Language;
import az.unibank.msauth.model.view.AuthDetailsView;
import az.unibank.msauth.model.view.AuthenticationView;
import az.unibank.msauth.repository.UserRepository;
import az.unibank.msauth.service.AuthenticationService;
import az.unibank.msauth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static az.unibank.msauth.model.enums.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationView signIn(LoginDto request, Language language) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(
                        getExceptionTranslation(USER_NOT_FOUND.getCode(), language), USER_NOT_FOUND.getCode()
                ));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidUserException(
                    getExceptionTranslation(INVALID_USER.getCode(), language), INVALID_USER.getCode()
            );
        }
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtUtil.generateToken(getClaims(user));
        return AuthenticationView.builder().token(jwt).build();
    }

    @Override
    public AuthDetailsView checkAuthentication(String token, Attribute attribute) {
        var auth = jwtUtil.extractClaims(token);
        if (!auth.getRole().getAttributes().contains(attribute)) {
            throw new AccessDeniedException("Access denied","403");
        }
        return auth;
    }

    private UserClaims getClaims(User user) {
        var userClaims = new UserClaims();
        userClaims.setUserId(user.getId());
        userClaims.setUsername(user.getUsername());
        userClaims.setRole(user.getRole().getName());
        return userClaims;
    }
}