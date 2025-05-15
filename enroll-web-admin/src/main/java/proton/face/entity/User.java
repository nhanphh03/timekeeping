
package proton.face.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@Setter
@Getter
@ManagedBean
@SessionScoped
@Data
@Slf4j
public class User implements Serializable {
	private int id;
	private String password;
	private String username;
	private Role role;


}
