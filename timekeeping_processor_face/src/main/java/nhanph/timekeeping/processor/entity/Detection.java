package nhanph.timekeeping.processor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Detection {

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