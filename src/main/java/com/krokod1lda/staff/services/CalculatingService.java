package com.krokod1lda.staff.services;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class CalculatingService {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private StaffRepository staffRepository;

    public List<List> getRecordsByDatesList() {

        Iterable<Record> records = recordRepository.findAll();

        List<Date> dates = new ArrayList<>(); // создали список с датами
        for (Record record : records) {
            dates.add(record.getDate());
        }
        Set<Date> datesSet = new LinkedHashSet<>(dates); // оставили уникальные даты

        List<List> recordsByDatesList = new ArrayList<>();
        for (Date date : datesSet) {

            List<Record> recordsByDate = recordRepository.findByDate(date);
            // ПОПРОБОВАТЬ СВЯЗАТЬ recordId и высчитанное время (юзать функцию нижнюю)
            recordsByDatesList.add(recordsByDate);

        }

        return recordsByDatesList;
    }

    public Map<Long, Staff> getStaffById() { // РАЗОБРАТЬСЯ ЧТО ТУТ БЫЛО НАПИСАНО И ДЛЯ ЧЕГО, ПЕРЕДЕЛАТЬ

        // Далее передаем данные для вывода имени и фамилии по id
        Iterable<Staff> staffs = staffRepository.findAll();

        Map<Long, Staff> staffById = new HashMap<>();
        for (Staff staff : staffs) {
            staffById.put(staff.getId(), staff);
        }
        return staffById;
    }
}
