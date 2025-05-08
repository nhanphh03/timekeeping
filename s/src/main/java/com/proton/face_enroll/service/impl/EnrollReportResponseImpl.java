package com.proton.face_enroll.service.impl;

import com.proton.face_enroll.dao.EnrollReportResponseRepository;
import com.proton.face_enroll.dto.response.EnrollReportResponse;
import com.proton.face_enroll.model.People;
import com.proton.face_enroll.service.EnrollReportResponseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EnrollReportResponseImpl implements EnrollReportResponseService {

    private final EnrollReportResponseRepository enrollReportResponseRepository;

    public EnrollReportResponseImpl(EnrollReportResponseRepository enrollReportResponseRepository) {
        this.enrollReportResponseRepository = enrollReportResponseRepository;
    }

    @Override
    public List<EnrollReportResponse> getListTimekeepingOfPeople(String peopleId, Integer groupId, String fromDate,
                                                                 String toDate, boolean isMorningLate,
                                                                 boolean noCheckin) throws ParseException {
        List<EnrollReportResponse> timekeepingList = new ArrayList<>();


        if (noCheckin) {
            Date fromDate1;
            Date toDate1;
            if (StringUtils.isEmpty(fromDate) && StringUtils.isEmpty(toDate)) {
                fromDate1 = Date.from(
                        LocalDate.now()
                                .withDayOfMonth(1)
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                );
                toDate1 = Date.from(
                        LocalDate.now()
                                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                                .atTime(23, 59, 59)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                );
            } else {
                fromDate1 = new SimpleDateFormat("dd-MM-yyyy").parse(fromDate);
                toDate1 = new SimpleDateFormat("dd-MM-yyyy").parse(toDate);
            }

            LocalDate dtFrom;
            LocalDate dtTo;
            if (fromDate == null) {
                dtFrom = YearMonth.now().atDay(1);
            } else {
                dtFrom = fromDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            if (toDate == null) {
                dtTo = LocalDate.now();
            } else {
                dtTo = toDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            List<LocalDate> lsDate = new ArrayList<>();
            while (dtTo.isAfter(dtFrom) || dtTo.isEqual(dtFrom)) {
                if (!(dtTo.getDayOfWeek() == DayOfWeek.SATURDAY || dtTo.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    lsDate.add(dtTo);
                }
                dtTo = dtTo.minusDays(1);
            }


            Map<String, EnrollReportResponse> timekeepingMap = new TreeMap<>();
            for (EnrollReportResponse item : timekeepingList) {
                timekeepingMap.put(item.getPeopleId() + "_" + item.getEnrolledDate(), item);
            }


            List<People> allPeopleId = enrollReportResponseRepository.getPeopeIdList(peopleId);

            timekeepingList = new ArrayList<>();
            for (People people : allPeopleId) {
                for (LocalDate cd : lsDate) {
                    String key = people.getPeopleId() + "_" + cd.toString();
                    if (!timekeepingMap.containsKey(key)) {
                        EnrollReportResponse noSignUp = new EnrollReportResponse();
                        noSignUp.setPeopleId(people.getPeopleId());
                        noSignUp.setEnrolledDate(cd.toString());
                        noSignUp.setDescription("Không chấm công");
                        noSignUp.setImagePath(people.getImageBase64());
                        noSignUp.setFullName(people.getName());
                        if(people.getGroup().getName() != null) {
                            noSignUp.setGroupName(people.getGroup().getName());
                        }

                        timekeepingList.add(noSignUp);
                    }
                }
            }
        } else{
//            String strFromDate = null;
//            String strToDate = null;
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat formatPattern = new SimpleDateFormat("yyyy-MM-dd");
//
//            if (!StringUtils.isEmpty(fromDate)) {
//                strFromDate = formatPattern.format(fromDate);
//            }
//            if (!StringUtils.isEmpty(toDate)) {
//                strToDate = formatPattern.format(toDate);
//            }

            timekeepingList = enrollReportResponseRepository.getListTimekeepingOfPeople(peopleId, groupId, fromDate, toDate, isMorningLate);
        }



        return timekeepingList;
    }

}
