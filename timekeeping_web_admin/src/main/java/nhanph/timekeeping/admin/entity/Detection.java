package nhanph.timekeeping.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.Date;

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
	@Column(name = "image_path")
	private String imagePath;
	@Column(name = "camera_code")
	private String cameraCode;
	@Column(name = "customer_code")
	private String customerCode;
	@Column(name = "recognition_status")
	private String recognitionStatus;
	@Column(name = "response_raw")
	private String responseRaw;
	@Column(name = "created_time")
	private Date createdTime;
	@Column(name = "captured_time")
	private String capturedTime;
	@Column(name = "first_time_check_in")
	private String firstTimeCheckIn;
	@Column(name = "last_time_check_in")
	private String lastTimeCheckIn;
	@Column(name = "search_id")
	private String searchId;
	@Column(name = "score")
	private Double score;

}
