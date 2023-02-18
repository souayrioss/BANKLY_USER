package org.roronoa.banklyuser.service.Imp;

import lombok.RequiredArgsConstructor;

import org.roronoa.banklyuser.config.JwtUtils;
import org.roronoa.banklyuser.dto.AuthDto;
import org.roronoa.banklyuser.entity.Role;
import org.roronoa.banklyuser.entity.User;
import org.roronoa.banklyuser.repository.UserRepository;
import org.roronoa.banklyuser.service.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImp implements IUserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public User save(User userReq) {
        var user = User.builder()
                .firstName(userReq.getFirstName())
                .lastName(userReq.getLastName())
                .email(userReq.getEmail())
                .password(passwordEncoder.encode(userReq.getPassword()))
                .role(Role.USER)
                .uuid(UUID.randomUUID().toString())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUser(String uuid) {
        return null;
    }

    @Override
    public List<User> getListUsers() {
        return null;
    }

    @Override
    public User findByEmail(AuthDto authDto) {
        User user = userRepository.findByEmail(authDto.getEmail()).orElseThrow();
        if (passwordEncoder.matches(authDto.getPassword(),user.getPassword()))
        return user;
        return null;
    }
    @Override
    public User validateToken(String token) {
        final String userEmail = jwtUtils.extractUsername(token);
        User user = userRepository.findByEmail(userEmail).get();
        if (userEmail.equals(user.getEmail()) && !jwtUtils.isTokenExpired(token)) {
            jwtUtils.generateToken(user);
            return user;
        } else {
            return null;
        }
    }
}