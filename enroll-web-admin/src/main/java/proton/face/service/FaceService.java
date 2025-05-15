package proton.face.service;

import proton.face.dto.registerface.RegisterFaceRequest;
import proton.face.dto.searchface.SearchFaceRequest;

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
