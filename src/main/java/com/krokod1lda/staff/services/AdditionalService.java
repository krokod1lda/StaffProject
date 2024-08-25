package com.krokod1lda.staff.services;

import com.krokod1lda.staff.exceptions.ResourceNotFoundException;
import com.krokod1lda.staff.models.Additional;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.AdditionalRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import com.krokod1lda.staff.wrappers.additionalWrappers.AdditionalAndStaffNameWrapper;
import com.krokod1lda.staff.wrappers.additionalWrappers.AdditionalFullWithStaffNameWrapper;
import com.krokod1lda.staff.wrappers.additionalWrappers.AdditionalWithStaffNameListAndDateWrapper;
import com.krokod1lda.staff.wrappers.additionalWrappers.AllAdditionalsWrapper;
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

    public AdditionalFullWithStaffNameWrapper getAdditionalWIthStaffNameById(long id) {

        Additional additional = additionalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " not found... oh no"));
        Staff staff = staffRepository.findById(additional.getStaffId()).orElseThrow();
        return new AdditionalFullWithStaffNameWrapper(additional, staff.getName());
    }

    public void addAdditionalToAdditionalAndStaffNamesList(List<AdditionalAndStaffNameWrapper> additionalAndStaffNames, Additional additional) {

        Staff staff = staffRepository.findById(additional.getStaffId()).orElseThrow();
        AdditionalAndStaffNameWrapper additionalAndStaffName = new AdditionalAndStaffNameWrapper(additional.getId(), additional.getType(),
                additional.getPrice(), staff.getName());
        additionalAndStaffNames.add(additionalAndStaffName);
    }

    public AllAdditionalsWrapper getAllAdditionalsByDates() {
        List<Date> uniqueDates = getSetOfDates();
        List<AdditionalWithStaffNameListAndDateWrapper> list = new ArrayList<>();

        for (Date date : uniqueDates) {
            List<Additional> additionals = additionalRepository.findByDate(date);
            List<AdditionalAndStaffNameWrapper> additionalAndStaffNames = new ArrayList<>();

            for (Additional additional : additionals) {
                addAdditionalToAdditionalAndStaffNamesList(additionalAndStaffNames, additional);
            }
            AdditionalWithStaffNameListAndDateWrapper additionalWithStaffNameListAndDate =
                    new AdditionalWithStaffNameListAndDateWrapper(date, additionalAndStaffNames);
            list.add(additionalWithStaffNameListAndDate);
        }

        return new AllAdditionalsWrapper(list);
    }

    public void removeAdditional(long id) {

        Additional additional = additionalRepository.findById(id).orElseThrow();
        additionalRepository.delete(additional);
    }
}