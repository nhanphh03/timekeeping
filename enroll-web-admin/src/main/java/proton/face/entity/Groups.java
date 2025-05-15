package proton.face.entity;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@SessionScoped
@ManagedBean
@Getter
@Setter
public class Groups implements Serializable {
	private int groupId;
	private String groupCode;
	private String groupName;
}
