package com.example.loginauthapi.controller;

import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.domain.user.enums.UserEnum;
import com.example.loginauthapi.dto.LoginRequestDTO;
import com.example.loginauthapi.dto.RegisterRequestDTO;
import com.example.loginauthapi.dto.ResponseDTO;
import com.example.loginauthapi.infra.security.TokenService;
import com.example.loginauthapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(token, user.getName(), user.getRole().name() ));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = repository.findByEmail(body.email());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setName(body.name());
            newUser.setRole(UserEnum.USER);
            repository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(token, newUser.getName(), newUser.getRole().name() ));
        }
            return ResponseEntity.badRequest().build();

    }

    @GetMapping("/admin")
    public String admin(){
        return "hello world";
    }
}
