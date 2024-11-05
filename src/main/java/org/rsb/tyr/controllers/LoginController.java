package org.rsb.tyr.controllers;

import org.rsb.tyr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  
  @Autowired
  private UserService service;

  public LoginController(UserService service) {
    this.service = service;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    var user = service.getUserByName(userName).get();
    model.addAttribute("person", user);
    return "dashboard";
  }
}
