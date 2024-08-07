package com.krokod1lda.staff.services;

import com.krokod1lda.staff.exceptions.ResourceNotFoundException;
import com.krokod1lda.staff.models.Additional;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.AdditionalRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalService {

    @Autowired
    AdditionalRepository additionalRepository;
    @Autowired
    StaffRepository staffRepository;

    public void addAdditional(long staffId, String type, int price, Date date, String description) {

        Additional additional = new Additional(staffId, type, price, date, description);
        additionalRepository.save(additional);
    }

    public List<Date> getSetOfDates() {
        List<Additional> additionals = (List<Additional>) additionalRepository.findAll();

        return additionals.stream().map(Additional::getDate).sorted(Comparator.reverseOrder())
                .distinct().collect(Collectors.toList());
    }

    public AdditionalFullWithStaffName getAdditionalWIthStaffNameById(long id) {

        Additional additional = additionalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " not found... oh no"));
        Staff staff = staffRepository.findById(additional.getStaffId()).orElseThrow();
        return new AdditionalFullWithStaffName(additional, staff.getName());
    }

    public void addAdditionalToAdditionalAndStaffNamesList(List<AdditionalAndStaffName> additionalAndStaffNames, Additional additional) {

        Staff staff = staffRepository.findById(additional.getStaffId()).orElseThrow();
        AdditionalAndStaffName additionalAndStaffName = new AdditionalAndStaffName(additional.getId(), additional.getType(),
                additional.getPrice(), staff.getName());
        additionalAndStaffNames.add(additionalAndStaffName);
    }

    public AllAdditionalsWrapper getAllAdditionalsByDates() {
        List<Date> uniqueDates = getSetOfDates();
        List<AdditionalWithStaffNameListAndDate> list = new ArrayList<>();

        for (Date date : uniqueDates) {
            List<Additional> additionals = additionalRepository.findByDate(date);
            List<AdditionalAndStaffName> additionalAndStaffNames = new ArrayList<>();

            for (Additional additional : additionals) {
                addAdditionalToAdditionalAndStaffNamesList(additionalAndStaffNames, additional);
            }
            AdditionalWithStaffNameListAndDate additionalWithStaffNameListAndDate =
                    new AdditionalWithStaffNameListAndDate(date, additionalAndStaffNames);
            list.add(additionalWithStaffNameListAndDate);
        }

        return new AllAdditionalsWrapper(list);
    }

    public void removeAdditional(long id) {

        Additional additional = additionalRepository.findById(id).orElseThrow();
        additionalRepository.delete(additional);
    }


    public static class AdditionalAndStaffName {
        private long id;
        private String type;
        private int price;
        private String staffName;

        public AdditionalAndStaffName(long id, String type, int price, String staffName) {
            this.id = id;
            this.type = type;
            this.price = price;
            this.staffName = staffName;
        }

        public AdditionalAndStaffName() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }
    }

    public static class AdditionalWithStaffNameListAndDate {

        private Date date;
        private List<AdditionalAndStaffName> additionals;

        public AdditionalWithStaffNameListAndDate(Date date, List<AdditionalAndStaffName> additionals) {
            this.date = date;
            this.additionals = additionals;
        }

        public AdditionalWithStaffNameListAndDate() {
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public List<AdditionalAndStaffName> getAdditionals() {
            return additionals;
        }

        public void setAdditionals(List<AdditionalAndStaffName> additionals) {
            this.additionals = additionals;
        }
    }

    public static class AllAdditionalsWrapper {
        private List<AdditionalWithStaffNameListAndDate> list;

        public AllAdditionalsWrapper(List<AdditionalWithStaffNameListAndDate> list) {
            this.list = list;
        }

        public AllAdditionalsWrapper() {
        }

        public List<AdditionalWithStaffNameListAndDate> getList() {
            return list;
        }

        public void setList(List<AdditionalWithStaffNameListAndDate> list) {
            this.list = list;
        }
    }

    public static class AdditionalFullWithStaffName {
        private Additional additional;
        private String staffName;

        public AdditionalFullWithStaffName(Additional additional, String staffName) {
            this.additional = additional;
            this.staffName = staffName;
        }

        public AdditionalFullWithStaffName() {
        }

        public Additional getAdditional() {
            return additional;
        }

        public void setAdditional(Additional additional) {
            this.additional = additional;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }
    }
}