package com.proton.face_enroll.service;

import com.proton.face_enroll.dto.request.FindPeopleByIdReq;
import com.proton.face_enroll.dto.response.PeopleResponse;
import com.proton.face_enroll.model.People;

import java.util.List;

public interface PeopleService {
    People getPeopleById(String peopleId);
    List<PeopleResponse> findPeople(FindPeopleByIdReq findPeopleRequest);

}

