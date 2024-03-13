package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Staff;
import com.krokod1lda.staff.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class StaffController {


    @Autowired
    private StaffService staffService;


    @GetMapping("/allStaff")
    @ResponseBody
    public ResponseEntity<StaffService.AllStaffWrapper> allStaff() {

        return new ResponseEntity<>(staffService.getAllStaff(), HttpStatus.OK);
    }


    @GetMapping(value = "/staff{id}")
    @ResponseBody
    public ResponseEntity<StaffService.StaffCardWrapper> viewStaff(@PathVariable(value = "id") long id) {

        return new ResponseEntity<>(staffService.wrapTheList(staffService.getStaffById(id), staffService.getRecordsAndTime(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/staff{id}/edit")
    @ResponseBody
    public ResponseEntity<Staff> getEditStaff(@PathVariable(value = "id") long id) {

        return new ResponseEntity<>(staffService.getStaffById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/addStaff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newStaff(@RequestBody Staff staff) {

        staffService.addStaff(staff.getName(), staff.getSurname());
        return;
    }


    @PostMapping(value = "/staff{id}/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editStaff(@RequestBody Staff staff) {

        staffService.editStaff(staff.getId(), staff.getName(), staff.getSurname());
        return;
    }

    @PostMapping(value = "/staff{id}/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteStaff(@RequestBody Staff staff) {

        staffService.removeStaff(staff.getId());
        return;
    }
}
