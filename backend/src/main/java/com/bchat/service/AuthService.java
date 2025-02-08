package com.bchat.service;

import com.bchat.model.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}