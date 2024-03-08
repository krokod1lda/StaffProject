//package com.krokod1lda.staff.controllers;
//
//import com.krokod1lda.staff.models.Staff;
//import com.krokod1lda.staff.repositories.StaffRepository;
//import com.krokod1lda.staff.services.CalculatingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class MainController {
//
//    @Autowired
//    private CalculatingService calculatingService;
//    @Autowired
//    StaffRepository staffRepository;
//    @GetMapping("/")
//    public Iterable<Staff> home() {
//
//        return this.staffRepository.findAll();
//    }
//}


//Код ниже как было ранее, с использоваием чистого html


package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.entityAttributes.MainAttributes;
import com.krokod1lda.staff.entityAttributes.RecordAttributes;
import com.krokod1lda.staff.entityAttributes.StaffAttributes;
import com.krokod1lda.staff.services.CalculatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CalculatingService calculatingService;
    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(RecordAttributes.RECORDS_BY_DATES_LIST.getValue(), calculatingService.getRecordsByDatesList());
        model.addAttribute(StaffAttributes.STAFF_BY_ID.getValue(), calculatingService.getStaffById()); // передали словарь с ключом - id, значением - объектом staff
        model.addAttribute(MainAttributes.TITLE.getValue(), MainAttributes.MAIN_PAGE.getValue());
        return "home";
    }
}
