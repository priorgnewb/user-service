package org.faze.userservice.controllers;

import java.util.List;
import org.faze.userservice.models.Role;
import org.faze.userservice.models.User;
import org.faze.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

  @Autowired
  UserService userService;

  // Показать список всех администраторов
  @GetMapping("/alladmins")
  @PreAuthorize("hasRole('ADMIN')")
  public List<User> showAllAdmins() {
    return userService.showAllAdmins();
  }

  // Показать список ID всех администраторов
  @GetMapping("/alladminsid")
  @PreAuthorize("hasRole('ADMIN')")
  public List<Long> showAllAdminsId() {
    return userService.getAllAdminsId();
  }

  // Показать список всех пользователей
  @GetMapping("/allusers")
  @PreAuthorize("hasRole('ADMIN')")
  public List<User> showAllUsers() {
    return userService.showAllUsers();
  }

  // Посмотреть список доступных для назначения ролей
  @GetMapping("/roles")
  @PreAuthorize("hasRole('ADMIN')")
  public List<Role> showRoles() {
    return userService.showRoles();
  }

}
