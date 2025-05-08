package nhanph.timekeeping.processor.service;

import nhanph.timekeeping.processor.dto.faceReq.RegisterFaceRequest;
import nhanph.timekeeping.processor.dto.faceReq.RemoveFaceRequest;
import nhanph.timekeeping.processor.dto.faceReq.SearchFaceRequest;
import nhanph.timekeeping.processor.dto.faceRes.RegisterFaceResponse;
import nhanph.timekeeping.processor.dto.faceRes.RemoveFaceResponse;
import nhanph.timekeeping.processor.dto.faceRes.SearchFaceResponse;

/**
 * @Package: nhanph.timekeeping.processor.service
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

public interface FaceService {
    RegisterFaceResponse registerFace(RegisterFaceRequest registerFaceRequest);

    SearchFaceResponse searchFace(SearchFaceRequest searchFaceRequest);

    RemoveFaceResponse removeFace(RemoveFaceRequest removeFaceRequest);
}
