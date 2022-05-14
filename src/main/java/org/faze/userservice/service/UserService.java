package org.faze.userservice.service;

import java.util.List;
import org.faze.userservice.models.Role;
import org.faze.userservice.models.User;
import org.faze.userservice.payload.response.ApiResponse;
import org.faze.userservice.payload.response.UserProfile;
import org.faze.userservice.security.services.UserDetailsImpl;

public interface UserService {

  UserProfile getCurrentUser(UserDetailsImpl userDetails);

  UserProfile getUserProfile(String username);

  User addUser(User user);

  User updateUser(User newUser, String username, UserDetailsImpl currentUser);

  ApiResponse deleteUser(String username, UserDetailsImpl currentUser);

  ApiResponse giveAdmin(String username);

  ApiResponse takeAdmin(String username);

  List<User> showAllUsers();

  List<Role> showRoles();

  List<User> showAllAdmins();

  List<Long> getAllAdminsId();
}
