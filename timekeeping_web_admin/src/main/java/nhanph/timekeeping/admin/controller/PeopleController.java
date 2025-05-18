package nhanph.timekeeping.admin.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nhanph.timekeeping.admin.entity.Customer;
import nhanph.timekeeping.admin.entity.CustomerGroup;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;
import nhanph.timekeeping.admin.constant.RoleConstant;
import nhanph.timekeeping.admin.entity.CustomerType;
import nhanph.timekeeping.admin.repository.CustomerTypeRepository;
import nhanph.timekeeping.admin.repository.CustomerGroupRepository;
import nhanph.timekeeping.admin.repository.CustomerRepository;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
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
    private List<CustomerGroup> zone;
    private List<CustomerType> customerTypeList;
    private List<Customer> peopleList;
    private List<String> imageList;
    private Customer people;
    private UploadedFile imageFile;
    private String originalFaceImage;
    private final CustomerRepository customerRepository;
    private final CustomerTypeRepository customerTypeRepository;

    public PeopleController(CustomerRepository customerRepository, CustomerGroupRepository customerGroupRepository,
                            CustomerTypeRepository customerTypeRepository) {
        this.customerRepository = customerRepository;
        this.customerTypeRepository = customerTypeRepository;
        peopleList = customerRepository.findAll();
        zone = customerGroupRepository.findAll();
        customerTypeList = customerTypeRepository.findAll();
    }

    public String getPeople(Customer customer) {
        imageList = customerRepository.findListLast5Detection(customer.getCustomerCode());
        zoneSelected = customer.getGroupId();
        customerTypeSelected = customer.getCustomerType();
        if (customer.getDateOfBirth() != null) {
            dateOfBirthSelected = LocalDate.parse((CharSequence) customer.getDateOfBirth());
        } else {
            dateOfBirthSelected = null;
        }
        this.people = customer;
        this.originalFaceImage = customer.getImagePath();
        return "updatePeople";
    }

    public void searchPeople() {
//        peopleList = customerRepository.searchPeopleList(peopleIdSearch, fullNameSearch, mobilePhoneSearch);
        peopleList = customerRepository.findAll();
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
        people.setCustomerType(customerTypeSelected);
        people.setGroupId(zoneSelected);
        people.setDateOfBirth(Date.valueOf(dateOfBirthSelected.toString()));
        try {
//            boolean regFace = people.getImagePath() != null && !people.getImagePath().equals(originalFaceImage);
//
//            if (regFace) {
//                log.info("[PeopleID={}] Updating face. Old={}. New={}", people.getCustomerCode(),
//                        originalFaceImage, people.getImagePath());
//            }
//            customerRepository.updatePeople(people, regFace);
            originalFaceImage = people.getImagePath();
            peopleList = customerRepository.findAll();
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

    public Customer getPeople() {
        if (this.people == null)
            this.people = new Customer();
        return people;
    }

}
