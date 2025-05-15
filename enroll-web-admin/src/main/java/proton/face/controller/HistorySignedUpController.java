package proton.face.controller;

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
import proton.face.constant.RoleConstant;
import proton.face.entity.CustomerType;
import proton.face.entity.Detection;
import proton.face.entity.Groups;
import proton.face.entity.People;
import proton.face.repository.CustomerTypeRepository;
import proton.face.repository.GroupRepository;
import proton.face.repository.HistoryPeopleSignedUpRepository;
import proton.face.util.StringUtils;
import proton.face.util.Utils;

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
    private final GroupRepository groupRepository;
    private final LoginController loginController;
    private final CustomerTypeRepository customerTypeRepository;


    public HistorySignedUpController(HistoryPeopleSignedUpRepository historyPeopleSignedUpRepository,
                                     LoginController loginController, GroupRepository groupRepository
            , CustomerTypeRepository customerTypeRepository) {

        this.historyPeopleSignedUpRepository = historyPeopleSignedUpRepository;
        this.loginController = loginController;
        this.customerTypeRepository = customerTypeRepository;
        this.groupRepository = groupRepository;
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
            this.zone = groupRepository.getGroupList();
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
