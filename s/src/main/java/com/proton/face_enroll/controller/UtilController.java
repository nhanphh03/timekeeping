package com.proton.face_enroll.controller;


import com.proton.face_enroll.dao.UtilRepository;
import com.proton.face_enroll.model.Config;
import com.proton.face_enroll.model.ImageData;
import com.proton.face_enroll.model.LivenessHistory;
import com.proton.face_enroll.model.Timesheet;
import com.proton.face_enroll.model.Whitelist;
import com.proton.face_enroll.utils.MinioUtil;
import java.sql.Timestamp;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/util"})
@Log4j2
public class UtilController {

    private final UtilRepository utilRepo;

    private final MinioUtil minIO;

    public UtilController( UtilRepository utilRepo, MinioUtil minIO) {
        this.utilRepo = utilRepo;
        this.minIO = minIO;
    }

    @PostMapping({"/livenessHistory"})
    public ResponseEntity<Integer> insertLivenessHistory(@RequestBody LivenessHistory history) {
        return ResponseEntity.ok(this.utilRepo.insertLivenessHistory(history));
    }

    @GetMapping({"/system_timestamp"})
    public ResponseEntity<Timestamp> getSystemTimestamp() {
        return ResponseEntity.ok(this.utilRepo.getCurrentSystemTime());
    }

    @GetMapping({"/whitelist"})
    public ResponseEntity<List<Whitelist>> getListWhiteList() {
        return ResponseEntity.ok(this.utilRepo.getListWhiteList());
    }

    @GetMapping({"/cofigs"})
    public ResponseEntity<List<Config>> getListConfig() {
        return ResponseEntity.ok(this.utilRepo.getListConfig());
    }

    @GetMapping({"/timesheets/"})
    public ResponseEntity<List<Timesheet>> getTimesheetSQL(@RequestParam(required = false,name = "fromTime") String fromTime, @RequestParam(required = false,name = "toTime") String toTime) {
        return ResponseEntity.ok(this.utilRepo.getTimesheetSQL(fromTime, toTime));
    }

    @PostMapping({"/uploadImage"})
    public ResponseEntity<String> uploadImage(@RequestBody ImageData data) {
        String bucketName = "";

        try {
            bucketName = this.minIO.uploadImage(data.getFileName(), data.getImageData());
        } catch (Exception var4) {
            log.error(var4.getMessage());
        }

        return ResponseEntity.ok(bucketName);
    }
}
