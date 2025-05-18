package nhanph.timekeeping.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import nhanph.timekeeping.admin.dto.registerface.RegisterFaceRequest;
import nhanph.timekeeping.admin.dto.searchface.SearchFaceRequest;
import nhanph.timekeeping.admin.entity.CapturedImages;
import nhanph.timekeeping.admin.entity.Detection;
import nhanph.timekeeping.admin.repository.DetectionRepository;
import nhanph.timekeeping.admin.repository.CapturedRepository;
import nhanph.timekeeping.admin.repository.PeopleRegImageRepository;
import nhanph.timekeeping.admin.service.HistoryCapturedImagesService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static nhanph.timekeeping.admin.constant.Constants.CallAPIResponse.INPUT_ERROR;
import static nhanph.timekeeping.admin.constant.Constants.CallAPIResponse.REGISTER_FACE_FAIL;
import static nhanph.timekeeping.admin.constant.Constants.CallAPIResponseCodes.INPUT_ERROR_RES;
import static nhanph.timekeeping.admin.constant.Constants.FaceResponse.FACE_EXISTED;
import static nhanph.timekeeping.admin.constant.Constants.FaceResponseCodes.FACE_EXISTED_RES;
import static nhanph.timekeeping.admin.constant.Constants.FaceResponseCodes.FACE_NOT_EXISTED_RES;
import static nhanph.timekeeping.admin.constant.Constants.FaceResponseCodes.REGISTER_FACE_FAIL_RES;
import static nhanph.timekeeping.admin.constant.Constants.FaceResponseCodes.REGISTER_FACE_SUCCESS_RES;
import static nhanph.timekeeping.admin.constant.Constants.SOURCE;
import static nhanph.timekeeping.admin.util.ApiClient.downloadFileEncodeToBase64;

/**
 * @Package: proton.face.service.impl
 * @author: nhanph
 * @date: 3/4/2025 2025
 * @Copyright: @nhanph
 */

@Service
@RequiredArgsConstructor
public class HistoryCapturedImagesServiceImpl implements HistoryCapturedImagesService {

    private final FaceServiceImpl faceService;
    private final CapturedRepository capturedRepository;
    private final DetectionRepository detectionJPARepository;
    private final PeopleRegImageRepository peopleRegImageRepository;
    @Override
    public String saveNewPeople(People people, CapturedImages capturedImages) throws Exception{

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(date);
        // check khuôn mặt
        String imgEncode = downloadFileEncodeToBase64(capturedImages.getCapturedImagePath());
        SearchFaceRequest searchRequest = SearchFaceRequest.builder().image(imgEncode).source(SOURCE).build();
        String responseSearch = faceService.searchFace(searchRequest);
        if (INPUT_ERROR_RES.equals(responseSearch)) return INPUT_ERROR;
        if (FACE_EXISTED_RES.equals(responseSearch)) {
            return FACE_EXISTED;
        }
        if (FACE_NOT_EXISTED_RES.equals(responseSearch)) {
            // thêm khuôn mặt
            RegisterFaceRequest registerRequest = createRegisterFaceRequest(imgEncode, people.getPeopleId());
            String responseRegister = faceService.registerFace(registerRequest);
            if (INPUT_ERROR_RES.equals(responseRegister)) {
                return INPUT_ERROR;
            }
            if (REGISTER_FACE_FAIL_RES.equals(responseRegister)) {
                return REGISTER_FACE_FAIL;
            }
            if (REGISTER_FACE_SUCCESS_RES.equals(responseRegister)){

                //thêm vào db
                people.setImagePath(people.getImagePathNoHostV2());
                capturedRepository.save(people);
//                peopleRegImageRepository.updateCapturedImagesById(people.getPeopleId(), capturedImages.getId());

                Detection detection = Detection.builder()
                        .capturedImagePath(people.getImagePathNoHostV2())
                        .cameraId(capturedImages.getCameraId())
                        .peopleId(people.getPeopleId())
                        .responseTime(null)
                        .recognitionStatus(null)
                        .responseRaw(null)
                        .capturedTime(new Timestamp(System.currentTimeMillis()))
                        .createdTime(formattedDate)
                        .dayFirstTime(capturedImages.getCreatedTime())
                        .dayNoonTime(null)
                        .livenessStatus("TRUE")
                        .build();

                detectionJPARepository.save(detection);

                PeopleRegImage peopleRegImage = PeopleRegImage.builder()
                        .status(1)
                        .peopleId(people.getPeopleId())
                        .createdTime(new Timestamp(System.currentTimeMillis()))
                        .image(people.getImagePathNoHostV2())
                        .build();

                peopleRegImageRepository.save(peopleRegImage);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Đăng ký nhân viên thành công " + people.getFullName(), ""));
                return "";
            }
        }
        return "";
    }

    private RegisterFaceRequest createRegisterFaceRequest(String base64Image, String peopleCode) {
        return RegisterFaceRequest.builder()
                .createdAt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())))
                .isLiveCheck(false)
                .source(SOURCE)
                .metaData("")
                .peopleId(peopleCode)
                .image(base64Image)
                .build();
    }
}
