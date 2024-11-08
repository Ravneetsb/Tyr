package org.rsb.tyr.controllers;

import org.rsb.tyr.config.EndpointLister;
import org.rsb.tyr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  private final UserService service;
  private final EndpointLister endpointLister;

  @Autowired
  public LoginController(UserService service, EndpointLister endpointLister) {
    this.service = service;
    this.endpointLister = endpointLister;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    // Get user information
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    var user = service.getUserByName(userName).get();

    // Add both user and endpoints to the model
    model.addAttribute("person", user);
    model.addAttribute("endpoints", endpointLister.getEndpoints());

    return "dashboard";
  }
}
