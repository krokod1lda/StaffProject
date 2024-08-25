package com.krokod1lda.staff.controllers;

import com.krokod1lda.staff.dto.AdditionalRequestDto;
import com.krokod1lda.staff.services.AdditionalService;
import com.krokod1lda.staff.wrappers.additionalWrappers.AdditionalFullWithStaffNameWrapper;
import com.krokod1lda.staff.wrappers.additionalWrappers.AllAdditionalsWrapper;
import jakarta.validation.Valid;
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
    public void newAdditional(@Valid @RequestBody AdditionalRequestDto additional) {

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
    public AdditionalFullWithStaffNameWrapper getAdditional(@PathVariable(value = "id") long id) {

        log.info("Инициирован процесс получения информации о additional, ID : {}", id);
        return additionalService.getAdditionalWIthStaffNameById(id);
    }

    @PostMapping(value = "/additional{id}/delete")
    public void deleteAdditional(@PathVariable(value = "id") long id) {

        log.info("Инициирован процесс удаления additional");
        additionalService.removeAdditional(id);
    }
}