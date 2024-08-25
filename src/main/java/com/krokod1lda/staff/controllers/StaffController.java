package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.dto.StaffRequestDto;
import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.services.StaffCardViewService;
import com.krokod1lda.staff.services.StaffService;
import com.krokod1lda.staff.wrappers.staffWrappers.AllStaffWrapper;
import com.krokod1lda.staff.wrappers.staffWrappers.StaffCardWrapper;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Log4j2
public class StaffController {


    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffCardViewService staffCardTableWrapper;

    @GetMapping("/allStaff")
    @ResponseBody
    public ResponseEntity<AllStaffWrapper> allStaff() {

        log.info("Инициирован процесс получения информации обо всех сотрудниках");
        return new ResponseEntity<>(staffService.getAllStaff(), HttpStatus.OK);
    }

    @GetMapping(value = "/staff{id}")
    @ResponseBody
    public ResponseEntity<StaffCardWrapper> viewStaff(@PathVariable(value = "id") long id) {

        log.info("Инициирован процесс получения информации о сотруднике для отображения карточки, ID сотрудника: {}", id);
        return new ResponseEntity<>(staffCardTableWrapper.getStaffCardDataById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/staff{id}/edit")
    @ResponseBody
    public ResponseEntity<Staff> getEditStaff(@PathVariable(value = "id") long id) {

        log.info("Инициирован процесс получения информации о сотруднике для редактирования информации, ID сотрудника: {}", id);
        return new ResponseEntity<>(staffService.getStaffById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/addStaff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newStaff(@Valid @RequestBody StaffRequestDto staff) {

        log.info("Инициирован процесс добавления нового сотредника");
        staffService.addStaff(staff.getName(), staff.getSurname());
    }

    @PostMapping(value = "/staff{id}/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editStaff(@PathVariable(value = "id") long id, @Valid @RequestBody StaffRequestDto staff) {

        log.info("Инициирован процесс редактирования информации о сотруднике, ID: {}", id);
        staffService.editStaff(id, staff.getName(), staff.getSurname());
    }

    @PostMapping(value = "/staff{id}/delete")
    public void deleteStaff(@PathVariable(value = "id") long id) {

        log.info("Инициирован процесс удаления сотрудника, ID: {}", id);
        staffService.removeStaff(id);
    }
}
