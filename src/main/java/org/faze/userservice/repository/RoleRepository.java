package org.faze.userservice.repository;

import java.util.Optional;
import org.faze.userservice.models.ERole;
import org.faze.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(ERole name);
}