package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.repo.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/")
    public String home(Model model) {

        Iterable<Record> records = recordRepository.findAll();
        model.addAttribute("records", records);

        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("title", "Статистика");
        return "statistics";
    }
}
