package proton.face.repository;

import proton.face.entity.Detection;
import proton.face.entity.People;

import java.util.List;

public interface HistoryPeopleSignedUpRepository {


    List<Detection> getPeopleSignedUp(String peopleId, String status, String fromTime, String toTime);

    String updatePeople(People people, Detection detection) throws Exception;


    Detection getPeopleSignedUpById(int id);
}
