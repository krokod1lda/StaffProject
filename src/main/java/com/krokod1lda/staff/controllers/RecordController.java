package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping(value = "/addRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newRecord(@RequestBody Record record) {

        recordService.addRecord(record.getStaffId(), record.getDate(), record.getStartHours(), record.getEndHours(), record.getWorkingRate());
        return;
    }

    @GetMapping("/allRecords")
    @ResponseBody
    public RecordService.AllRecordsWrapper allRecords() {

        return recordService.getAllRecordsByDates();
    }

    @GetMapping(value = "/record{id}/edit")
    @ResponseBody
    public RecordService.RecordFullWithStaffName getEditRecord(@PathVariable(value = "id") long id) {

        return recordService.getRecordWithStaffNameById(id);
    }


    @PostMapping(value = "/record{id}/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRecord(@RequestBody Record record) {

        recordService.removeRecord(record.getId());
        return;
    }
}