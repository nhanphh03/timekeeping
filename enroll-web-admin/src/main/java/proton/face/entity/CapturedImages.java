package proton.face.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ManagedBean
@RequestScoped
@Data
@Log4j2
public class CapturedImages implements Serializable {

	private int id;
	private String peopleId;
	private String capturedImagePath;
	private String fullName;
	private String gender;
	private String dateOfBirth;
	private LocalDate birthday;
	private int responseTime;
	private String recognizationStatus;
	private String responseRaw;
	private String customerType;
	private String createdTime;
	private int cameraId;
	private String cameraName;
	private String groupName;
	private String mobilePhone;
	private int status;
	private int groupId;
	private int customerTypeId;
	private String livenessStatus;
	private String fullNameMark;

	public String getFullNameMarkV2() {
		if (fullName != null && "ERROR".equals(livenessStatus)) {
			return fullName + " *";
		}
		return fullName;
	}

	public void setDateOfBirthV2(String dateOfBirth) {
		try {
			birthday = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_DATE);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		this.dateOfBirth = dateOfBirth;
	}

}
