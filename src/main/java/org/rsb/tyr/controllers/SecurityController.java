/*
package org.rsb.tyr.controllers;

import org.rsb.tyr.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic/security")
public class SecurityController {
  private final SecurityService securityService;

  @Autowired
  public SecurityController(SecurityService securityService) {
    this.securityService = securityService;
  }

  @RequestMapping("/{name}")
  public String checkPerson(@PathVariable String name) {
    return securityService.check(name);
  }

  @RequestMapping("/allow/{name}")
  public String allowEntry(@PathVariable String name) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    securityService.allowEntry(name, userName);
    return "Allowed " + name + "to enter.";
  }

  @RequestMapping("/isAllowed/{name}")
  public Boolean isEntryAllowed(@PathVariable String name) {
    return securityService.isEntryAllowed(name);
  }

  @RequestMapping("/deny/{name}")
  public String denyEntry(@PathVariable String name) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    securityService.denyEntry(name, userName);
    return "Denied " + name + " entry.";
  }
}
*/
package org.rsb.tyr.controllers;

import org.rsb.tyr.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/basic/security")
public class SecurityController {
  private final SecurityService securityService;

  @Autowired
  public SecurityController(SecurityService securityService) {
    this.securityService = securityService;
  }

  @GetMapping
  public String showCheckForm() {
    return "person-check";
  }

  @GetMapping("/check")
  public String checkPerson(@RequestParam String name, Model model) {
    String result = securityService.check(name);
    model.addAttribute("result", result);
    model.addAttribute("searchedName", name);

    if (result != null && !result.equals("NONE")) {
      // Extract score from the result string
      String scoreStr = result.split("DANGER SCORE: ")[1];
      double score = Double.parseDouble(scoreStr);
      model.addAttribute("score", score);
    }

    return "person-check";
  }

  @PostMapping("/allow/{name}")
  public String allowEntry(@PathVariable String name, Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    securityService.allowEntry(name, userName);

    // Redirect back to check with the same name to show updated status
    return "redirect:/basic/security/check?name=" + name;
  }

  @PostMapping("/deny/{name}")
  public String denyEntry(@PathVariable String name, Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    securityService.denyEntry(name, userName);

    // Redirect back to check with the same name to show updated status
    return "redirect:/basic/security/check?name=" + name;
  }
}