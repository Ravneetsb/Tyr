package org.rsb.tyr.controllers;

import org.rsb.tyr.models.AuthLevel;
import org.rsb.tyr.models.User;
import org.rsb.tyr.repositories.AuthLevelRepository;
import org.rsb.tyr.services.UserService;
import org.rsb.tyr.util.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Autowired private UserService userService;

  @Autowired private AuthLevelRepository authLevelRepository;

  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("authLevels", authLevelRepository.findAll());
    return "register-user";
  }

  @GetMapping("/users")
  public String listUsers(Model model) {
    List<User> users = userService.getAllUsers();
    model.addAttribute("users", users);
    return "users-list";
  }

  @PostMapping("/register")
  public String registerUser(
      @RequestParam String name,
      @RequestParam String password,
      @RequestParam String confirmPassword,
      @RequestParam String authLevel,
      Model model,
      RedirectAttributes redirectAttributes) {

    // Validation
    if (!password.equals(confirmPassword)) {
      model.addAttribute("error", "Passwords do not match");
      model.addAttribute("authLevels", authLevelRepository.findAll());
      return "register-user";
    }

    if (userService.getUserByName(name).isPresent()) {
      model.addAttribute("error", "User already exists");
      model.addAttribute("authLevels", authLevelRepository.findAll());
      return "register-user";
    }

    try {
      // Create new user
      User user = new User();
      user.setName(name);

      // Hash password
      Authenticator.Password<Authenticator.PlainPassword> plainPassword =
          Authenticator.Password.plain(password);
      Authenticator.Password<Authenticator.HashedPassword> hashedPassword =
          Authenticator.hashPassword(plainPassword);
      user.setPassword(hashedPassword.getValue());

      // Set auth level
      AuthLevel authLevelObj =
          authLevelRepository
              .findByName(authLevel)
              .orElseThrow(() -> new IllegalArgumentException("Invalid auth level"));
      user.setAuthLevel(authLevelObj);

      // Save user
      userService.registerNewUser(user);

      redirectAttributes.addFlashAttribute("success", "User registered successfully");
      return "redirect:/admin/users";

    } catch (Exception e) {
      model.addAttribute("error", "Error registering user: " + e.getMessage());
      model.addAttribute("authLevels", authLevelRepository.findAll());
      return "register-user";
    }
  }
}
