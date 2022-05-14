package org.faze.userservice.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.faze.userservice.exception.AccessDeniedException;
import org.faze.userservice.exception.AppException;
import org.faze.userservice.exception.BadRequestException;
import org.faze.userservice.exception.ResourceNotFoundException;
import org.faze.userservice.models.ERole;
import org.faze.userservice.models.Role;
import org.faze.userservice.models.User;
import org.faze.userservice.payload.response.ApiResponse;
import org.faze.userservice.payload.response.UserProfile;
import org.faze.userservice.repository.RoleRepository;
import org.faze.userservice.repository.UserRepository;
import org.faze.userservice.security.services.UserDetailsImpl;
import org.faze.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public UserProfile getCurrentUser(UserDetailsImpl userDetails) {

    return new UserProfile(
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail());
  }

  @Override
  public UserProfile getUserProfile(String username) {
    User user = userRepository.getUserByName(username);

    if (user == null) {
      return new UserProfile();
    }

    return new UserProfile(user.getId(), user.getUsername(), user.getEmail());
  }

  @Override
  public User addUser(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
          "Указанный username уже используется");
      throw new BadRequestException(apiResponse);
    }

    if (userRepository.existsByEmail(user.getEmail())) {
      ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Указанный e-mail уже используется");
      throw new BadRequestException(apiResponse);
    }

    Set<Role> roles = new HashSet<>();
    roles.add(
        roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new AppException("Роль пользователя не установлена")));
    user.setRoles(roles);

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public User updateUser(User newUser, String username, UserDetailsImpl currentUser) {
    User user = userRepository.getUserByName(username);

    if (user.getId().equals(currentUser.getId()) ||
        currentUser.getAuthorities()
            .contains(new SimpleGrantedAuthority(ERole.ROLE_ADMIN.toString()))) {

      user.setPassword(passwordEncoder.encode(newUser.getPassword()));

      return userRepository.save(user);
    }

    ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
        "У вас нет разрешений на изменение указанного пользователя");
    throw new BadRequestException(apiResponse);
  }

  @Override
  public ApiResponse deleteUser(String username, UserDetailsImpl currentUser) {

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", username));

    if (!user.getId().equals(currentUser.getId()) &&
        !currentUser.getAuthorities()
            .contains(new SimpleGrantedAuthority(ERole.ROLE_ADMIN.toString()))) {

      ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
          "У вас нет разрешений на изменение указанного пользователя: " + username);
      throw new AccessDeniedException(apiResponse);
    }

    userRepository.deleteById(user.getId());

    return new ApiResponse(Boolean.TRUE, "Пользователь: " + username + " был успешно удален");
  }

  @Override
  public ApiResponse giveAdmin(String username) {
    User user = userRepository.getUserByName(username);
    Set<Role> roles = new HashSet<>();

    roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
        .orElseThrow(() -> new AppException("Роль не установлена")));
    roles.add(
        roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new AppException("Роль не установлена")));
    user.setRoles(roles);
    userRepository.save(user);

    return new ApiResponse(Boolean.TRUE,
        "Роль администратора была успешно установлена пользователю: " + username);

  }

  @Override
  public ApiResponse takeAdmin(String username) {
    User user = userRepository.getUserByName(username);
    Set<Role> roles = new HashSet<>();

    roles.add(
        roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new AppException("Роль не установлена")));
    user.setRoles(roles);
    userRepository.save(user);

    return new ApiResponse(Boolean.TRUE,
        "Роль администратора была успешно отозвана у пользователя: " + username);

  }

  @Override
  public List<User> showAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public List<Role> showRoles() {
    return roleRepository.findAll();
  }

  @Override
  public List<User> showAllAdmins() {
    List<Long> adminIds = getAllAdminsId();
    return userRepository.findAllByIdIn(adminIds);
  }

  // Вернуть список id администраторов сервиса
  public List<Long> getAllAdminsId() {
    String sql = "SELECT user_id FROM user_roles WHERE role_id=3";
    return jdbcTemplate.query(
        sql,
        (rs, rowNum) -> rs.getLong("user_id")
    );
  }
}
