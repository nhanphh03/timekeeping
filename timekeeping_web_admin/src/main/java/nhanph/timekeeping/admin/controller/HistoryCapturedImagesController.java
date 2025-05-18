package nhanph.timekeeping.admin.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nhanph.timekeeping.admin.entity.CapturedImages;
import nhanph.timekeeping.admin.entity.CustomerType;
import nhanph.timekeeping.admin.repository.CustomerTypeRepository;
import nhanph.timekeeping.admin.repository.CustomerGroupRepository;
import nhanph.timekeeping.admin.repository.CustomerRepository;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Qualifier;
import proton.face.entity.*;
import proton.face.repository.*;
import nhanph.timekeeping.admin.dto.response.DataResponseFromRegisterFaceSearch;
import nhanph.timekeeping.admin.dto.response.DataResponseFromRemoveFacesSearch;
import nhanph.timekeeping.admin.service.impl.HistoryCapturedImagesServiceImpl;
import nhanph.timekeeping.admin.util.HttpUtils;
import nhanph.timekeeping.admin.util.StringUtils;
import nhanph.timekeeping.admin.util.Utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
@Slf4j
public class HistoryCapturedImagesController implements Serializable {

    @Qualifier("dataSourceConfig")
    private DataSource datasource;

    private static final long serialVersionUID = 1L;
    private CapturedImages capturedImages;
    private People peopleSelected;
    private LocalDate dateSelected;
    private int zoneSelected;
    private int customerTypeSelected;
    private List<Groups> zone;
    private List<CustomerType> customerTypeList;
    private List<People> peopleList;
    private boolean formUpdate = true;
    private String title;
    private List<CapturedImages> capturedImagesList;
    private String peopleIdSearch;
    private String statusSearch;
    private Date fromDate;
    private Date toDate;
    private final HistoryCapturedImagesRepository historyCapturedImagesRepository;
    private final HistoryCapturedImagesServiceImpl historyCapturedImagesServiceImpl;
    private final CustomerGroupRepository customerGroupRepository;
    private final LoginController loginController;
    private final CustomerTypeRepository customerTypeRepository;
    private final CustomerRepository customerRepository;
    HttpUtils httpUtils = new HttpUtils();


    public HistoryCapturedImagesController(HistoryCapturedImagesRepository historyCapturedImagesRepository,
                                           LoginController loginController,
                                           CustomerGroupRepository customerGroupRepository,
                                           HistoryCapturedImagesServiceImpl historyCapturedImagesServiceImpl,
                                           CustomerTypeRepository customerTypeRepository, CustomerRepository customerRepository) {
        this.historyCapturedImagesRepository = historyCapturedImagesRepository;
        this.loginController = loginController;
        this.customerRepository = customerRepository;
        this.customerTypeRepository = customerTypeRepository;
        this.historyCapturedImagesServiceImpl = historyCapturedImagesServiceImpl;
        this.customerGroupRepository = customerGroupRepository;
        this.zone = customerGroupRepository.getGroupList();
        this.customerTypeList = customerTypeRepository.getCustomerTypeList();
        this.capturedImagesList = historyCapturedImagesRepository.
                getPeopleCapturedImages(null, null, null, null);
    }

    public String getCapturedImagesById(Integer id) {
        if (!StringUtils.isEmpty(String.valueOf(id))) {
            this.capturedImages = historyCapturedImagesRepository.getCapturedImagesById(id);
            this.zoneSelected = this.capturedImages.getGroupId();
            if (this.capturedImages.getDateOfBirth() != null) {
                this.dateSelected = LocalDate.parse(this.capturedImages.getDateOfBirth());
            }else {
                this.dateSelected = null;
            }
            this.customerTypeSelected = this.capturedImages.getCustomerTypeId();
            if (this.capturedImages.getStatus() == 1) {
                this.formUpdate = true;
                this.title = "Cập nhật";
            } else {
                this.formUpdate = false;
                this.title = "Đăng ký";
                this.capturedImages.setPeopleId("");
            }
            return "createPeople";
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ko tìm đc id", ""));
            return "";
        }
    }


    public void searchCapturedImagesList() {
        String strFromDate = null;
        String strToDate = null;

        try {
            strFromDate = Utils.convertDateToString(this.fromDate, "yyyy-MM-dd");
            strToDate = Utils.convertDateToString(this.toDate, "yyyy-MM-dd");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        this.capturedImagesList = historyCapturedImagesRepository.
                getPeopleCapturedImages(this.peopleIdSearch, this.statusSearch, strFromDate, strToDate);
    }

    public String action() throws Exception {
        if (this.zoneSelected == 0) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn khu vực", ""));
            return "";
        } else {
            return this.formUpdate ? this.updatePeople() : this.savePeople();
        }
    }

    public String updatePeople() throws ParseException {
        try {
            People people = new People();
            people.setFullName(this.capturedImages.getFullName());
            people.setBirthday(this.dateSelected);
            people.setCustomerTypeId(customerTypeSelected);
            people.setGroupId(this.zoneSelected);
            people.setImagePath(this.capturedImages.getCapturedImagePath());
            people.setMobilePhone(this.capturedImages.getMobilePhone());
            people.setGender(this.capturedImages.getGender());
            people.setPeopleId(this.capturedImages.getPeopleId());
            capturedImages.setId(this.capturedImages.getId());
            historyCapturedImagesRepository.updatePeople(people, this.capturedImages);
            this.capturedImagesList = historyCapturedImagesRepository.
                    getPeopleCapturedImages(null, null, null, null);
            return "historyCapturedImages?faces-redirect=true";
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cập nhật nhân viên thất bại", ""));
            return null;
        }
    }

    public String savePeople() {
        if (customerRepository.isExistPeople(this.capturedImages.getPeopleId())) {
            log.info("Nhân viên đã được đăng ký");
            this.showDialog("confirmReg");
            return "";
        } else {
            try {

                People people = People.builder()
                        .imagePath(this.capturedImages.getCapturedImagePath())
                        .gender(this.capturedImages.getGender())
                        .birthday(this.dateSelected)
                        .customerTypeId(this.customerTypeSelected)
                        .groupId(1)
                        .customerType(this.capturedImages.getCustomerType())
                        .status(1)
                        .peopleId(this.capturedImages.getPeopleId())
                        .fullName(this.capturedImages.getFullName())
                        .mobilePhone(this.capturedImages.getMobilePhone())
                        .build();
                capturedImages.setId(this.capturedImages.getId());

                return historyCapturedImagesServiceImpl.saveNewPeople(people, this.capturedImages);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Đăng ký nhân viên thất bại", ""));
                return null;
            }
        }
    }

    public void reRegister() throws Exception {
        People people = People.builder()
                .fullName(this.capturedImages.getFullName())
                .gender(this.capturedImages.getGender())
                .dateOfBirth(this.capturedImages.getDateOfBirth())
                .customerTypeId(this.customerTypeSelected)
                .groupId(this.zoneSelected)
                .imagePath(this.capturedImages.getCapturedImagePath())
                .mobilePhone(this.capturedImages.getMobilePhone())
                .peopleId(this.capturedImages.getPeopleId())
                .build();

        Connection connection = datasource.getConnection();
        connection.setAutoCommit(false);
        boolean result = historyCapturedImagesRepository.reRegisterPeople(people, this.capturedImages);
        if (result) {
            DataResponseFromRemoveFacesSearch dataRemoveResponse = this.httpUtils.callAPIToRemove(this.capturedImages.getPeopleId());
            if (!"SUCCESS".equals(dataRemoveResponse.getStatus()) && !"PEOPLE_NOT_FOUND_ERROR".equals(dataRemoveResponse.getStatus()) && !"PEOPLE_DO_NOT_EXITS_ERROR".equals(dataRemoveResponse.getStatus())) {
                connection.rollback();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Đăng ký lại nhân viên thất bại", ""));
            } else {
                DataResponseFromRegisterFaceSearch dataResponse = this.httpUtils.callAPIToCreateV2(this.capturedImages.getCapturedImagePath(), this.capturedImages.getPeopleId());
                if ("SUCCESS".equals(dataResponse.getStatus())) {
                    connection.commit();
                    log.info("Đăng ký lại SUCCESS");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Đăng ký lại thành công nhân viên " + this.capturedImages.getFullName(), ""));
                } else {
                    connection.rollback();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Đăng ký lại nhân viên thất bại", ""));
                }
            }
        } else {
            connection.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Đăng ký lại nhân viên thất bại", ""));
        }

        this.hideDialog("confirmReg");
    }

    public void onRowSelect() {
        this.capturedImages.setPeopleId(this.peopleSelected.getPeopleId());
        this.capturedImages.setFullName(this.peopleSelected.getFullName());
        this.capturedImages.setMobilePhone(this.peopleSelected.getMobilePhone());
        this.dateSelected = LocalDate.parse(this.peopleSelected.getDateOfBirth());
        this.capturedImages.setGender(this.peopleSelected.getGender());
        this.zoneSelected = this.peopleSelected.getGroupId();
        this.customerTypeSelected = this.peopleSelected.getCustomerTypeId();
    }

    public void getPeopleListByPeopleId() {
        if (StringUtils.isEmpty(this.capturedImages.getPeopleId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập mã nhân viên", ""));
        } else {
            this.peopleList = customerRepository.searchPeopleList(this.capturedImages.getPeopleId(), null, null);
            this.showDialog("peoplePicker");
        }
    }

    public void getPeopleListByFullName() {
        if (StringUtils.isEmpty(this.capturedImages.getFullName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập số tên", ""));
        } else {
            this.peopleList = customerRepository.searchPeopleList(null, this.capturedImages.getFullName(), null);
            this.showDialog("peoplePicker");
        }
    }

    public void getPeopleListByMobilePhone() {
        if (StringUtils.isEmpty(this.capturedImages.getMobilePhone())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập số điện thoại", ""));
        } else {
            this.peopleList = customerRepository.searchPeopleList(null, null, this.capturedImages.getMobilePhone());
            this.showDialog("peoplePicker");
        }
    }

    public void showDialog(String id) {
        PrimeFaces.current().executeScript("PF('" + id + "').show();");
    }

    public void hideDialog(String id) {
        PrimeFaces.current().executeScript("PF('" + id + "').hide();");
    }

    public CapturedImages getCapturedImages() {
        if (this.capturedImages == null) {
            this.capturedImages = new CapturedImages();
        }
        return this.capturedImages;
    }

}
