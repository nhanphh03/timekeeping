package com.proton.face_enroll.dao;

import com.proton.face_enroll.dto.response.EnrollReportResponse;
import com.proton.face_enroll.model.People;

import java.util.List;

public interface EnrollReportResponseRepository {

    List<EnrollReportResponse> getListTimekeepingOfPeople(String peopleId, Integer groupId, String fromDate,
                                                          String toDate, boolean isMorningLate);


    List<People> getPeopeIdList(String peopleId);
}
