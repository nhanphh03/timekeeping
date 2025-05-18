package nhanph.timekeeping.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import nhanph.timekeeping.admin.entity.Detection;

/**
 * @Package: proton.face.repository
 * @author: nhanph
 * @date: 3/4/2025 2025
 * @Copyright: @nhanph
 */

@Repository
public interface DetectionRepository extends JpaRepository<Detection, Long> {
}
