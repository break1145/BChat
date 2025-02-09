package com.bchat.controller;

import com.bchat.model.dto.LoginDto;
import com.bchat.model.dto.RefreshTokenDTO;
import com.bchat.common.result.Result;
import com.bchat.model.dto.RegisterDTO;
import com.bchat.model.po.User;
import com.bchat.model.vo.JWTAuthVO;
import com.bchat.service.AuthService;
import com.bchat.utils.JwtTokenProvider;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.bchat.common.result.ResultCodeEnum.APP_LOGIN_AUTH;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private AuthService authService;
    @Resource
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public Result<JWTAuthVO> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthVO jwtAuthResponse = new JWTAuthVO(token);
        log.info("用户登录成功：" + loginDto.getUsername());
        return Result.ok(jwtAuthResponse);
    }

    @PostMapping("logout")
    public Result<?> logout(){
        User user = authService.logout();
        log.info("用户退出登录：" + user.getUsername());
        return Result.ok(user);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO registerDTO){
        authService.register(registerDTO);
        log.info("用户注册成功：" + registerDTO.getUsername());
        return Result.ok();
    }

    @PostMapping("/refresh-token")
    public Result<?> refreshToken(@RequestBody RefreshTokenDTO refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            String newToken = jwtTokenProvider.generateTokenFromRefreshToken(refreshToken);
            return Result.ok(new JWTAuthVO(newToken));
        }

        return Result.build("FAIL", APP_LOGIN_AUTH);
    }
}