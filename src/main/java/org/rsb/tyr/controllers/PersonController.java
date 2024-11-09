package org.rsb.tyr.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.rsb.tyr.models.Person;
import org.rsb.tyr.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/list")
public class PersonController {

  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    personService.calculateScores();
    this.personService = personService;
  }

  @GetMapping("/")
  public String getPersons(Model model) {
    personService.calculateScores();
    List<Person> list = personService.getAllPersons();
    list.sort(Comparator.comparingDouble(Person::getScore).reversed());
    List<Person> top10 = list.stream().limit(10).collect(Collectors.toList());
    model.addAttribute("persons", top10);
    return "person-list";
  }

  @GetMapping("/full")
  public String getAllPersons(Model model) {
    personService.calculateScores();
    List<Person> list = personService.getAllPersons();
    list.sort(Comparator.comparingDouble(Person::getScore).reversed());
    model.addAttribute("persons", list);
    return "person-list";
  }

  @GetMapping("/{name}")
  public String getPersonByNameWithDetails(@PathVariable String name, Model model) {
    return personService
        .getPersonByNameWithDetails(name)
        .map(
            person -> {
              model.addAttribute("person", person);
              return "person-details";
            })
        .orElse("person-not-found");
  }
}
