package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.entityAttributes.RecordAttributes;
import com.krokod1lda.staff.entityAttributes.StaffAttributes;
import com.krokod1lda.staff.services.RecordService;
import com.krokod1lda.staff.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecordController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private RecordService recordService;

    @GetMapping("/addRecord")
    public String addRecord(Model model) {

        model.addAttribute(StaffAttributes.STAFFS.getValue(), staffService.getAllStaff()); // Передаем данные всех сотрудников в шаблон
        model.addAttribute(StaffAttributes.TITLE.getValue(), RecordAttributes.ADDIND_RECORD.getValue());

        return "add-record";
    }

    @PostMapping("/addRecord")
    public String newRecord(@RequestParam long staffId,
                            @RequestParam String date, @RequestParam String startHours,
                            @RequestParam String endHours, @RequestParam double workingRate) {

        recordService.addRecord(staffId, date, startHours, endHours, workingRate);

        return "redirect:/";
    }

    @PostMapping("/record{id}/remove")
    public String recordDelete(@PathVariable(value = "id") long id) {

        recordService.deleteRecord(id);

        return "redirect:/";
    }

}