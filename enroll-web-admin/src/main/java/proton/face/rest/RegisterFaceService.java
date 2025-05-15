package proton.face.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import proton.face.constant.Constants;
import proton.face.constant.StatusConstant;
import proton.face.controller.LoginController;
import proton.face.entity.People;
import proton.face.repository.impl.PeopleRepositoryImpl;
import proton.face.response.DataResponseFromCheckFacesSearch;
import proton.face.response.DataResponseFromRegisterFaceSearch;
import proton.face.service.MinioService;
import proton.face.util.HttpUtils;
import proton.face.util.Utils;

import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterFaceService {

    private final PeopleRepositoryImpl peopleRepository;
    private final HttpUtils httpUtils;
    private final MinioService minioService;

    public Response registerFace(People people, List<List<String>> faces,
                                 MultipartFile fileUpload, int tabIndex) throws Exception {

        boolean flagIsLiveLess = true;
        if (tabIndex == 0) {
            String imagePath = minioService.uploadImage(people.getPeopleId(), Base64.getDecoder().decode(faces.get(0).get(0)));
            people.setImagePath(imagePath);

            DataResponseFromCheckFacesSearch checkFacesSearch = httpUtils.callAPIToSearch("Constants.SOURCE",
                    faces.get(0).get(0));
            String peopleId = checkFacesSearch.getPeopleIdOfFaceV2();
            if (peopleId != null) {
                return Response.error("Khuôn mặt đã tồn tại trong hệ thống");
            }
            try {
                boolean result;
                if (!flagIsLiveLess) {
                    result = peopleRepository.registerPeople(people)
                            && peopleRepository.registerPeopleRegImage(people.getPeopleId(), faces)
                            && peopleRepository.registerPeopleWhitelist(people.getPeopleId(),
                            Objects.requireNonNull(LoginController.getSessionUser()).getId());
                } else {
                    result = peopleRepository.registerPeople(people)
                            && peopleRepository.registerPeopleRegImage(people.getPeopleId(), faces);
                }

                if (!result) {
                    return Response.error("Đăng ký thất bại");
                } else {
                    DataResponseFromRegisterFaceSearch dataResponse = httpUtils.callAPIToRegister(faces.get(0).get(0), people.getPeopleId());
                    if (dataResponse != null && dataResponse.getStatus().equals(StatusConstant.STATUS_SUCCESS)) {
                        return Response.success("Đăng ký thành công", people);
                    } else {
                        return Response.error("Đăng ký thất bại");
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else if (tabIndex == 1) {
            String fileName = Utils.getNameFileImage(people.getPeopleId());
            people.setImagePath(fileName);

            InputStream inputStream = fileUpload.getInputStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            minioService.uploadImage(fileName, bytes);

            String base64 = Base64.getEncoder().encodeToString(bytes);
            DataResponseFromCheckFacesSearch checkFacesSearch = httpUtils.callAPIToSearch("Constants.SOURCE",
                    base64);
            String peopleId = checkFacesSearch.getPeopleIdOfFaceV2();
            if (peopleId != null) {
                return Response.error("Khuôn mặt đã tồn tại trong hệ thống");
            }

            try {
                boolean result = peopleRepository.registerPeople(people) && peopleRepository.registerPeopleRegImage(people.getPeopleId(), fileName);

                if (result) {
                    DataResponseFromRegisterFaceSearch dataResponse = httpUtils.callAPIToRegister(base64, people.getPeopleId());
                    if (dataResponse != null && dataResponse.getStatus().equals(StatusConstant.STATUS_SUCCESS)) {
                        return Response.success("Đăng ký thành công", people);
                    } else return Response.error("Đăng ký thất bại");
                } else {
                    return Response.error("Đăng ký thất bại");
                }
            } catch (Exception e) {
                log.error(String.valueOf(e));
            }
        }
        return null;
    }
}
