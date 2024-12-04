package az.unibank.msauth.controller;

import az.unibank.msauth.model.dto.LoginDto;
import az.unibank.msauth.model.enums.Attribute;
import az.unibank.msauth.model.enums.Language;
import az.unibank.msauth.model.view.AuthDetailsView;
import az.unibank.msauth.model.view.AuthenticationView;
import az.unibank.msauth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationView> signIn(
            @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, defaultValue = "AZE") Language language,
            @RequestBody LoginDto loginDto
    ) {
        return ResponseEntity.ok(authenticationService.signIn(loginDto, language));
    }

    @GetMapping("/internal/check")
    public ResponseEntity<AuthDetailsView> checkAuthentication(
            @RequestParam Attribute attribute, HttpServletRequest request
    ) {
        return ResponseEntity.ok(authenticationService.checkAuthentication(
                request.getHeader(HttpHeaders.AUTHORIZATION), attribute)
        );
    }
}