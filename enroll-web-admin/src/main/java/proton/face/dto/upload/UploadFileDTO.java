package proton.face.dto.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @Package: proton.face.dto.searchface
 * @author: nhanph
 * @date: 4/5/2025 2025
 * @Copyright: @nhanph
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFileDTO {
    @JsonProperty("base64_img")
    private String base64Img;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("file_name")
    private String fileName;
}
