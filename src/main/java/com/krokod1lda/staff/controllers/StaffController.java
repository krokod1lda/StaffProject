package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.entityAttributes.StaffAttributes;
import com.krokod1lda.staff.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class StaffController {


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

    @GetMapping(value = "/staff{id}")
    @ResponseBody
    public ResponseEntity<StaffService.Wrap> viewStaff(@PathVariable(value = "id") long id) {

        return new ResponseEntity<>(staffService.wrapTheList(staffService.getStaffById(id), staffService.getRecordsAndTime(id)), HttpStatus.OK);
    }

    @PostMapping("/staff{id}/remove")
    public String staffDelete(@PathVariable(value = "id") long id) {

        staffService.removeStaff(id);

        return "redirect:/";
    }
}
