package com.proton.face_enroll.service.impl;

import com.proton.face_enroll.dao.PeopleRepository;
import com.proton.face_enroll.dao.RegisterFaceRepository;
import com.proton.face_enroll.dto.RemoveFaceRequestPy;
import com.proton.face_enroll.dto.RemoveFaceResponsePy;
import com.proton.face_enroll.dto.odooreq.RegisterFaceRequest;
import com.proton.face_enroll.dto.odooreq.RemoveFaceRequest;
import com.proton.face_enroll.dto.odooreq.SearchFaceRequest;
import com.proton.face_enroll.dto.odoores.RegisterFaceResponse;
import com.proton.face_enroll.dto.odoores.RemoveFaceResponse;
import com.proton.face_enroll.dto.odoores.SearchFaceResponse;
import com.proton.face_enroll.dto.registerface.RegisterFaceRequestPy;
import com.proton.face_enroll.dto.registerface.RegisterFaceResponsePy;
import com.proton.face_enroll.dto.searchface.SearchFaceObject;
import com.proton.face_enroll.dto.searchface.SearchFaceResponsePy;
import com.proton.face_enroll.service.FaceEnrollService;
import com.proton.face_enroll.utils.ApiClient;
import com.proton.face_enroll.utils.FaceServiceUtil;
import com.proton.face_enroll.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.proton.face_enroll.utils.Constants.SOURCE;

/**
 * {@code @Package:} com.proton.face_enroll.dto.odoores
 * {@code @author:} nhanph
 * {@code @date:} 3/18/2025 2025
 * {@code @Copyright:} @nhanph
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class FaceEnrollServiceImpl implements FaceEnrollService {

    @Value(value = "${url.search.face.prt}")
    public  String urlFaceSearch;

    @Value(value = "${url.register.face.prt}")
    public  String urlFaceRegister;

    @Value(value = "${url.del.face.prt}")
    public  String urlFaceRemove;

    @Value(value = "${base.authorization}")
    public  String baseAuthorization;

    private final ApiClient apiClient;
    private final RegisterFaceRepository registerFaceRepository;
    private final PeopleRepository peopleRepository;

    @Override
    public RegisterFaceResponse registerFace(RegisterFaceRequest registerFaceRequest, String authorization) {
        RegisterFaceResponse registerFaceResponse = new RegisterFaceResponse();

        // Validate và xử lý request
        registerFaceResponse = FaceServiceUtil.validateAndProcessRequest(
                authorization,
                baseAuthorization,
                registerFaceRequest.getImagePath(),
                registerFaceResponse);

        // Nếu có lỗi, trả về response lỗi
        if (!"200".equals(registerFaceResponse.getResCode())) {
            return registerFaceResponse;
        }

        if (Boolean.TRUE.equals(peopleRepository.isExistPeopleByPeopleId(registerFaceRequest.getPeopleId()))){
            registerFaceResponse.setPeopleId(registerFaceRequest.getPeopleId());
            registerFaceResponse.setResCode("400");
            registerFaceResponse.setResMsg("Đã có id nhân viên này");
            return registerFaceResponse;
        }

        // Xử lý logic đăng ký khuôn mặt
        SearchFaceResponsePy searchFaceResponse = ResponseUtil.searchFace(
                ResponseUtil.getBase64Image(registerFaceRequest.getImagePath()),
                SOURCE,
                apiClient,
                urlFaceSearch);

        if ("SUCCESS".equals(searchFaceResponse.getStatus())) {
            Optional<SearchFaceObject> firstData = searchFaceResponse.getData().stream().findFirst();
            if (firstData.isPresent() && firstData.get().getScore() != null) {
                registerFaceResponse.setResCode("400");
                if (firstData.get().getPeopleId() == null){
                    registerFaceResponse.setResMsg("Nhận dạng khuôn mặt thất bại !");
                }else{
                    registerFaceResponse.setResMsg("Khuôn mặt đã tồn tại !");
                }
                registerFaceResponse.setOldPeopleId(firstData.get().getPeopleId());
                return registerFaceResponse;
            } else {
                return registerNewFace(registerFaceRequest, ResponseUtil.getBase64Image(registerFaceRequest.getImagePath()), registerFaceResponse);
            }
        } else {
            return ResponseUtil.buildErrorResponse(registerFaceResponse, "400", "Lỗi định dạng ảnh !");
        }
    }
    private RegisterFaceResponse registerNewFace(RegisterFaceRequest registerFaceRequest, String base64Image, RegisterFaceResponse registerFaceResponse) {
        RegisterFaceRequestPy registerRequest = createRegisterFaceRequest(base64Image, registerFaceRequest.getPeopleId());
        String registerCode = registerFaceApi(registerRequest);
        if ("OO".equals(registerCode)) {
            registerFaceRepository.registerFace(registerFaceRequest);
            registerFaceResponse.setResCode("200");
            registerFaceResponse.setResMsg("Thêm dữ liệu nhân viên thành công");
            registerFaceResponse.setPeopleId(registerFaceRequest.getPeopleId());
        } else {
            registerFaceResponse.setResCode("500");
            registerFaceResponse.setResMsg("Thêm dữ liệu nhân viên thất bại");
        }
        return registerFaceResponse;
    }

    public String registerFaceApi(RegisterFaceRequestPy registerFaceRequest) {
        RegisterFaceResponsePy registerFaceResponse = apiClient.sendPostRequest(urlFaceRegister, registerFaceRequest, RegisterFaceResponsePy.class);

        if (registerFaceResponse == null) {
            return "05";
        }

        if ("SUCCESS".equals(registerFaceResponse.getStatus())) {
            if (registerFaceResponse.getPeopleId() != null && registerFaceResponse.getPeopleId() != 0L) {
                return "OO";
            } else {
                return "05";
            }
        }
        return registerFaceResponse.getStatus();
    }


    private RegisterFaceRequestPy createRegisterFaceRequest(String base64Image, String peopleId) {
        return RegisterFaceRequestPy.builder()
                .createdAt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())))
                .isLiveCheck(false)
                .source(SOURCE)
                .metaData("")
                .peopleId(peopleId)
                .image(base64Image)
                .build();
    }

    @Override
    public SearchFaceResponse searchFace(SearchFaceRequest searchFaceRequest, String authorization) {
        SearchFaceResponse searchFaceResponse = new SearchFaceResponse();

        // Validate và xử lý request
        searchFaceResponse = FaceServiceUtil.validateAndProcessRequest(
                authorization,
                baseAuthorization,
                searchFaceRequest.getImagePath(),
                searchFaceResponse);

        // Nếu có lỗi, trả về response lỗi
        if (!"200".equals(searchFaceResponse.getResCode())) {
            return searchFaceResponse;
        }

        // Xử lý logic tìm kiếm khuôn mặt
        SearchFaceResponsePy searchFaceResponsePy = ResponseUtil.searchFace(
                ResponseUtil.getBase64Image(searchFaceRequest.getImagePath()),
                SOURCE,
                apiClient,
                urlFaceSearch);

        if ("SUCCESS".equals(searchFaceResponsePy.getStatus())) {
            Optional<SearchFaceObject> firstData = searchFaceResponsePy.getData().stream().findFirst();
            searchFaceResponse.setResCode("200");
            if (firstData.isPresent() && firstData.get().getPeopleId() != null) {
                searchFaceResponse.setResMsg("Khuôn mặt đã tồn tại !");
                searchFaceResponse.setPeopleId(firstData.get().getPeopleId());
            } else {
                searchFaceResponse.setResMsg("Khuôn mặt chưa tồn tại !");
            }
            return searchFaceResponse;
        } else {
            return ResponseUtil.buildErrorResponse(searchFaceResponse, "400", "Lỗi định dạng ảnh !");
        }
    }

    @Override
    public RemoveFaceResponse removeFace(RemoveFaceRequest removeFaceRequest, String authorization) {
        RemoveFaceResponse removeFaceResponse = new RemoveFaceResponse();
        if (authorization == null || authorization.isEmpty()) {
            removeFaceResponse.setResCode("401");
            removeFaceResponse.setResMsg("Nhập mã xác thực");
            return removeFaceResponse;
        }
        if (!baseAuthorization.equals(authorization)) {
            removeFaceResponse.setResCode("403");
            removeFaceResponse.setResMsg("Sai mã xác thực");
            return removeFaceResponse;
        }
        String resCode  = removeFace(removeFaceRequest);
        if (!"05".equals(resCode)) {
            removeFaceResponse.setResCode("200");
            removeFaceResponse.setResMsg("Xoá dữ liệu khuôn mặt thành công !");
        }else {
            removeFaceResponse.setResCode("500");
            removeFaceResponse.setResMsg("Xoá dữ liệu khuôn mặt thất bại !" + resCode );
        }
        return removeFaceResponse;
    }


    public String removeFace(RemoveFaceRequest removeFaceRequest) {

        RemoveFaceRequestPy removeFaceRequestPy= RemoveFaceRequestPy.builder()
                .peopleId(removeFaceRequest.getPeopleId())
                .source(SOURCE)
                .build();

        RemoveFaceResponsePy registerFaceResponse = apiClient.sendPostRequest(urlFaceRemove,
                removeFaceRequestPy, RemoveFaceResponsePy.class);

        if (registerFaceResponse == null) {
            return "05";
        }
        return registerFaceResponse.getStatus() +  " " + registerFaceResponse.getMessage();
    }
}
