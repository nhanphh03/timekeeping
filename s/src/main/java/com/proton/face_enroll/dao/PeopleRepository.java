package com.proton.face_enroll.dao;

import com.proton.face_enroll.dto.request.FindPeopleByIdReq;
import com.proton.face_enroll.dto.response.PeopleResponse;
import com.proton.face_enroll.model.People;

import java.util.List;

public interface PeopleRepository {
    People getPeopleById(String peopleId);

    void createPeople(People people);

    List<PeopleResponse> findPeopleById(FindPeopleByIdReq findPeopleRequest);

    Boolean isExistPeopleByPeopleId(String peopleId);

    Boolean isExistPeopleByPhoneNumber(String mobilePhone);
}
