package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.services.MainTableWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MainController {

    @Autowired
    MainTableWrapper mainTableWrapper;

    @GetMapping(value = "/home")
    @ResponseBody
    public ResponseEntity<MainTableWrapper.AllDataWrapper> viewAllData() {

        return new ResponseEntity<>(mainTableWrapper.getAllDataForMainView(), HttpStatus.OK);
    }
}
