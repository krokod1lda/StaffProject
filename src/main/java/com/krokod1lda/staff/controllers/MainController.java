package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repo.RecordRepository;
import com.krokod1lda.staff.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/")
    public String home(Model model) {

        Iterable<Record> records = recordRepository.findAll();

        List<String> dates = new ArrayList<>(); // создали список с датами
        for (Record record : records) {
            dates.add(record.getDate());
        }
        Set<String> datesSet = new LinkedHashSet<>(dates); // оставили уникальные даты

        List<List> recordsByDatesList = new ArrayList<>();
        for (String date : datesSet) {
            List<Record> recordsByDate = recordRepository.findByDate(date);
            recordsByDatesList.add(recordsByDate);
        }
        model.addAttribute("recordsByDatesList", recordsByDatesList);

        // Далее передаем данные для вывода имени и фамилии по id

        Iterable<Staff> staffs = staffRepository.findAll();

        Map<Long, Staff> staffById = new HashMap<>();
        for (Staff staff : staffs) {
            staffById.put(staff.getId(), staff);
        }
        model.addAttribute("staffById", staffById); // передали словарь с ключом - id, значением - объектом staff

        model.addAttribute("title", "Главная страница");
        return "home";
    }
    @GetMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("title", "Статистика");
        return "statistics";
    }
}
