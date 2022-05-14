package org.faze.userservice.controllers;

import javax.validation.Valid;
import org.faze.userservice.models.User;
import org.faze.userservice.payload.response.ApiResponse;
import org.faze.userservice.payload.response.UserProfile;
import org.faze.userservice.security.CurrentUser;
import org.faze.userservice.security.services.UserDetailsImpl;
import org.faze.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  // Показать профиль текущего пользователя
  @GetMapping("/my")
  @PreAuthorize("hasAnyRole('USER','ADMIN','MODERATOR')")
  public ResponseEntity<UserProfile> getCurrentUser(@CurrentUser UserDetailsImpl currentUser) {
    UserProfile userProfile = userService.getCurrentUser(currentUser);

    return new ResponseEntity<>(userProfile, HttpStatus.OK);
  }

  // Показать профиль пользователя с указанным username
  @GetMapping("/{username}/profile")
  @PreAuthorize("hasAnyRole('USER','ADMIN','MODERATOR')")
  public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) {
    UserProfile userProfile = userService.getUserProfile(username);

    return new ResponseEntity<>(userProfile, HttpStatus.OK);
  }

  // Создать пользователя
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
    User newUser = userService.addUser(user);

    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }

  // Обновить профиль пользователя
  @PutMapping("/{username}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser,
      @PathVariable(value = "username") String username,
      @CurrentUser UserDetailsImpl currentUser) {
    User updatedUser = userService.updateUser(newUser, username, currentUser);

    return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
  }

  // Удалить профиль пользователя
  @DeleteMapping("/{username}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
      @CurrentUser UserDetailsImpl currentUser) {
    ApiResponse apiResponse = userService.deleteUser(username, currentUser);

    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  // Установить пользователю роль администратора
  @PutMapping("/{username}/giveAdmin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "username") String username) {
    ApiResponse apiResponse = userService.giveAdmin(username);

    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  // Отобрать у пользователя роль администратора
  @PutMapping("/{username}/takeAdmin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse> takeAdmin(@PathVariable(name = "username") String username) {
    ApiResponse apiResponse = userService.takeAdmin(username);

    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

}
