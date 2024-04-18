package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Additional;
import com.krokod1lda.staff.services.AdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdditionalController {

    @Autowired
    AdditionalService additionalService;

    @PostMapping(value = "/addAdditional", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newStaff(@RequestBody Additional additional) {

        additionalService.addAdditional(additional.getStaffId(), additional.getType(),
                additional.getPrice(), additional.getDate(), additional.getDescription());
        return;
    }

    @GetMapping("/allAdditionals")
    @ResponseBody
    public AdditionalService.AllAdditionalsWrapper allAdditionals() {

        return additionalService.getAllAdditionalsByDates();
    }

    @GetMapping(value = "/additional{id}/edit")
    @ResponseBody
    public AdditionalService.AdditionalFullWithStaffName getEditAdditional(@PathVariable(value = "id") long id) {

        return additionalService.getAdditionalWIthStaffNameById(id);
    }

    @PostMapping(value = "/additional{id}/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteAdditional(@RequestBody Additional additional) {

        additionalService.removeAdditional(additional.getId());
        return;
    }
}