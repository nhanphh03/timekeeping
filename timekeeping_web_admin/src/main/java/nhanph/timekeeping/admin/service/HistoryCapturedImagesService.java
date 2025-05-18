package nhanph.timekeeping.admin.service;

import nhanph.timekeeping.admin.entity.CapturedImages;

/**
 * @Package: proton.face.service
 * @author: nhanph
 * @date: 3/4/2025 2025
 * @Copyright: @nhanph
 */
public interface HistoryCapturedImagesService {

    String saveNewPeople(People people, CapturedImages capturedImages) throws Exception;

}
