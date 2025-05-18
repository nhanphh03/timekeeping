package nhanph.timekeeping.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.Date;

@Slf4j
@ManagedBean
@RequestScoped
@Getter
@Setter
@Builder
@Entity
@Table(name = "customer_group")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private Integer status;

    @Column(name = "created_time")
    private Date createdTime;
}
