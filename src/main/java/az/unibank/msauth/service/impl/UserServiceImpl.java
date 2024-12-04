package az.unibank.msauth.service.impl;

import az.unibank.msauth.repository.UserRepository;
import az.unibank.msauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static az.unibank.msauth.model.enums.ExceptionMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND.getTranslationEn()));
        userEntity.setPassword(userEntity.getPassword());
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }
}