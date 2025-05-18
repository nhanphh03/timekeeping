package nhanph.timekeeping.admin.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import nhanph.timekeeping.admin.constant.RoleConstant;
import nhanph.timekeeping.admin.entity.CustomerType;
import nhanph.timekeeping.admin.entity.Detection;
import nhanph.timekeeping.admin.repository.CustomerTypeRepository;
import nhanph.timekeeping.admin.repository.CustomerGroupRepository;
import nhanph.timekeeping.admin.util.StringUtils;
import nhanph.timekeeping.admin.util.Utils;

@Named
@ViewScoped
@Getter
@Setter
public class HistorySignedUpController implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(HistorySignedUpController.class);
    private int zoneSelected;
    private int customerTypeSelected;
    private List<Groups> zone;
    private List<CustomerType> customerTypeList;
    private Detection detection;
    private List<Detection> detectionList;
    private String peopleIdSearch;
    private String statusSearch;
    private Date fromDate;
    private Date toDate;
    private LocalDate selectedDate;
    private final HistoryPeopleSignedUpRepository historyPeopleSignedUpRepository;
    private final CustomerGroupRepository customerGroupRepository;
    private final LoginController loginController;
    private final CustomerTypeRepository customerTypeRepository;


    public HistorySignedUpController(HistoryPeopleSignedUpRepository historyPeopleSignedUpRepository,
                                     LoginController loginController, CustomerGroupRepository customerGroupRepository
            , CustomerTypeRepository customerTypeRepository) {

        this.historyPeopleSignedUpRepository = historyPeopleSignedUpRepository;
        this.loginController = loginController;
        this.customerTypeRepository = customerTypeRepository;
        this.customerGroupRepository = customerGroupRepository;
        this.detectionList = historyPeopleSignedUpRepository.getPeopleSignedUp(null, null, null, null);
    }

    public void searchDetectionList() {
        String strFromDate = null;
        String strToDate = null;

        try {
            strFromDate = Utils.convertDateToString(this.fromDate, "yyyy-MM-dd");
            strToDate = Utils.convertDateToString(this.toDate, "yyyy-MM-dd");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        this.detectionList = historyPeopleSignedUpRepository.getPeopleSignedUp(this.peopleIdSearch,
                this.statusSearch, strFromDate, strToDate);
    }

    public String getPeopleSignedUp(Integer id) {
        if (RoleConstant.ADMIN.equals(LoginController.isUserInRoles())) {
            this.detection = historyPeopleSignedUpRepository.getPeopleSignedUpById(id);
            selectedDate = LocalDate.parse(this.detection.getDateOfBirth());
            this.zone = customerGroupRepository.getGroupList();
            this.customerTypeList = customerTypeRepository.getCustomerTypeList();
            this.zoneSelected = detection.getGroupId();
            this.customerTypeSelected = detection.getCustomerTypeId();
            return "updatePeopleSignedUp";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Không có quyền truy cập", ""));
            return "";
        }
    }

    public String updatePeople() throws Exception {
        if (validate()) {
            try {
                People people = new People();
                people.setImagePath(this.detection.getCapturedImagePath());
                people.setCustomerType(this.detection.getCustomerType());
                people.setBirthday(this.selectedDate);
                people.setFullName(this.detection.getFullName());
                people.setGender(this.detection.getGender());
                people.setPeopleId(this.detection.getPeopleId());
                people.setMobilePhone(this.detection.getMobilePhone());
                people.setCustomerTypeId(this.customerTypeSelected);
                people.setGroupId(this.zoneSelected);
                historyPeopleSignedUpRepository.updatePeople(people, this.detection);
                this.detectionList = historyPeopleSignedUpRepository.getPeopleSignedUp(null, null, null, null);
                return "historySignedUp?redirect=true";
            } catch (Exception var3) {
                log.error("Cập nhất thất bại");
                log.error(var3.getMessage(), var3);
                return "";
            }
        }
        return "";
    }

    public Detection getDetection() {
        if (this.detection == null) {
            this.detection = new Detection();
        }
        return this.detection;
    }

    private boolean validate() {
        if (StringUtils.isEmpty(this.detection.getPeopleId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập mã", ""));
            return false;
        }
        if (StringUtils.isEmpty(this.detection.getFullName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập họ tên", ""));
            return false;
        }
        if (StringUtils.isEmpty(this.detection.getMobilePhone())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập số điện thoại", ""));
            return false;
        }
        if (!detection.getMobilePhone().trim().startsWith("0") || detection.getMobilePhone().trim().length() != 10 || !detection.getMobilePhone().matches("[0-9]+")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Số điện thoại không hợp lệ", ""));
            return false;
        }
        if (StringUtils.isEmpty(this.detection.getGender())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn giới tính", ""));
            return false;
        }
        if (this.selectedDate == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn ngày sinh", ""));
            return false;
        }
        if (customerTypeSelected == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn phân loại", ""));
            return false;
        }
        if (zoneSelected == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn khu vực", ""));
            return false;
        }
        return true;
    }
}
