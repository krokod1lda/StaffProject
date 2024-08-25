package com.krokod1lda.staff.services;

import com.krokod1lda.staff.exceptions.ResourceNotFoundException;
import com.krokod1lda.staff.models.Additional;
import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.AdditionalRepository;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import com.krokod1lda.staff.wrappers.staffWrappers.RecordForStaffTableViewWrapper;
import com.krokod1lda.staff.wrappers.staffWrappers.RecordsAndAdditionalByDateWrapper;
import com.krokod1lda.staff.wrappers.staffWrappers.StaffCardWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class StaffCardViewService {

    @Autowired
    RecordRepository recordRepository;
    @Autowired
    AdditionalRepository additionalRepository;
    @Autowired
    StaffRepository staffRepository;


    public List<Date> getUniqueDatesOfRecordsAndAdditionals(long id) {
        List<Additional> additionalList = additionalRepository.findByStaffId(id);
        List<Record> recordList = recordRepository.findByStaffId(id);

        return Stream.concat(
                        additionalList.stream().map(Additional::getDate),
                        recordList.stream().map(Record::getDate))
                .sorted(Comparator.reverseOrder()).distinct().collect(Collectors.toList());
    }

    public float StrToFlGetTime(String time1, String time2) {

        float h1 = Float.parseFloat(time1.substring(0, 2)), h2 = Float.parseFloat(time2.substring(0, 2));
        float m1 = Float.parseFloat(time1.substring(3)), m2 = Float.parseFloat(time2.substring(3));

        return ((h2 * 60 + m2) - (h1 * 60 + m1)) / 60;
    }

    public List<RecordForStaffTableViewWrapper> getRecordsForTableByDate(long id, Date date) {

        List<Record> recordList = recordRepository.findByStaffIdAndDate(id, date);
        List<RecordForStaffTableViewWrapper> resultList = new ArrayList<>();
        for (Record record : recordList) {
            RecordForStaffTableViewWrapper RecordForStaffTableView = new RecordForStaffTableViewWrapper(record.getId(), record.getStartHours(),
                    record.getEndHours(), StrToFlGetTime(record.getStartHours(), record.getEndHours()), record.getWorkingRate());
            resultList.add(RecordForStaffTableView);
        }
        return resultList;
    }

    public float getResultPriceByRecordsForOneDay(List<RecordForStaffTableViewWrapper> records) {

        float result = 0;

        for (RecordForStaffTableViewWrapper RecordForStaffTableView : records) {
            float oneRecordResult = RecordForStaffTableView.getHours() * RecordForStaffTableView.getWorkingRate();
            result += oneRecordResult;
        }
        return result;
    }


    public StaffCardWrapper getStaffCardDataById(long staffId) {

        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("oh no, not found staff with ID " + staffId));

        List<RecordsAndAdditionalByDateWrapper> list = new ArrayList<>();
        List<Date> uniqueDates = getUniqueDatesOfRecordsAndAdditionals(staffId);
        for (Date date : uniqueDates) {

            float result = 0;

            List<RecordForStaffTableViewWrapper> records = getRecordsForTableByDate(staffId, date);
            float prizes = 0;
            float sales = 0;
            float fines = 0;

            result += getResultPriceByRecordsForOneDay(records);

            List<Additional> additionals = additionalRepository.findByStaffIdAndDate(staffId, date);
            for (Additional additional : additionals) {

                if (Objects.equals(additional.getType(), "prize")) {
                    prizes += additional.getPrice();
                    result += additional.getPrice();
                }
                if (Objects.equals(additional.getType(), "sale")) {
                    sales += additional.getPrice();
                    result += additional.getPrice();
                }
                if (Objects.equals(additional.getType(), "fine")) {
                    fines += additional.getPrice();
                    result -= additional.getPrice();
                }
            }

            RecordsAndAdditionalByDateWrapper item = new RecordsAndAdditionalByDateWrapper(date, records, prizes, sales, fines, result);
            list.add(item);
        }
        return new StaffCardWrapper(staff, list);
    }
}
