package com.fg.system.modules.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {
    @RequestMapping("/login")
    public String login(){
        return "admin/login";
    }
    @RequestMapping("/index")
    public String index(){
        return "admin/index";
    }
}
