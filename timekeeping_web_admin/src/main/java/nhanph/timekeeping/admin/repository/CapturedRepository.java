package nhanph.timekeeping.admin.repository;

import nhanph.timekeeping.admin.entity.CapturedImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Package: proton.face.repository
 * @author: nhanph
 * @date: 3/4/2025 2025
 * @Copyright: @nhanph
 */

@Repository
public interface CapturedRepository extends JpaRepository<CapturedImages, Long> {
}
