package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repo.RecordRepository;
import com.krokod1lda.staff.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class RecordController {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/addRecord")
    public String addRecord(Model model) {

        Iterable<Staff> staffs = staffRepository.findAll();
        model.addAttribute("staffs", staffs); // Передаем данные всех сотрудников в шаблон

        model.addAttribute("title", "Добавление записи");
        return "add-record";
    }

    @PostMapping("/addRecord")
    public String newRecord(@RequestParam Long staffId,
                            @RequestParam String date, @RequestParam String startHours,
                            @RequestParam String endHours, @RequestParam double workingRate) {

        Record record = new Record(staffId, parseDate(date), startHours, endHours, workingRate);
        recordRepository.save(record);

        return "redirect:/";
    }

    @PostMapping("/record{id}/remove")
    public String recordDelete(@PathVariable(value = "id") long id) {

        Record record = recordRepository.findById(id).orElseThrow();
        recordRepository.delete(record);

        return "redirect:/";
    }
    private String parseDate(String str) {

        String year = str.substring(0, 4);
        String month = str.substring(5, 7);
        String day = str.substring(8);

        return day + "." + month + "." + year;
    }
}