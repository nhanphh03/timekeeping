package com.proton.face_enroll.controller;

import com.proton.face_enroll.dto.request.FindPeopleByIdReq;
import com.proton.face_enroll.model.People;
import com.proton.face_enroll.service.PeopleService;
import com.proton.face_enroll.utils.CallApiFace;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/people"})
public class PeopleController {

    final PeopleService peopleService;
    final CallApiFace apiFace;

    public PeopleController(PeopleService peopleService, CallApiFace apiFace) {
        this.peopleService = peopleService;
        this.apiFace = apiFace;
    }

    @GetMapping({"/getById/{id}"})
    public ResponseEntity<People> getPeopleById(@PathVariable("id") String peopleId) {
        return ResponseEntity.ok(this.peopleService.getPeopleById(peopleId));
    }

    @PostMapping({"/create"})
    public ResponseEntity<People> createPeople(@RequestBody People people) {
        apiFace.sendSearchFace(people);
        return ResponseEntity.ok(people);
    }

    @GetMapping({"/search"})
    public ResponseEntity<?> searchEmployeeByIdWildCard(@RequestBody FindPeopleByIdReq findPeopleRequest,
                                                        @RequestHeader(value = "Authorization") String authorization) {
        if (!authorization.equals("dwKddrv9HhHMU0ylpsxpuoZPMkr6KaxDWORJPmss")) {
            return ResponseEntity.badRequest().body("Authorization is invalid");
        } else return ResponseEntity.ok(this.peopleService.findPeople(findPeopleRequest));
    }
}

