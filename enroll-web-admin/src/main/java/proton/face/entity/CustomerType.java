package proton.face.entity;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@Setter
@Getter
@SessionScoped
@ManagedBean
public class CustomerType implements Serializable{

	private int customerTypeId;

	private String customerTypeName;

}
