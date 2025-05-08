package com.proton.face_enroll.dto.searchface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Package: proton.face.dto.searchface
 * @author: nhanph
 * @date: 3/3/2025 2025
 * @Copyright: @nhanph
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFaceResponsePy {

    private String status;
    private String message;
    private List<SearchFaceObject> data;
}
