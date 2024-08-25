package com.krokod1lda.staff.services;

import com.krokod1lda.staff.models.Additional;
import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.AdditionalRepository;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import com.krokod1lda.staff.wrappers.mainViewWrappers.AllDataWrapper;
import com.krokod1lda.staff.wrappers.mainViewWrappers.AllFieldsForOneStaffWrapper;
import com.krokod1lda.staff.wrappers.mainViewWrappers.AllObjectsForOneDayWrapper;
import com.krokod1lda.staff.wrappers.mainViewWrappers.RecordForTableViewWrapper;
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
public class MainViewService {

    @Autowired
    RecordRepository recordRepository;
    @Autowired
    AdditionalRepository additionalRepository;
    @Autowired
    StaffRepository staffRepository;

    public List<Date> getUniqueDatesOfAllRecordsAndAdditionals() {

        List<Additional> additionalList = (List<Additional>) additionalRepository.findAll();
        List<Record> recordList = (List<Record>) recordRepository.findAll();

        return Stream.concat(
                        additionalList.stream().map(Additional::getDate),
                        recordList.stream().map(Record::getDate)).sorted(Comparator.reverseOrder()).distinct()
                .collect(Collectors.toList());
    }

    public float StrToFlGetTime(String time1, String time2) {

        float h1 = Float.parseFloat(time1.substring(0, 2)), h2 = Float.parseFloat(time2.substring(0, 2));
        float m1 = Float.parseFloat(time1.substring(3)), m2 = Float.parseFloat(time2.substring(3));

        return ((h2 * 60 + m2) - (h1 * 60 + m1)) / 60;
    }

    public boolean wasWorkedStaffByDate(Staff staff, Date date) {

        return !additionalRepository.findByStaffIdAndDate(staff.getId(), date).isEmpty() ||
                !recordRepository.findByStaffIdAndDate(staff.getId(), date).isEmpty();
    }

    public List<RecordForTableViewWrapper> getRecordsForTableByDate(long id, Date date) {

        List<Record> recordList = recordRepository.findByStaffIdAndDate(id, date);
        List<RecordForTableViewWrapper> resultList = new ArrayList<>();
        for (Record record : recordList) {
            RecordForTableViewWrapper recordForTableView = new RecordForTableViewWrapper(record.getId(), record.getStartHours(),
                    record.getEndHours(), StrToFlGetTime(record.getStartHours(), record.getEndHours()), record.getWorkingRate());
            resultList.add(recordForTableView);
        }
        return resultList;
    }

    public float getResultPriceByRecordsForOneDay(List<RecordForTableViewWrapper> records) {

        float result = 0;

        for (RecordForTableViewWrapper recordForTableView : records) {
            float oneRecordResult = recordForTableView.getHours() * recordForTableView.getWorkingRate();
            result += oneRecordResult;
        }
        return result;
    }

    public AllDataWrapper getAllDataForMainView() {

        List<AllObjectsForOneDayWrapper> list = new ArrayList<>();

        List<Date> uniqueDates = getUniqueDatesOfAllRecordsAndAdditionals();
        List<Staff> staffs = (List<Staff>) staffRepository.findAll();

        for (Date date : uniqueDates) {

            List<AllFieldsForOneStaffWrapper> staffList = new ArrayList<>();
            for (Staff staff : staffs) {

                if (!wasWorkedStaffByDate(staff, date)) {
                    continue;
                }

                float prizes = 0;
                float sales = 0;
                float fines = 0;
                float result = 0;

                List<RecordForTableViewWrapper> records = getRecordsForTableByDate(staff.getId(), date);
                result += getResultPriceByRecordsForOneDay(records);

                List<Additional> additionals = additionalRepository.findByStaffIdAndDate(staff.getId(), date);
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

                AllFieldsForOneStaffWrapper allFieldsForOneStaffWrapper =
                        new AllFieldsForOneStaffWrapper(staff, records, prizes, sales, fines, result);

                staffList.add(allFieldsForOneStaffWrapper);

            }
            AllObjectsForOneDayWrapper allObjectsForOneDayWrapper = new AllObjectsForOneDayWrapper(date, staffList);
            list.add(allObjectsForOneDayWrapper);
        }
        return new AllDataWrapper(list);

    }
}