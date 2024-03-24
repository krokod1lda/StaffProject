package com.krokod1lda.staff.services;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;


@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    public void addRecord(long staffId, Date date, String startHours,
                          String endHours, double workingRate) {

        Record record = new Record(staffId, date, startHours, endHours, workingRate);
        recordRepository.save(record);
    }

    public void deleteRecord(long id) {

        Record record = recordRepository.findById(id).orElseThrow();
        recordRepository.delete(record);
    }

//    private String parseDate(String str) { // Подлежит удалению (изменить тип на Date)
//
//        String year = str.substring(0, 4);
//        String month = str.substring(5, 7);
//        String day = str.substring(8);
//
//        return day + "." + month + "." + year;
//    }
}
