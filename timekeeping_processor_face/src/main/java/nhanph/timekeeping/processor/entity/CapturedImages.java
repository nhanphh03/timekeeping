package nhanph.timekeeping.processor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "captured_images")
public class CapturedImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "path_image")
    private String imagePath;
    @Column(name = "camera_code")
    private String cameraCode;
    @Column(name = "customer_code")
    private String customerCode;
    @Column(name = "detected_status")
    private String detectedStatus;
    @Column(name = "response_raw")
    private String responseRaw;
    @Column(name = "captured_time")
    private String capturedTime;
    @Column(name = "response_time")
    private String responseTime;
    @Column(name = "search_id")
    private String searchId;
}
