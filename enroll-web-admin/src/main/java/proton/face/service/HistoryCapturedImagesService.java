package proton.face.service;

import proton.face.entity.CapturedImages;
import proton.face.entity.People;

/**
 * @Package: proton.face.service
 * @author: nhanph
 * @date: 3/4/2025 2025
 * @Copyright: @nhanph
 */
public interface HistoryCapturedImagesService {

    String saveNewPeople(People people, CapturedImages capturedImages) throws Exception;

}
