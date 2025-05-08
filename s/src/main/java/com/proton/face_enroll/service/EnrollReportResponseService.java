package com.proton.face_enroll.service;

import com.proton.face_enroll.dto.response.EnrollReportResponse;

import java.text.ParseException;
import java.util.List;

public interface EnrollReportResponseService {
    List<EnrollReportResponse> getListTimekeepingOfPeople(String peopleId, Integer groupId, String fromDate,
                                                          String toDate, boolean isMorningLate, boolean noCheckIn) throws ParseException;

}
