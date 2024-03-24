package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.services.RecordService;
import com.krokod1lda.staff.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RecordController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private RecordService recordService;


    @PostMapping(value = "/addRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newRecord(@RequestBody Record record) {

        recordService.addRecord(record.getStaffId(), record.getDate(), record.getStartHours(), record.getEndHours(), record.getWorkingRate());
        return;
    }


//    @PostMapping("/record{id}/remove")
//    public String recordDelete(@PathVariable(value = "id") long id) {
//
//        recordService.deleteRecord(id);
//
//        return "redirect:/";
//    }

}