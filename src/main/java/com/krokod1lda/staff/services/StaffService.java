package com.krokod1lda.staff.services;

import com.krokod1lda.staff.RecordAndHoursWrapper;
import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private RecordRepository recordRepository;


    public void addStaff(String name, String surname) {

        Staff staff = new Staff(name, surname);
        staffRepository.save(staff);
    }


    public Staff getStaffById(long id) {

        return staffRepository.findById(id).orElseThrow();
    }

    public Iterable<Staff> getAllStaff() {

        return staffRepository.findAll();
    }


    public List<RecordAndHoursWrapper> getRecordsAndTime(long id) {


        List<Record> records = recordRepository.findByStaffId(id); // записи данного сотрудника
        List<RecordAndHoursWrapper> recordAndHoursWrappers = new ArrayList<>();

        for (Record record : records) {
            Float hours = StrToFlGetTime(record.getStartHours(), record.getEndHours());

            recordAndHoursWrappers.add(new RecordAndHoursWrapper(record, hours));
        }

        return recordAndHoursWrappers;
    }


    public Wrap wrapTheList(Staff staff, List<RecordAndHoursWrapper> list) { // Обертывание списка объектов RecordAndHoursWrapper

        return new Wrap(staff, list);
    }


    public static class Wrap { // Класс для обертывания списка объектов RecordAndHoursWrapper

        private Staff staff;

        private List<RecordAndHoursWrapper> list;

        public List<RecordAndHoursWrapper> getList() {
            return list;
        }

        public void setList(List<RecordAndHoursWrapper> list) {
            this.list = list;
        }

        public Staff getStaff() {
            return staff;
        }

        public void setStaff(Staff staff) {
            this.staff = staff;
        }

        public Wrap(Staff staff, List<RecordAndHoursWrapper> list) {

            this.staff = staff;
            this.list = list;
        }
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
