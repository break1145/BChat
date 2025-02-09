package com.bchat.service.impl;

import com.bchat.model.dto.LoginDto;
import com.bchat.model.dto.RegisterDTO;
import com.bchat.model.po.User;
import com.bchat.repository.UserRepository;
import com.bchat.service.AuthService;
import com.bchat.utils.AuthUtil;
import com.bchat.utils.JwtTokenProvider;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenProvider jwtTokenProvider;
    @Resource
    private AuthUtil authUtil;


    public AuthServiceImpl(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        User user = new User(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User logout() {
        // 由于使用jwt token做权限校验，登出时让前端抛弃即可，接口暂时保留（后面如果使用cookie，要做清除）
        User user = authUtil.getCurrentUser();
        return user;
    }



}