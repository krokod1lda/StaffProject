package com.krokod1lda.staff.services;

import com.krokod1lda.staff.exceptions.ResourceNotFoundException;
import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import com.krokod1lda.staff.wrappers.recordWrappers.AllRecordsWrapper;
import com.krokod1lda.staff.wrappers.recordWrappers.RecordAndStaffNameWrapper;
import com.krokod1lda.staff.wrappers.recordWrappers.RecordFullWithStaffNameWrapper;
import com.krokod1lda.staff.wrappers.recordWrappers.RecordWithStaffNameListAndDateWrapper;
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

    public RecordFullWithStaffNameWrapper getRecordWithStaffNameById(long id) {

        Record record = recordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("oh god, record with id " + id + " not found"));
        Staff staff = staffRepository.findById(record.getStaffId()).orElseThrow();
        return new RecordFullWithStaffNameWrapper(record, staff.getName());
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
        List<RecordWithStaffNameListAndDateWrapper> list = new ArrayList<>();

        for (Date date : uniqueDates) {
            List<Record> records = recordRepository.findByDate(date);
            List<RecordAndStaffNameWrapper> recordAndStaffNames = new ArrayList<>();

            for (Record record : records) {
                addRecordToRecordAndStaffNamesList(recordAndStaffNames, record);
            }

            RecordWithStaffNameListAndDateWrapper recordWithStaffNameListAndDate =
                    new RecordWithStaffNameListAndDateWrapper(date, recordAndStaffNames);
            list.add(recordWithStaffNameListAndDate);
        }

        return new AllRecordsWrapper(list);
    }

    public void addRecordToRecordAndStaffNamesList(List<RecordAndStaffNameWrapper> recordAndStaffNames, Record record) {

        Staff staff = staffRepository.findById(record.getStaffId()).orElseThrow();
        RecordAndStaffNameWrapper recordAndStaffName = new RecordAndStaffNameWrapper(record.getId(), record.getStartHours(),
                record.getEndHours(), record.getWorkingRate(), staff.getName());
        recordAndStaffNames.add(recordAndStaffName);

    }

    public void removeRecord(long id) {

        Record record = recordRepository.findById(id).orElseThrow();
        recordRepository.delete(record);
    }
}
