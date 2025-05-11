package nhanph.timekeeping.processor.service.impl;

import lombok.RequiredArgsConstructor;
import nhanph.timekeeping.processor.entity.CapturedImages;
import nhanph.timekeeping.processor.repository.CapturedImagesRepository;
import nhanph.timekeeping.processor.service.CapturedImagesService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CapturedImagesServiceImpl implements CapturedImagesService {
    private final CapturedImagesRepository capturedImagesRepository;

    @Override
    public void saveCapturedImage(CapturedImages capturedImages) {
        capturedImagesRepository.save(capturedImages);
    }
}
