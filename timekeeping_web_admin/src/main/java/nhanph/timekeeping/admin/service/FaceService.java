package nhanph.timekeeping.admin.service;

import nhanph.timekeeping.admin.dto.registerface.RegisterFaceRequest;
import nhanph.timekeeping.admin.dto.searchface.SearchFaceRequest;

/**
 * @Package: proton.face.service
 * @author: nhanph
 * @date: 3/3/2025 2025
 * @Copyright: @nhanph
 */
public interface FaceService {

    String searchFace(SearchFaceRequest searchFaceRequest);
    String registerFace(RegisterFaceRequest registerFaceRequest);

}
