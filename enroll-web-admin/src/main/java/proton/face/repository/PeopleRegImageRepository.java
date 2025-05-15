package proton.face.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proton.face.entity.PeopleRegImage;

/**
 * @Package: proton.face.repository
 * @author: nhanph
 * @date: 3/4/2025 2025
 * @Copyright: @nhanph
 */

@Repository
public interface PeopleRegImageRepository extends JpaRepository<PeopleRegImage, Long> {

    @Modifying
    @Query(value = "update capturedimages set people_id = :people_id where id = :id", nativeQuery = true)
    void updateCapturedImagesById(@Param("people_id") String peopleId, @Param("id") long id);
}
