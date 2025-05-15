package proton.face.controller;

import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;
import proton.face.constant.Constants;
import proton.face.constant.RoleConstant;
import proton.face.constant.StatusConstant;
import proton.face.entity.CustomerType;
import proton.face.entity.Groups;
import proton.face.entity.People;
import proton.face.repository.CustomerTypeRepository;
import proton.face.repository.GroupRepository;
import proton.face.repository.PeopleRepository;
import proton.face.dto.response.DataResponseFromRegisterFaceSearch;
import proton.face.util.HttpUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ManagedBean
@RequestScoped
@Slf4j
public class PeopleFile {
	private int id;
	private String peopleId;
	private String fullName;
	private String dateOfBirth;
	private String gender;
	private String customerType;
	private String lastCheckinTime;
	private String mobilePhone;
	private int zoneSelected;
	private int customerTypeSelected;
	private String peopleSelected;
	private GroupRepository groupRepository;
	private PeopleRepository peopleRepository;
	private CustomerTypeRepository customerTypeRepository;
	private List<Groups> zone;
	private List<CustomerType> customerTypeList;
	private List<People> peopleList;

	private UploadedFile imagePath;

	HttpUtils httpUtils = new HttpUtils();

	public PeopleFile() {
		zone = groupRepository.getGroupList();
		customerTypeList = customerTypeRepository.getCustomerTypeList();
		peopleList = peopleRepository.getPeopleList();
	}

	public void upload(PeopleFile peopleFile, People people) {

		if (RoleConstant.ADMIN.equals(LoginController.isUserInRoles())) {
			if (peopleRepository.isExistPeople(peopleSelected)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Thất bại", "Mã nhân viên đã được đăng ký"));
			}

			try {
				DataResponseFromRegisterFaceSearch dataResponse =
						httpUtils.callAPIToCreateHaveFile(peopleFile.getImagePath(), peopleFile.getPeopleId());
				if (dataResponse.getStatus().equals(StatusConstant.STATUS_SUCCESS)) {

					String filePath = getUploadFilePath(imagePath.getFileName());
					copyFile(filePath, imagePath);
					Path path = Paths.get(filePath);
					if (!Files.exists(path)) {
						Files.createFile(path);
					}
					log.info("file {}" , imagePath.getFileName());
					people.setCustomerType(peopleFile.getCustomerType());
					people.setDateOfBirth(peopleFile.getDateOfBirth());
					people.setFullName(peopleFile.getFullName());
					people.setGender(peopleFile.getGender());
					people.setImagePath(filePath);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Thành công", "Đã thêm" + people.getFullName()));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Thất bại", "Ảnh không hợp lệ. Vui lòng chọn ảnh khác"));
				}
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Thất bại", "Trùng mã cá nhân"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Không có quyền truy cập", ""));
		}
	}

	public void copyFile(String filePath, UploadedFile uploadedFile) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = uploadedFile.getInputStream();
			File file = new File(filePath);
			log.info(file.getAbsolutePath());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			log.info(file.getAbsolutePath());
			out = Files.newOutputStream(file.toPath());
			int read = 0;
			byte[] bytes = new byte[1024 * 1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	}

	public String getUploadFilePath(String fileName) {
		try {
            return Constants.IMAGE_PATH + "face" + fileName;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return "";
		}

	}

	public void onItemSelectedListener(SelectEvent<String> event){
	    String selectedItem = event.getObject();
	    People itemP = peopleList.stream()
	    		  .filter(item -> selectedItem.equals(item.getPeopleId()))
	    		  .findAny()
	    		  .orElse(null);

        if (itemP != null) {
            zoneSelected = itemP.getGroupId();
			fullName = itemP.getFullName();
			mobilePhone = itemP.getMobilePhone();
			dateOfBirth = itemP.getDateOfBirth();
			gender = itemP.getGender();
			customerTypeSelected = itemP.getCustomerTypeId();
        }
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getLastCheckinTime() {
		return lastCheckinTime;
	}

	public void setLastCheckinTime(String lastCheckinTime) {
		this.lastCheckinTime = lastCheckinTime;
	}

	public UploadedFile getImagePath() {
		return imagePath;
	}

	public void setImagePath(UploadedFile imagePath) {
		this.imagePath = imagePath;
	}

	public List<Groups> getZone() {
		return zone;
	}

	public void setZone(List<Groups> zone) {
		this.zone = zone;
	}

	public List<CustomerType> getCustomerTypeList() {
		return customerTypeList;
	}

	public void setCustomerTypeList(List<CustomerType> customerTypeList) {
		this.customerTypeList = customerTypeList;
	}

	public int getZoneSelected() {
		return zoneSelected;
	}

	public void setZoneSelected(int zoneSelected) {
		this.zoneSelected = zoneSelected;
	}

	public int getCustomerTypeSelected() {
		return customerTypeSelected;
	}

	public void setCustomerTypeSelected(int customerTypeSelected) {
		this.customerTypeSelected = customerTypeSelected;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPeopleSelected() {
		return peopleSelected;
	}

	public void setPeopleSelected(String peopleSelected) {
		this.peopleSelected = peopleSelected;
	}

	public List<People> getPeopleList() {
		return peopleList;
	}

	public void setPeopleList(List<People> peopleList) {
		this.peopleList = peopleList;
	}

}
