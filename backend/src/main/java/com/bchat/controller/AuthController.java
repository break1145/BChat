package com.bchat.controller;

import com.bchat.model.dto.LoginDto;
import com.bchat.model.request.RefreshTokenRequest;
import com.bchat.model.response.JWTAuthResponse;
import com.bchat.service.AuthService;
import com.bchat.utils.JwtTokenProvider;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private AuthService authService;
    @Resource
    private JwtTokenProvider jwtTokenProvider;

    // Login REST API
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        log.info(loginDto.toString());
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            String newToken = jwtTokenProvider.generateTokenFromRefreshToken(refreshToken);
            return ResponseEntity.ok(new JWTAuthResponse(newToken));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
    }
}