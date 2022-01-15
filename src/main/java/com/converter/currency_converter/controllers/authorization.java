package com.converter.currency_converter.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.converter.currency_converter.components.Security;
import com.converter.currency_converter.models.AuthAnswer;
import com.converter.currency_converter.models.Session;
import com.converter.currency_converter.models.User;
import com.converter.currency_converter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class authorization {

    @Autowired
    private Security security;

    @Autowired
    private UserRepository repository;

    @GetMapping("/login")
    public String loginPage(@CookieValue(value = "token", defaultValue = "") String token) {
        if (security.isAuth(token))
            return "redirect:/";
        return "login";
    }

    @ResponseBody
    @PostMapping("/login")
    public AuthAnswer login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) {

        User user = repository.getByUsernameAndByPassword(username, password);
        AuthAnswer answer = new AuthAnswer();
        if (user != null) {
            answer.setStatus(1);
            answer.setMsg("Success");
            Session session = security.addSession(user);
            Cookie cookie = new Cookie("token", session.getId());
            response.addCookie(cookie);
        } else {
            answer.setStatus(-1);
            answer.setMsg("Неверный логин или пароль");
        }

        return answer;
    }

}
