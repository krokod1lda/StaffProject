package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.dto.RecordRequestDto;
import com.krokod1lda.staff.services.RecordService;
import com.krokod1lda.staff.wrappers.recordWrappers.AllRecordsWrapper;
import com.krokod1lda.staff.wrappers.recordWrappers.RecordFullWithStaffNameWrapper;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Log4j2
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping(value = "/addRecord", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newRecord(@Valid @RequestBody RecordRequestDto record) {

        log.info("Инициирован процесс добавления новой записи");
        recordService.addRecord(record.getStaffId(), record.getDate(), record.getStartHours(), record.getEndHours(), record.getWorkingRate());
    }

    @GetMapping("/allRecords")
    @ResponseBody
    public AllRecordsWrapper allRecords() {

        log.info("Инициирован процесс получения информации обо всех записях");
        return recordService.getAllRecordsByDates();
    }

    @GetMapping(value = "/record{id}/edit")
    @ResponseBody
    public RecordFullWithStaffNameWrapper getEditRecord(@PathVariable(value = "id") long id) {

        log.info("Инициирован процесс получения информации о записи, ID записи: {}", id);
        return recordService.getRecordWithStaffNameById(id);
    }

    @PostMapping(value = "/record{id}/delete")
    public void deleteRecord(@PathVariable(value = "id") long id) {

        log.info("Инициирован процесс удаления записи, ID записи: {}", id);
        recordService.removeRecord(id);
    }
}