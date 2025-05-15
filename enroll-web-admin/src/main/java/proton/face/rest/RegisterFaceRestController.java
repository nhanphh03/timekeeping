package proton.face.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proton.face.entity.People;

import java.util.List;

@RestController
@RequestMapping("/v1/face")
@Slf4j
@RequiredArgsConstructor
public class RegisterFaceRestController {

    private final RegisterFaceService registerFaceService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFace(@RequestBody People people,
                                          @RequestBody List<List<String>> faces,
                                          @RequestBody MultipartFile fileUpload,
                                          @RequestBody int tabIndex) throws Exception {
        return ResponseEntity.ok(registerFaceService.registerFace(people, faces, fileUpload, tabIndex));
    }
}
