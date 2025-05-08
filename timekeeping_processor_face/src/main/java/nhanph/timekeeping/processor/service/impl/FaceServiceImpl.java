package nhanph.timekeeping.processor.service.impl;

import lombok.RequiredArgsConstructor;
import nhanph.timekeeping.processor.dto.faceReq.RegisterFaceRequest;
import nhanph.timekeeping.processor.dto.faceReq.RemoveFaceRequest;
import nhanph.timekeeping.processor.dto.faceReq.SearchFaceRequest;
import nhanph.timekeeping.processor.dto.faceRes.RegisterFaceResponse;
import nhanph.timekeeping.processor.dto.faceRes.RemoveFaceResponse;
import nhanph.timekeeping.processor.dto.faceRes.SearchFaceResponse;
import nhanph.timekeeping.processor.service.FaceService;
import nhanph.timekeeping.processor.util.ApiClient;
import nhanph.timekeeping.processor.util.Constants;
import org.springframework.stereotype.Service;

/**
 * @Package: nhanph.timekeeping.processor.service.impl
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

@Service
@RequiredArgsConstructor
public class FaceServiceImpl implements FaceService {
    private final ApiClient apiClient;

    @Override
    public RegisterFaceResponse registerFace(RegisterFaceRequest registerFaceRequest) {
        return apiClient.sendPostRequest(
                Constants.SERVICE_FACE.URL_FACE + Constants.SERVICE_FACE.REGISTER_FACE,
                registerFaceRequest,
                RegisterFaceResponse.class,
                null
        );
    }

    @Override
    public SearchFaceResponse searchFace(SearchFaceRequest searchFaceRequest) {
        return apiClient.sendPostRequest(
                Constants.SERVICE_FACE.URL_FACE + Constants.SERVICE_FACE.SEARCH,
                searchFaceRequest,
                SearchFaceResponse.class,
                null
        );
    }

    @Override
    public RemoveFaceResponse removeFace(RemoveFaceRequest removeFaceRequest) {
        return apiClient.sendPostRequest(
                Constants.SERVICE_FACE.URL_FACE + Constants.SERVICE_FACE.REMOVE_FACE,
                removeFaceRequest,
                RemoveFaceResponse.class,
                null
        );
    }
}
