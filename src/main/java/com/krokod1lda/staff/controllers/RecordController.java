package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecordController {

    @Autowired
    private StaffRepository staffRepository;
    @GetMapping("/addRecord")
    public String addRecord(Model model) {

        Iterable<Staff> staffs = staffRepository.findAll();
        model.addAttribute("staffs", staffs); // Передаем данные всех сотрудников в шаблон

        model.addAttribute("title", "Добавление записи");
        return "add-record";
    }
}
