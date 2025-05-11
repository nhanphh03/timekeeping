package nhanph.timekeeping.processor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_code")
    private String customerCode;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "gender")
    private String gender;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "status")
    private Integer status;
    @Column(name = "mobile_phone")
    private String mobilePhone;
}
