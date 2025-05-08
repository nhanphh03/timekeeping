package com.proton.face_enroll.service.impl;

import com.proton.face_enroll.dao.PeopleRepository;
import com.proton.face_enroll.dto.request.FindPeopleByIdReq;
import com.proton.face_enroll.dto.response.BasicResponse;
import com.proton.face_enroll.dto.response.PeopleResponse;
import com.proton.face_enroll.model.People;
import com.proton.face_enroll.service.PeopleService;
import com.proton.face_enroll.utils.EnrollWebAdminApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepo;
    private final EnrollWebAdminApi enrollWebAdminApi;

    public PeopleServiceImpl(PeopleRepository peopleRepo, EnrollWebAdminApi enrollWebAdminApi) {
        this.peopleRepo = peopleRepo;
        this.enrollWebAdminApi = enrollWebAdminApi;
    }

    public People getPeopleById(String peopleId) {
        return this.peopleRepo.getPeopleById(peopleId);
    }

    @Override
    public List<PeopleResponse> findPeople(FindPeopleByIdReq findPeopleRequest) {
        BasicResponse basicResponse = enrollWebAdminApi.callApiOnOdooToCheckEmployee(findPeopleRequest);
        if (basicResponse.getCode() == null) {
            log.debug("Error Null: {}", basicResponse.getMessage());
            return null;
        }else if (basicResponse.getCode().equals(1)) {
            log.debug("Error: {}", basicResponse.getMessage());
            return null;
        }else {
            log.debug("Success: {}", basicResponse.getMessage());
            return peopleRepo.findPeopleById(findPeopleRequest);
        }
    }
}
