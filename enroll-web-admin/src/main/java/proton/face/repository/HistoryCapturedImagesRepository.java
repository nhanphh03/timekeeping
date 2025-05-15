package proton.face.repository;

import proton.face.entity.CapturedImages;
import proton.face.entity.People;

import java.util.List;

public interface HistoryCapturedImagesRepository {

    List<CapturedImages> getPeopleCapturedImages(String peopleId, String status, String fromTime, String toTime);

    String updatePeople(People people, CapturedImages capturedImages) throws Exception;

    boolean reRegisterPeople(People people, CapturedImages ci);

    CapturedImages getCapturedImagesById(int id) ;

}
