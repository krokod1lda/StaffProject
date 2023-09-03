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
import java.util.*;

@Controller
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/addStaff")
    public String addStaff(Model model) {

        model.addAttribute("title", "Добавление сотрудника");

        return "add-staff";
    }
    @PostMapping("/addStaff")
    public String newStaff(@RequestParam String name, @RequestParam String surname,
                           @RequestParam String patronymic) {

        Staff staff = new Staff(name, surname, patronymic);
        staffRepository.save(staff);

        return "redirect:/";
    }
    @GetMapping("/allStaff")
    public String allStaff(Model model) {

        Iterable<Staff> staffs = staffRepository.findAll();
        model.addAttribute("staffs", staffs); // Передаем данные всех сотрудников в шаблон

        model.addAttribute("title", "Сотрудники");

        return "all-staff";
    }

    @GetMapping("/staff{id}")
    public String viewStaff(@PathVariable(value = "id") long id, Model model) {

        if(!staffRepository.existsById(id)) {
            return "redirect:/";
        }

        Optional<Staff> staff = staffRepository.findById(id);  // следующие 4 строки - передаем данные об одном сотруднике
        ArrayList<Staff> res = new ArrayList<>();
        staff.ifPresent(res::add);
        model.addAttribute("staff", res);

        List<Record> records = recordRepository.findByStaffId(id); // записи данного сотрудника

        List<Float> resultTime = new ArrayList<Float>();

        for (Record record : records) {
            float resTime = StrToFlGetTime(record.getStartHours(), record.getEndHours());
            resultTime.add(resTime);
        }

        Map<Record, Float> recordsAndTime = new HashMap<>();

        for (int i = 0; i < records.size(); i++) {
            recordsAndTime.put(records.get(i), resultTime.get(i));
        }

        model.addAttribute("recordsAndTime", recordsAndTime); // передаем в шаблон Map с записями и соответствующим временем
        model.addAttribute("title", "Сотрудник");
        return "staff-card";
    }

    @PostMapping("/staff{id}/remove")
    public String staffDelete(@PathVariable(value = "id") long id) {

        Staff staff = staffRepository.findById(id).orElseThrow();
        staffRepository.delete(staff);

        List<Record> records = recordRepository.findByStaffId(id);
        recordRepository.deleteAll(records);

        return "redirect:/";
    }
    private float StrToFlGetTime(String time1, String time2) { // функция возвращает количество часов

        float h1 = Float.parseFloat(time1.substring(0, 2)), h2 = Float.parseFloat(time2.substring(0, 2)); // сразу привели часы к float
        float m1 = Float.parseFloat(time1.substring(3)), m2 = Float.parseFloat(time2.substring(3));

        return ((h2 * 60 + m2) - (h1 * 60 + m1)) / 60;
    }
}
