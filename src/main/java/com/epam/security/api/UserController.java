package com.epam.security.api;

import com.epam.security.entity.RoleEntity;
import com.epam.security.entity.UserEntity;
import com.epam.security.repository.RoleRepository;
import com.epam.security.repository.UserRepository;
import com.epam.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        RoleEntity roleEntity = RoleEntity.builder()
                .roleName("USER")
                .build();

        RoleEntity roleToSave = this.roleRepository.findByRoleName("USER")
                .orElseGet(() -> roleEntity);
        userEntity.setRoles(List.of(roleToSave));
        return this.userRepository.save(userEntity);
    }

    @PostMapping("login")
    public String login(@RequestBody UserEntity userEntity){
         Authentication authentication = this.authenticationManager.authenticate(new
                 UsernamePasswordAuthenticationToken(userEntity.getUsername(),
                 userEntity.getPassword()));
         return this.tokenService.generateToken(authentication);
    }
}
