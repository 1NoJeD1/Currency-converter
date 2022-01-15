package com.converter.currency_converter.controllers;

import java.sql.Date;

import com.converter.currency_converter.components.GetBankData;
import com.converter.currency_converter.components.Security;
import com.converter.currency_converter.models.ConverterAnswer;
import com.converter.currency_converter.models.History;
import com.converter.currency_converter.models.Rate;
import com.converter.currency_converter.models.Session;
import com.converter.currency_converter.repository.CurrencyRepository;
import com.converter.currency_converter.repository.HistoryRepository;
import com.converter.currency_converter.repository.RateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class converter {

    private CurrencyRepository currencyRepository = new CurrencyRepository();
    private RateRepository rateRepository = new RateRepository();

    @Autowired
    private GetBankData bankData;

    @Autowired
    private Security security;

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping("/")
    public String converter_main(
            Model model,
            @CookieValue(value = "token", defaultValue = "") String token) {
        if (!security.isAuth(token))
            return "redirect:/login";
        model.addAttribute("currency", currencyRepository.getAll());
        model.addAttribute("history", historyRepository.getBySessionId(token));
        return "converter";
    }

    @ResponseBody
    @PostMapping("/converter")
    public ConverterAnswer postConverterAnswer(
            @CookieValue(value = "token", defaultValue = "") String token,
            @RequestParam String source_code,
            @RequestParam String target_code,
            @RequestParam Float source_value) {

        if (!security.isAuth(token))
            return new ConverterAnswer(0.0f, 0.0f);

        Date date = new Date(new java.util.Date().getTime());

        if (!rateRepository.existsByDate(date))
            bankData.getDataByDate(date);

        ConverterAnswer answer = new ConverterAnswer();

        Rate source = rateRepository.getByIdAndDate(source_code, date);
        Rate target = rateRepository.getByIdAndDate(target_code, date);

        float sourceRateInRub = (source.getNominal() / source.getRate());
        float targetRateInRub = (target.getNominal() / target.getRate());

        answer.setSource(source_value);
        answer.setTarget((targetRateInRub / sourceRateInRub) * source_value);

        Session session = security.getSessionData(token);

        History history = new History();
        history.setUser_id(session.getUser_id());
        history.setSource(source_code);
        history.setTarget(target_code);
        history.setSource_value(answer.getSource());
        history.setTarget_value(answer.getTarget());
        history.setRate_date(target.getDate());

        historyRepository.save(history);
        return answer;
    }

}
