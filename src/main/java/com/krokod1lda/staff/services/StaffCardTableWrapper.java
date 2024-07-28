package com.krokod1lda.staff.services;

import com.krokod1lda.staff.exceptions.ResourceNotFoundException;
import com.krokod1lda.staff.models.Additional;
import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.AdditionalRepository;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
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
public class StaffCardTableWrapper {

    @Autowired
    RecordRepository recordRepository;
    @Autowired
    AdditionalRepository additionalRepository;
    @Autowired
    StaffRepository staffRepository;


    public List<Date> getUniqueDatesOfRecordsAndAdditionals(long id) {
        List<Additional> additionalList = (List<Additional>) additionalRepository.findByStaffId(id);
        List<Record> recordList = (List<Record>) recordRepository.findByStaffId(id);

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

    public List<RecordForTableView> getRecordsForTableByDate(long id, Date date) {

        List<Record> recordList = recordRepository.findByStaffIdAndDate(id, date);
        List<RecordForTableView> resultList = new ArrayList<>();
        for (Record record : recordList) {
            RecordForTableView recordForTableView = new RecordForTableView(record.getId(), record.getStartHours(),
                    record.getEndHours(), StrToFlGetTime(record.getStartHours(), record.getEndHours()), record.getWorkingRate());
            resultList.add(recordForTableView);
        }
        return resultList;
    }

    public float getResultPriceByRecordsForOneDay(List<RecordForTableView> records) {

        float result = 0;

        for (RecordForTableView recordForTableView : records) {
            float oneRecordResult = (float) (recordForTableView.getHours() * recordForTableView.getWorkingRate());
            result += oneRecordResult;
        }
        return result;
    }


    public AllObjectsWrapper getStaffCardDataById(long staffId) {

        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("oh no, not found staff with ID " + staffId));

        List<AllFieldsForOneObjectWrapper> list = new ArrayList<>();
        List<Date> uniqueDates = getUniqueDatesOfRecordsAndAdditionals(staffId);
        for (Date date : uniqueDates) {

            float result = 0;

            List<RecordForTableView> records = getRecordsForTableByDate(staffId, date);
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

            AllFieldsForOneObjectWrapper item = new AllFieldsForOneObjectWrapper(date, records, prizes, sales, fines, result);
            list.add(item);
        }
        return new AllObjectsWrapper(staff, list);
    }


    public static class RecordForTableView {
        private long id;
        private String startHours;
        private String endHours;
        private float hours;
        private float workingRate;

        public RecordForTableView(long id, String startHours, String endHours, float hours, float workingRate) {
            this.id = id;
            this.startHours = startHours;
            this.endHours = endHours;
            this.hours = hours;
            this.workingRate = workingRate;
        }

        public RecordForTableView() {
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

        public float getHours() {
            return hours;
        }

        public void setHours(float hours) {
            this.hours = hours;
        }

        public float getWorkingRate() {
            return workingRate;
        }

        public void setWorkingRate(float workingRate) {
            this.workingRate = workingRate;
        }
    }

    public static class AllFieldsForOneObjectWrapper {
        private Date date;
        private List<RecordForTableView> records;
        private float prizes;
        private float sales;
        private float fines;
        private float result;

        public AllFieldsForOneObjectWrapper(Date date, List<RecordForTableView> records, float prizes,
                                            float sales, float fines, float result) {
            this.date = date;
            this.records = records;
            this.prizes = prizes;
            this.sales = sales;
            this.fines = fines;
            this.result = result;
        }

        public AllFieldsForOneObjectWrapper() {
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public List<RecordForTableView> getRecords() {
            return records;
        }

        public void setRecords(List<RecordForTableView> records) {
            this.records = records;
        }

        public float getPrizes() {
            return prizes;
        }

        public void setPrizes(float prizes) {
            this.prizes = prizes;
        }

        public float getSales() {
            return sales;
        }

        public void setSales(float sales) {
            this.sales = sales;
        }

        public float getFines() {
            return fines;
        }

        public void setFines(float fines) {
            this.fines = fines;
        }

        public float getResult() {
            return result;
        }

        public void setResult(float result) {
            this.result = result;
        }
    }

    public static class AllObjectsWrapper {
        private Staff staff;
        private List<AllFieldsForOneObjectWrapper> list;

        public AllObjectsWrapper(Staff staff, List<AllFieldsForOneObjectWrapper> list) {
            this.staff = staff;
            this.list = list;
        }

        public AllObjectsWrapper() {
        }

        public List<AllFieldsForOneObjectWrapper> getList() {
            return list;
        }

        public void setList(List<AllFieldsForOneObjectWrapper> list) {
            this.list = list;
        }

        public Staff getStaff() {
            return staff;
        }

        public void setStaff(Staff staff) {
            this.staff = staff;
        }
    }
}
