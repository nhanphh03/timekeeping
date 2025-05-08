package nhanph.timekeeping.processor.dto.faceRes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Package: nhanph.timekeeping.processor.dto.faceRes
 * @author: nhanph
 * @date: 05/08/2025 2025
 * @Copyright: @nhanph
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFaceResponse {

    private String status;
    private String message;
    private List<SearchFaceObject> data;
}