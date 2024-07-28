package com.krokod1lda.staff.services;

import com.krokod1lda.staff.exceptions.ResourceNotFoundException;
import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;
    @Autowired
    StaffRepository staffRepository;

    public void addRecord(long staffId, Date date, String startHours,
                          String endHours, float workingRate) {

        Record record = new Record(staffId, date, startHours, endHours, workingRate);
        recordRepository.save(record);
    }

    public RecordFullWithStaffName getRecordWithStaffNameById(long id) {

        Record record = recordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("oh god, record with id " + id + " not found"));
        Staff staff = staffRepository.findById(record.getStaffId()).orElseThrow();
        return new RecordFullWithStaffName(record, staff.getName());
    }

    public List<Date> getSetOfDates() {
        List<Record> records = (List<Record>) recordRepository.findAll();

        return records.stream()
                .map(Record::getDate)
                .sorted(Comparator.reverseOrder())
                .distinct()
                .collect(Collectors.toList());
    }

    public AllRecordsWrapper getAllRecordsByDates() {
        List<Date> uniqueDates = getSetOfDates();
        List<RecordWithStaffNameListAndDate> list = new ArrayList<>();

        for (Date date : uniqueDates) {
            List<Record> records = recordRepository.findByDate(date);
            List<RecordAndStaffName> recordAndStaffNames = new ArrayList<>();

            for (Record record : records) {
                addRecordToRecordAndStaffNamesList(recordAndStaffNames, record);
            }

            RecordWithStaffNameListAndDate recordWithStaffNameListAndDate =
                    new RecordWithStaffNameListAndDate(date, recordAndStaffNames);
            list.add(recordWithStaffNameListAndDate);
        }

        return new AllRecordsWrapper(list);
    }

    public void addRecordToRecordAndStaffNamesList(List<RecordAndStaffName> recordAndStaffNames, Record record) {

        Staff staff = staffRepository.findById(record.getStaffId()).orElseThrow();
        RecordAndStaffName recordAndStaffName = new RecordAndStaffName(record.getId(), record.getStartHours(),
                record.getEndHours(), record.getWorkingRate(), staff.getName());
        recordAndStaffNames.add(recordAndStaffName);

    }

    public void removeRecord(long id) {

        Record record = recordRepository.findById(id).orElseThrow();
        recordRepository.delete(record);
    }

    public static class RecordAndStaffName {
        private long id;
        private String startHours;
        private String endHours;
        private float workingRate;
        private String staffName;

        public RecordAndStaffName(long id, String startHours, String endHours, float workingRate, String staffName) {
            this.id = id;
            this.startHours = startHours;
            this.endHours = endHours;
            this.workingRate = workingRate;
            this.staffName = staffName;
        }

        public RecordAndStaffName() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getStartHours() {
            return startHours;
        }

        public void setStartHours(String startHours) {
            this.startHours = startHours;
        }

        public String getEndHours() {
            return endHours;
        }

        public void setEndHours(String endHours) {
            this.endHours = endHours;
        }

        public float getWorkingRate() {
            return workingRate;
        }

        public void setWorkingRate(float workingRate) {
            this.workingRate = workingRate;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }
    }

    public static class RecordWithStaffNameListAndDate {
        private Date date;
        private List<RecordAndStaffName> records;

        public RecordWithStaffNameListAndDate(Date date, List<RecordAndStaffName> records) {
            this.date = date;
            this.records = records;
        }

        public RecordWithStaffNameListAndDate() {
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public List<RecordAndStaffName> getRecords() {
            return records;
        }

        public void setRecords(List<RecordAndStaffName> records) {
            this.records = records;
        }
    }

    public static class AllRecordsWrapper {
        private List<RecordWithStaffNameListAndDate> list;

        public AllRecordsWrapper(List<RecordWithStaffNameListAndDate> list) {
            this.list = list;
        }

        public AllRecordsWrapper() {
        }

        public List<RecordWithStaffNameListAndDate> getList() {
            return list;
        }

        public void setList(List<RecordWithStaffNameListAndDate> list) {
            this.list = list;
        }
    }

    public static class RecordFullWithStaffName {
        private Record record;
        private String staffName;

        public RecordFullWithStaffName(Record record, String staffName) {
            this.record = record;
            this.staffName = staffName;
        }

        public RecordFullWithStaffName() {
        }

        public Record getRecord() {
            return record;
        }

        public void setRecord(Record record) {
            this.record = record;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }
    }
}
