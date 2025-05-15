package proton.face.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;
import proton.face.constant.RoleConstant;
import proton.face.entity.CustomerType;
import proton.face.entity.Groups;
import proton.face.entity.People;
import proton.face.repository.CustomerTypeRepository;
import proton.face.repository.GroupRepository;
import proton.face.repository.PeopleRepository;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author: nhanph.proton
 * @date: 19-11-2024
 */

@Named
@ViewScoped
@Slf4j
@Getter
@Setter
public class PeopleController implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate dateOfBirthSelected;
    private String peopleIdSearch;
    private String fullNameSearch;
    private String mobilePhoneSearch;
    private int zoneSelected;
    private int customerTypeSelected;
    private List<Groups> zone;
    private List<CustomerType> customerTypeList;
    private List<People> peopleList;
    private List<String> imageList;
    private People people;
    private UploadedFile imageFile;
    private String originalFaceImage;
    private final PeopleRepository peopleRepository;
    private final CustomerTypeRepository customerTypeRepository;

    public PeopleController(PeopleRepository peopleRepository, GroupRepository groupRepository,
                            CustomerTypeRepository customerTypeRepository) {
        this.peopleRepository = peopleRepository;
        this.customerTypeRepository = customerTypeRepository;
        peopleList = peopleRepository.getListPeople();
        zone = groupRepository.getGroupList();
        customerTypeList = customerTypeRepository.getCustomerTypeList();
    }

    public String getPeople(People people) {
        if (RoleConstant.ADMIN.equals(LoginController.isUserInRoles())) {
            imageList = peopleRepository.getLast5Detection(people.getPeopleId());
            zoneSelected = people.getGroupId();
            customerTypeSelected = people.getCustomerTypeId();
            if (people.getDateOfBirth() != null) {
                dateOfBirthSelected = LocalDate.parse(people.getDateOfBirth());
            } else {
                dateOfBirthSelected = null;
            }
            this.people = people;
            this.originalFaceImage = people.getImagePath();
            return "updatePeople";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Không có quyền truy cập", ""));
            return "";
        }
    }

    public void searchPeople() {
        peopleList = peopleRepository.searchPeopleList(peopleIdSearch, fullNameSearch, mobilePhoneSearch);
    }

    public String updatePeople() {
        if (zoneSelected == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn khu vực", ""));
            return "updatePeople";
        }
        if (customerTypeSelected == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn phân loại", ""));
            return "updatePeople";
        }
        if (dateOfBirthSelected == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn ngày sinh", ""));
            return "updatePeople";
        }
        if (people.getFullName() == null || people.getFullName().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập họ tên", ""));
            return "updatePeople";
        }
        if (people.getMobilePhone() == null || people.getMobilePhone().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập số điện thoại", ""));
            return "updatePeople";
        }
        if (!people.getMobilePhone().trim().startsWith("0") || people.getMobilePhone().trim().length() != 10 || !people.getMobilePhone().trim().matches("[0-9]+")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Số điện thoại không hợp lệ", ""));
            return "updatePeople";
        }
        if (people.getGender() == null || people.getGender().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chọn giới tính", ""));
            return "updatePeople";
        }
        people.setCustomerTypeId(customerTypeSelected);
        people.setGroupId(zoneSelected);
        people.setDateOfBirth(dateOfBirthSelected.toString());
        try {
            boolean regFace = people.getImagePath() != null && !people.getImagePath().equals(originalFaceImage);

            if (regFace) {
                log.info("[PeopleID={}] Updating face. Old={}. New={}", people.getPeopleId(), originalFaceImage, people.getImagePath());
            }
            peopleRepository.updatePeople(people, regFace);
            originalFaceImage = people.getImagePath();
            peopleList = peopleRepository.getListPeople();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cập nhật thông tin nhân viên thành công", ""));
            return "listPeople.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return "";
    }

    public void onRowSelect(SelectEvent<String> event) {
        people.setImagePath(event.getObject());
    }

    public People getPeople() {
        if (this.people == null)
            this.people = new People();
        return people;
    }

}
