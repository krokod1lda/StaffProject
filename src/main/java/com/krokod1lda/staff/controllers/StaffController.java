package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.entityAttributes.RecordAttributes;
import com.krokod1lda.staff.entityAttributes.StaffAttributes;
import com.krokod1lda.staff.repositories.StaffRepository;
import com.krokod1lda.staff.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StaffService staffService;

    @GetMapping("/addStaff")
    public String addStaff(Model model) {

        model.addAttribute(StaffAttributes.TITLE.getValue(), StaffAttributes.ADDING_STAFF.getValue());

        return "add-staff";
    }
    @PostMapping("/addStaff")
    public String newStaff(@RequestParam String name, @RequestParam String surname) {

        staffService.addStaff(name, surname);

        return "redirect:/";
    }
    @GetMapping("/allStaff")
    public String allStaff(Model model) {

        model.addAttribute(StaffAttributes.STAFFS.getValue(), staffService.getAllStaff()); // Передаем данные всех сотрудников в шаблон
        model.addAttribute(StaffAttributes.TITLE.getValue(), StaffAttributes.STAFFS_RUS.getValue());

        return "all-staff";
    }

    @GetMapping("/staff{id}")
    public String viewStaff(@PathVariable(value = "id") long id, Model model) {

        if(!staffRepository.existsById(id)) { // Переместить
            return "redirect:/";
        }

        model.addAttribute(StaffAttributes.STAFF.getValue(), staffService.getStaffById(id));
        model.addAttribute(RecordAttributes.RECORDS_AND_TIME.getValue(), staffService.getRecordsAndTime(id)); // передаем в шаблон Map с записями и соответствующим временем
        model.addAttribute(StaffAttributes.TITLE.getValue(), StaffAttributes.STAFF_RUS.getValue());
        return "staff-card";
    }

    @PostMapping("/staff{id}/remove")
    public String staffDelete(@PathVariable(value = "id") long id) {

        staffService.removeStaff(id);

        return "redirect:/";
    }

}
