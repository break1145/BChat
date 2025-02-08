package com.bchat.service;

import com.bchat.model.dto.LoginDto;
import com.bchat.model.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDto loginDto);

    void register(RegisterDTO registerDTO);
}