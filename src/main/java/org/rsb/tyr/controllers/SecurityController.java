package org.rsb.tyr.controllers;

import org.rsb.tyr.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
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
    securityService.allowEntry(name);
    return "Allowed " + name + "to enter.";
  }

  @RequestMapping("/isAllowed/{name}")
  public Boolean isEntryAllowed(@PathVariable String name) {
    return securityService.isEntryAllowed(name);
  }

  @RequestMapping("/deny/{name}")
  public String denyEntry(@PathVariable String name) {
    securityService.denyEntry(name);
    return "Denied " + name + " entry.";
  }
}
