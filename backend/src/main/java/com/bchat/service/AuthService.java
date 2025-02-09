package com.bchat.service;

import com.bchat.model.dto.LoginDto;
import com.bchat.model.dto.RegisterDTO;
import com.bchat.model.po.User;

public interface AuthService {
    String login(LoginDto loginDto);

    void register(RegisterDTO registerDTO);

    User logout();
}