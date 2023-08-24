package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;
    @GetMapping("/addStaff")
    public String addStaff(Model model) {
        model.addAttribute("title", "Добавление сотрудника");
        return "add-staff";
    }
    @PostMapping("/addStaff")
    public String newStaff(@RequestParam String name, @RequestParam String surname,
                           @RequestParam String patronymic, Model model) {
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

        model.addAttribute("title", "Сотрудник");
        return "staff-card";
    }

    @PostMapping("/staff{id}/remove")
    public String staffDelete(@PathVariable(value = "id") long id) {

        Staff staff = staffRepository.findById(id).orElseThrow();
        staffRepository.delete(staff);

        return "redirect:/";
    }
}
