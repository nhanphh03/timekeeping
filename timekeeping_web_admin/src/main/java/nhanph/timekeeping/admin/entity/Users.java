
package nhanph.timekeeping.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Date;

@Setter
@Getter
@ManagedBean
@SessionScoped
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "created_time")
	private Date createdTime;
	@Column(name = "status")
	private Integer status;
}

