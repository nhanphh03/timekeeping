package proton.face.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Setter
@Getter
@ManagedBean
@SessionScoped
@Data
public class Role {
	private int id;
	private String name;
}
