package nhanph.timekeeping.admin.repository;

import nhanph.timekeeping.admin.entity.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerGroupRepository extends JpaRepository<CustomerGroup, Long> {
}
