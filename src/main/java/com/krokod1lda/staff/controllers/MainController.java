package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.services.MainViewService;
import com.krokod1lda.staff.wrappers.mainViewWrappers.AllDataWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Log4j2
public class MainController {

    @Autowired
    MainViewService mainTableWrapper;

    @GetMapping(value = "/home")
    @ResponseBody
    public ResponseEntity<AllDataWrapper> viewAllData() {

        log.info("Инициирован процесс получения информации на главной странице");
        return new ResponseEntity<>(mainTableWrapper.getAllDataForMainView(), HttpStatus.OK);
    }
}
