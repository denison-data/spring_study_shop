package com.denison.shops.controller.api;

import com.denison.shops.dto.api.LoginRequestDto;
import com.denison.shops.dto.api.LoginResponseDto;
import com.denison.shops.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value="Authorization", required = false) String bearerToken) {
        authService.logout();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkLogin(@RequestHeader(value = "Authorization", required = false) String bearerToken) {
        return ResponseEntity.ok(authService.checkLogin(bearerToken));
    }
}

