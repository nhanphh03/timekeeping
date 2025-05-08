package com.proton.face_enroll.controller;

import com.proton.face_enroll.dto.request.EnrollReportReq;
import com.proton.face_enroll.service.EnrollReportResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping({"/api/enroll-report"})
public class EnrollReportController {

    private final EnrollReportResponseService enrollReportResponseService;


    public EnrollReportController(EnrollReportResponseService enrollReportResponseService) {
        this.enrollReportResponseService = enrollReportResponseService;
    }

    @PostMapping()
    public ResponseEntity<Object> getListTimekeepingOfPeople(@RequestBody(required = false) EnrollReportReq enrollReportReq,
                                                        @RequestHeader(value = "Authorization") String authorization) throws ParseException {
        if (authorization == null || authorization.isEmpty()) {
            return ResponseEntity.badRequest().body("Authorization is required");
        } else if (!authorization.equals("dwKddrv9HhHMU0ylpsxpuoZPMkr6KaxDWORJPmss")) {
            return ResponseEntity.badRequest().body("Authorization is invalid");
        } else
            return ResponseEntity.ok(this.enrollReportResponseService.
                    getListTimekeepingOfPeople(enrollReportReq.getPeopleId(), enrollReportReq.getGroupId(),
                            enrollReportReq.getFromDate(), enrollReportReq.getToDate(), enrollReportReq.isMorningLate(),
                            enrollReportReq.isNoCheckIn()));
    }
}
