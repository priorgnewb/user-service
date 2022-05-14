package org.faze.userservice.repository;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import org.faze.userservice.exception.ResourceNotFoundException;
import org.faze.userservice.models.User;
import org.faze.userservice.security.services.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(@NotBlank String username);

  Boolean existsByUsername(@NotBlank String username);

  Boolean existsByEmail(@NotBlank String email);

  default User getUser(UserDetailsImpl userDetails) {
    return getUserByName(userDetails.getUsername());
  }

  default User getUserByName(String username) {
    return findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
  }

  List<User> findAllByIdIn(List<Long> adminIds);
}