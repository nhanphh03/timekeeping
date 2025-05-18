package nhanph.timekeeping.admin.repository;


import nhanph.timekeeping.admin.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
}
