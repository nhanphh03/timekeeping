package proton.face.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;

@SessionScoped
@ManagedBean
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "people")
public class People implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "people_id")
	private String peopleId;

	@Column(name = "full_name")
	private String fullName;

	@Transient
	private String dateOfBirth;

	@Column(name = "date_of_birth")
	private LocalDate birthday;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "Last_checkin_time")
	private String lastCheckinTime;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "group_id")
	private int groupId;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "avatar")
	private String avatar;

	@Transient
	private String groupName;

	@Transient
	private int customerTypeId;

	@Column(name = "status")
	private int status;

	public String getImagePathNoHostV2() {
		return imagePath.replace("http://192.168.1.19:8181/api/v1/file", "");
	}


}
