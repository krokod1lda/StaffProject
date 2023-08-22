package com.krokod1lda.staff.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecordController {
    @GetMapping("/addRecord")
    public String addRecord(Model model) {
        model.addAttribute("title", "Добавление записи");
        return "add-record";
    }
}
