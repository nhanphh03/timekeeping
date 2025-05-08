package nhanph.timekeeping.processor.repository;

import nhanph.timekeeping.processor.entity.Detection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectionRepository extends JpaRepository<Detection, Long> {

}
