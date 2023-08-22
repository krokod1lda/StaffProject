package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
