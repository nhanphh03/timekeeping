package com.lottefn.collateral.domain.repositories;

import com.lottefn.collateral.app.requests.user.UserFilterDTO;
import com.lottefn.collateral.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User getById(Long id);

    @Query("select p from User p where p.username like %:#{#filterDTO.username}% order by p.createdAt desc")
    Page<User> findAll(UserFilterDTO filterDTO, Pageable pageable);

    @Query("SELECT DISTINCT u.username FROM User u")
    List<String> findAllUsernames();

    @Query("SELECT DISTINCT u.email FROM User u")
    List<String> findAllEmail();
}
