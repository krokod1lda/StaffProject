package com.krokod1lda.staff.services;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private RecordRepository recordRepository;
    public void addStaff(String name, String surname, String patronymic) {

        Staff staff = new Staff(name, surname, patronymic);
        staffRepository.save(staff);
    }

    public ArrayList<Staff> getStaffById(long id) {

        Optional<Staff> staff = staffRepository.findById(id);  // следующие 4 строки - передаем данные об одном сотруднике
        ArrayList<Staff> res = new ArrayList<>();
        staff.ifPresent(res::add);

        return res;
    }
    public Iterable<Staff> getAllStaff() {

        return staffRepository.findAll();
    }

    public Map<Record, Float> getRecordsAndTime(long id) {

        List<Record> records = recordRepository.findByStaffId(id); // записи данного сотрудника
        List<Float> resultTime = new ArrayList<Float>();

        for (Record record : records) {
            float resTime = StrToFlGetTime(record.getStartHours(), record.getEndHours());
            resultTime.add(resTime);
        }

        Map<Record, Float> recordsAndTime = new HashMap<>();

        for (int i = 0; i < records.size(); i++) {
            recordsAndTime.put(records.get(i), resultTime.get(i));
        }

        return recordsAndTime;
    }

    public void removeStaff(long id) {

        Staff staff = staffRepository.findById(id).orElseThrow();
        staffRepository.delete(staff);

        List<Record> records = recordRepository.findByStaffId(id);
        recordRepository.deleteAll(records);
    }

    public float StrToFlGetTime(String time1, String time2) { // функция возвращает количество часов

        float h1 = Float.parseFloat(time1.substring(0, 2)), h2 = Float.parseFloat(time2.substring(0, 2)); // сразу привели часы к float
        float m1 = Float.parseFloat(time1.substring(3)), m2 = Float.parseFloat(time2.substring(3));

        return ((h2 * 60 + m2) - (h1 * 60 + m1)) / 60;
    }
}
