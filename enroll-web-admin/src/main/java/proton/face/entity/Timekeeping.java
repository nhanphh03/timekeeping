package proton.face.entity;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
@Getter
@Setter
public class Timekeeping {

	private String peopleId;
	private String fullName;
	private String checkIn;
	private String checkOut;
	private String imagePath;
	private String dateTimeKeeping;
	private String totalWork;
	private String groupName;
	private String noonTime;
	private String description;
	private String morningLate;
	private String earlyLeave;
	public void setMorningLateV2(String morningLate) {
		this.morningLate = morningLate;
	}
}
