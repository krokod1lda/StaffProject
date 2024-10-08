package com.krokod1lda.staff.services;

import com.krokod1lda.staff.exceptions.ResourceNotFoundException;
import com.krokod1lda.staff.models.Additional;
import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.repositories.AdditionalRepository;
import com.krokod1lda.staff.repositories.RecordRepository;
import com.krokod1lda.staff.repositories.StaffRepository;
import com.krokod1lda.staff.wrappers.staffWrappers.AllStaffWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private AdditionalRepository additionalRepository;


    public void addStaff(String name, String surname) {

        Staff staff = new Staff(name, surname);
        staffRepository.save(staff);
    }

    public void editStaff(long id, String name, String surname) {

        Staff staff = staffRepository.findById(id).orElseThrow();

        staff.setName(name);
        staff.setSurname(surname);

        staffRepository.save(staff);
    }

    public Staff getStaffById(long id) {

        return staffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("my god, not found staff with ID " + id));
    }

    public AllStaffWrapper getAllStaff() {
        Iterable<Staff> staffIterable = staffRepository.findAll();

        return new AllStaffWrapper(staffIterable);
    }


    public void removeStaff(long id) {

        Staff staff = staffRepository.findById(id).orElseThrow();
        staffRepository.delete(staff);

        List<Record> records = recordRepository.findByStaffId(id);
        recordRepository.deleteAll(records);

        List<Additional> additionals = additionalRepository.findByStaffId(id);
        additionalRepository.deleteAll(additionals);
    }
}