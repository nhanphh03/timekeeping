package proton.face.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.file.UploadedFile;
import proton.face.constant.StatusConstant;
import proton.face.dto.registerface.RegisterFaceRequest;
import proton.face.dto.searchface.SearchFaceRequest;
import proton.face.entity.CustomerType;
import proton.face.entity.Groups;
import proton.face.entity.People;
import proton.face.repository.CustomerTypeRepository;
import proton.face.repository.GroupRepository;
import proton.face.repository.PeopleRepository;
import proton.face.dto.response.DataResponseFromRegisterFaceSearch;
import proton.face.service.MinioService;
import proton.face.service.impl.FaceServiceImpl;
import proton.face.util.HttpUtils;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static proton.face.constant.Constants.CallAPIResponse.INPUT_ERROR;
import static proton.face.constant.Constants.CallAPIResponse.REGISTER_FACE_FAIL;
import static proton.face.constant.Constants.CallAPIResponseCodes.INPUT_ERROR_RES;
import static proton.face.constant.Constants.FaceResponse.FACE_EXISTED;
import static proton.face.constant.Constants.FaceResponseCodes.DUPLICATE_REGISTER_RES;
import static proton.face.constant.Constants.FaceResponseCodes.FACE_EXISTED_RES;
import static proton.face.constant.Constants.FaceResponseCodes.REGISTER_FACE_FAIL_RES;
import static proton.face.constant.Constants.FaceResponseCodes.REGISTER_FACE_SUCCESS_RES;
import static proton.face.constant.Constants.SOURCE;


@Getter
@Setter
@Slf4j
@Named
@ViewScoped
public class RegisterFaceController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String code;
    private String fullName;
    private String mobilePhone;
    private String dateOfBirth;
    private LocalDate birthday;
    private String gender;
    private int zoneSelected;
    private int customerTypeSelected;
    private boolean flagIsLiveLess = true;
    private People peopleSelected;
    private List<Groups> zone;
    private List<CustomerType> customerTypeList;
    private List<People> peopleList;
    private String face;
    private List<List<String>> faces = new ArrayList<>();
    private UploadedFile fileUpload;
    private int tabIndex = 1;

    private final PeopleRepository peopleRepository;
    private final MinioService minioService;
    private final HttpUtils httpUtils = new HttpUtils();
    private final CustomerTypeRepository customerTypeRepository;
    private final FaceServiceImpl faceService;
    private final GroupRepository groupRepository;

    public RegisterFaceController(GroupRepository groupRepository,
                                  CustomerTypeRepository customerTypeRepository,
                                  FaceServiceImpl faceService,
                                  MinioService minioService,
                                  PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        this.minioService = minioService;
        this.customerTypeRepository = customerTypeRepository;
        this.groupRepository = groupRepository;
        zone = groupRepository.getGroupList();
        this.faceService = faceService;
        customerTypeList = customerTypeRepository.getCustomerTypeList();

    }


    public void register() {
        try {
            if (tabIndex == 0) {
                registerWithFace();
            } else if (tabIndex == 1) {
                registerWithUploadedImage();
            }
        } catch (Exception e) {
            log.error("Registration failed: ", e);
            showErrorMessage("Đăng ký lỗi");
        } finally {
            hideDialog("confirmReg");
        }
    }

    private void registerWithFace() {
        String imagePath = minioService.uploadImage(code, Base64.getDecoder().decode(faces.get(0).get(0)));
        People people = createPeople(imagePath);

        boolean result = registerPeople(people);
        if (result) {
            DataResponseFromRegisterFaceSearch response = httpUtils.callAPIToRegister(faces.get(0).get(0), code);
            if (response != null && StatusConstant.STATUS_SUCCESS.equals(response.getStatus())) {
                showInfoMessage("Đăng ký thành công");
                clear();
            } else {
                showErrorMessage("Kiểm tra khuôn mặt thất bại");
            }
        } else {
            showErrorMessage("Đăng ký thất bại");
        }
    }

    private void registerWithUploadedImage() throws Exception {
        byte[] imageBytes = IOUtils.toByteArray(fileUpload.getInputStream());
        String fileName = minioService.uploadImage(code + ".jpg", imageBytes);

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        if (!isFaceValid(base64Image)) return;

        People person = createPeople(fileName);
        RegisterFaceRequest registerRequest = createRegisterFaceRequest(base64Image);
        String response = faceService.registerFace(registerRequest);

        if (REGISTER_FACE_SUCCESS_RES.equals(response)) {
            boolean result = peopleRepository.registerPeople(person) && peopleRepository.registerPeopleRegImage(code, fileName);
            showInfoMessage(result ? "Đăng ký thành công" : "Đăng ký thất bại");
        } else {
            handleRegistrationError(response);
        }
    }

    private People createPeople(String imagePath) {
        return People.builder()
                .peopleId(code)
                .fullName(fullName)
                .dateOfBirth(birthday != null ? birthday.toString() : "")
                .groupId(zoneSelected)
                .imagePath(imagePath)
                .mobilePhone(mobilePhone)
                .gender(gender)
                .customerTypeId(customerTypeSelected)
                .build();
    }

    private boolean registerPeople(People people) {
        boolean result = peopleRepository.registerPeople(people);
        if (!flagIsLiveLess) {
            result &= peopleRepository.registerPeopleRegImage(code, faces);
            result &= peopleRepository.registerPeopleWhitelist(code, Objects.requireNonNull(LoginController.getSessionUser()).getId());
        } else {
            result &= peopleRepository.registerPeopleRegImage(code, faces);
        }
        return result;
    }

    private boolean isFaceValid(String base64Image) {
        SearchFaceRequest searchRequest = SearchFaceRequest.builder().image(base64Image).source(SOURCE).build();
        String response = faceService.searchFace(searchRequest);

        if (INPUT_ERROR_RES.equals(response)) {
            showErrorMessage(INPUT_ERROR);
            return false;
        }
        if (FACE_EXISTED_RES.equals(response)) {
            showErrorMessage(FACE_EXISTED);
            return false;
        }
        return true;
    }

    private RegisterFaceRequest createRegisterFaceRequest(String base64Image) {
        return RegisterFaceRequest.builder()
                .createdAt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())))
                .isLiveCheck(false)
                .source(SOURCE)
                .metaData("")
                .peopleId(code)
                .image(base64Image)
                .build();
    }

    private void handleRegistrationError(String response) {
        switch (response) {
            case INPUT_ERROR_RES -> showErrorMessage(INPUT_ERROR);
            case DUPLICATE_REGISTER_RES -> showErrorMessage(FACE_EXISTED);
            case REGISTER_FACE_FAIL_RES -> showErrorMessage(REGISTER_FACE_FAIL);
            default -> showErrorMessage("Đăng ký thất bại");
        }
    }


    public void validate() {
        if (!isValidInput()) {
            return;
        }

        if (peopleRepository.isExistPeople(code)) {
            peopleRepository.updateExistPeople(code);
            showErrorMessage("Mã nhân viên đã được đăng ký");
            return;
        }

        if (tabIndex == 1) {
            if (fileUpload == null) {
                showWarnMessage("Vui lòng tải ảnh lên");
                return;
            }
            register();
        } else if (tabIndex == 0) {
            if (!isFaceCaptured()) return;

            if (!flagIsLiveLess) {
                showDialog("confirmReg");
            } else {
                register();
            }
        }
    }

    private boolean isValidInput() {
        if (StringUtils.isBlank(code)) {
            showWarnMessage("Trường 'Mã nhân viên' bắt buộc phải nhập");
            return false;
        }
        if (StringUtils.isBlank(fullName)) {
            showWarnMessage("Trường 'Họ và tên' bắt buộc phải nhập");
            return false;
        }
        if (StringUtils.isBlank(mobilePhone)) {
            showWarnMessage("Trường 'Số điện thoại' bắt buộc phải nhập");
            return false;
        }
        if (zoneSelected == 0) {
            showWarnMessage("Vui lòng chọn khu vực");
            return false;
        }
        if (StringUtils.isBlank(gender)) {
            showWarnMessage("Vui lòng chọn giới tính");
            return false;
        }
        return true;
    }

    private boolean isFaceCaptured() {
        if (faces == null || faces.isEmpty()) {
            showWarnMessage("Vui lòng chụp ảnh khuôn mặt. Được phép chụp tối đa 5 khuôn mặt");
            return false;
        }

        SearchFaceRequest searchFaceRequest = new SearchFaceRequest();
        searchFaceRequest.setSource(SOURCE);

        for (List<String> list : faces) {
            for (String imageBase64 : list) {
                searchFaceRequest.setImage(imageBase64);
                if (FACE_EXISTED_RES.equals(faceService.searchFace(searchFaceRequest))) {
                    return false;
                }
            }
            if (!flagIsLiveLess) break;
        }
        return true;
    }


    //TODO
    public void addFace() {
        if (StringUtils.isNotBlank(face) && faces.size() < 5) {
            faces.add(Arrays.asList(StringUtils.split(face, ";")));
        }

    }

    public void onRowSelect() {
        code = peopleSelected.getPeopleId();
        fullName = peopleSelected.getFullName();
        mobilePhone = peopleSelected.getMobilePhone();
        dateOfBirth = peopleSelected.getDateOfBirth();
        gender = peopleSelected.getGender();
        zoneSelected = peopleSelected.getGroupId();
        customerTypeSelected = peopleSelected.getCustomerTypeId();
    }

    public void getPeopleListByPeopleId() {
        if (StringUtils.isEmpty(code)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Cảnh báo", "Vui lòng nhập mã nhân viên"));
            return;
        }
        peopleList = peopleRepository.searchPeopleList(code, null, null);
        showDialog("peoplePicker");
    }

    public void getPeopleListByFullName() {
        if (StringUtils.isEmpty(fullName)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Cảnh báo", "Vui lòng nhập mã tên"));
            return;
        }
        peopleList = peopleRepository.searchPeopleList(null, fullName, null);
        showDialog("peoplePicker");
    }

    public void getPeopleListByMobilePhone() {
        if (StringUtils.isEmpty(mobilePhone)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Cảnh báo", "Vui lòng nhập số điện thoại"));
            return;
        }
        peopleList = peopleRepository.searchPeopleList(null, null, mobilePhone);
        showDialog("peoplePicker");
    }

    public void test() {
        System.out.println("test");
    }


    public void clear() {
        code = null;
        fullName = null;
        mobilePhone = null;
        dateOfBirth = null;
        birthday = null;
        gender = null;
        zoneSelected = 0;
        customerTypeSelected = 0;
        peopleSelected = null;
        face = null;
        faces = null;
    }

    public void removeFace(String index) {
        faces.remove(Integer.valueOf(index).intValue());
    }

    public void showInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Thông báo: ", message));
    }

    public void showWarnMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cảnh báo", message));
    }

    public void showErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi: ", message));
    }

    public void showDialog(String id) {
        PrimeFaces.current().executeScript("PF('" + id + "').show();");
    }

    public void hideDialog(String id) {
        PrimeFaces.current().executeScript("PF('" + id + "').hide();");
    }

    public void onTabChange(TabChangeEvent event) {
        tabIndex = ((TabView) event.getSource()).getIndex();
    }


}
