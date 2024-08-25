package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.models.Additional;
import com.krokod1lda.staff.services.AdditionalService;
import com.krokod1lda.staff.wrappers.additionalWrappers.AdditionalFullWithStaffNameWrapper;
import com.krokod1lda.staff.wrappers.additionalWrappers.AllAdditionalsWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Log4j2
public class AdditionalController {

    @Autowired
    AdditionalService additionalService;

    @PostMapping(value = "/addAdditional", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newAdditional(@RequestBody Additional additional) {

        additionalService.addAdditional(additional.getStaffId(), additional.getType(), additional.getPrice(), additional.getDate(), additional.getDescription());
    }

    @GetMapping("/allAdditionals")
    @ResponseBody
    public AllAdditionalsWrapper allAdditionals() {

        log.info("Инициирован процесс получения информации обо всех продажах, премиях и штрафах");
        return additionalService.getAllAdditionalsByDates();
    }

    @GetMapping(value = "/additional{id}/edit")
    @ResponseBody
    public AdditionalFullWithStaffNameWrapper getEditAdditional(@PathVariable(value = "id") long id) {

        log.info("Инициирован процесс получения информации о additional, ID : {}", id);
        return additionalService.getAdditionalWIthStaffNameById(id);
    }

    @PostMapping(value = "/additional{id}/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteAdditional(@RequestBody Additional additional) {

        log.info("Инициирован процесс удаления additional");
        additionalService.removeAdditional(additional.getId());
    }
}