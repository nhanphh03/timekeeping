package com.proton.face_enroll.dao;

import com.proton.face_enroll.model.Config;
import com.proton.face_enroll.model.LivenessHistory;
import com.proton.face_enroll.model.Timesheet;
import com.proton.face_enroll.model.Whitelist;
import java.sql.Timestamp;
import java.util.List;

public interface UtilRepository {
    Integer insertLivenessHistory(LivenessHistory livenessHistory);

    Timestamp getCurrentSystemTime();

    List<Whitelist> getListWhiteList();

    List<Config> getListConfig();

    List<Timesheet> getTimesheetSQL(String fromTime, String toTime);
}
