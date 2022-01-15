package com.converter.currency_converter.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.converter.currency_converter.components.Security;
import com.converter.currency_converter.models.Currency;
import com.converter.currency_converter.models.History;
import com.converter.currency_converter.repository.CurrencyRepository;
import com.converter.currency_converter.repository.HistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class history {

    @Autowired
    private Security security;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @GetMapping("/history")
    public String historyPage(
            @CookieValue(value = "token", defaultValue = "") String token,
            @RequestParam(required = false) Date date,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String target,
            Model model) {
        if (!security.isAuth(token))
            return "redirect:/login";

        List<History> historyList;

        if (date != null && source != null && target != null) {
            historyList = historyRepository.getBySessionIdAndDateAndSourceAndTarget(token, date, source, target);
        } else {
            historyList = historyRepository.getBySessionId(token);
        }

        model.addAttribute("currency", currencyRepository.getAll());
        model.addAttribute("history", historyList);
        return "history";
    }
}
