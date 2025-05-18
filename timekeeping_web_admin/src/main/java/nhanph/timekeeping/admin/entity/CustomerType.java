package nhanph.timekeeping.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.Date;

@Setter
@Getter
@SessionScoped
@ManagedBean
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_type")
public class CustomerType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "code")
	private String code;
	@Column(name = "created_time")
	private Date createdTime;
	@Column(name = "status")
	private Integer status;
}
