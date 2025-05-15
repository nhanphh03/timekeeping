package proton.face.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@ManagedBean
@RequestScoped
@Getter
@Setter
@Builder
@Entity
@Table(name = "detection")
@NoArgsConstructor
@AllArgsConstructor
public class Detection implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "people_id")
	private String peopleId;

	@Column(name = "response_time")
	private Integer responseTime;

	@Column(name = "captured_image_path")
	private String capturedImagePath;

	@Column(name = "recognization_status")
	private String recognitionStatus;

	@Column(name = "response_raw")
	private String responseRaw;

	@Column(name = "captured_time")
	private Timestamp capturedTime;

	@Transient
	private String fullName;

	@Transient
	private String gender;

	@Transient
	private String dateOfBirth;

	@Transient
	private LocalDate birthday;

	@Column(name = "created_time")
	private String createdTime;

	@Transient
	private String customerType;

	@Column(name = "camera_id")
	private int cameraId;

	@Transient
	private String cameraName;

	@Transient
	private String groupName;

	@Transient
	private String mobilePhone;

	@Transient
	private int customerTypeId;

	@Transient
	private int groupId;

	@Column(name = "liveness_status")
	private String livenessStatus;

	@Column(name = "day_first_time")
	private String dayFirstTime;

	@Column(name = "day_noon_time")
	private String dayNoonTime;

	public String getFullNameMarkV2() {
		if (fullName != null && "ERROR".equals(livenessStatus)) {
			return fullName + " *";
		}
		return fullName;
	}

	public void setDateOfBirthV2(String dateOfBirth) {
		try {
			birthday = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_DATE);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		this.dateOfBirth = dateOfBirth;
	}

}
