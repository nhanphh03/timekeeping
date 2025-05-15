package proton.face.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proton.face.dto.registerface.RegisterFaceRequest;
import proton.face.dto.registerface.RegisterFaceResponse;
import proton.face.dto.searchface.SearchFaceObject;
import proton.face.dto.searchface.SearchFaceRequest;
import proton.face.dto.searchface.SearchFaceResponse;
import proton.face.service.FaceService;
import proton.face.util.ApiClient;

import static proton.face.constant.Constants.CallAPIResponse.ERROR_FACE;
import static proton.face.constant.Constants.FaceResponseCodes.FACE_EXISTED_RES;
import static proton.face.constant.Constants.FaceResponseCodes.FACE_NOT_EXISTED_RES;
import static proton.face.constant.Constants.FaceResponseCodes.REGISTER_FACE_FAIL_RES;
import static proton.face.constant.Constants.FaceResponseCodes.REGISTER_FACE_SUCCESS_RES;
import static proton.face.constant.Constants.URL_FACE_REGISTER;
import static proton.face.constant.Constants.URL_FACE_SEARCH;


/**
 * @Package: proton.face.service.impl
 * @author: nhanph
 * @date: 3/3/2025 2025
 * @Copyright: @nhanph
 */

@Service
@RequiredArgsConstructor
public class FaceServiceImpl implements FaceService {

    private final ApiClient apiClient;


    @Override
    public String searchFace(SearchFaceRequest searchFaceRequest) {
        SearchFaceResponse searchFaceResponse = apiClient.sendPostRequest(URL_FACE_SEARCH, searchFaceRequest,
                SearchFaceResponse.class);
        if (searchFaceResponse == null) {
            return "";
        }
        if ("SUCCESS".equals(searchFaceResponse.getStatus())){
            if (searchFaceResponse.getData() != null && !searchFaceResponse.getData().isEmpty()) {
                SearchFaceObject firstData = searchFaceResponse.getData().get(0);
                if (firstData.getPeopleId() != null) {
                    return FACE_EXISTED_RES;
                } else {
                    return FACE_NOT_EXISTED_RES;
                }
            }else{
                return FACE_NOT_EXISTED_RES;
            }
        }
        return searchFaceResponse.getStatus();
    }

    @Override
    public String registerFace(RegisterFaceRequest registerFaceRequest) {

        RegisterFaceResponse registerFaceResponse = apiClient.sendPostRequest(URL_FACE_REGISTER,
                registerFaceRequest, RegisterFaceResponse.class);

        if (registerFaceResponse == null) {
            return ERROR_FACE;
        }

        if ("SUCCESS".equals(registerFaceResponse.getStatus())){
            if (registerFaceResponse.getPeopleId() != null
                    && registerFaceResponse.getPeopleId() != 0L) {
                return REGISTER_FACE_SUCCESS_RES;
            }else{
                return REGISTER_FACE_FAIL_RES;
            }
        }
        return registerFaceResponse.getStatus();
    }


}
