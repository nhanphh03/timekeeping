package proton.face.dto.searchface;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: proton.face.dto.searchface
 * @author: nhanph
 * @date: 3/3/2025 2025
 * @Copyright: @nhanph
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFaceRequest {
    private String image;
    private String source;
}
