package proton.face.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proton.face.entity.People;

/**
 * @Package: proton.face.repository
 * @author: nhanph
 * @date: 3/4/2025 2025
 * @Copyright: @nhanph
 */

@Repository
public interface PeopleJPARepository extends JpaRepository<People, Long> {
}
