package nhanph.timekeeping.processor.repository;

import nhanph.timekeeping.processor.entity.CapturedImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapturedImagesRepository extends JpaRepository<CapturedImages, Long> {
}
