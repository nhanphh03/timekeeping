package nhanph.timekeeping.processor.dto.minIO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDTO {
    @JsonProperty("base64_img")
    private String base64Img;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("file_name")
    @Nullable
    private String fileName;
}
