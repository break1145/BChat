package com.bchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/{path:^(?!api).*}") // 处理所有非 API 请求
    public String forward() {
        return "forward:/index.html";
    }
}