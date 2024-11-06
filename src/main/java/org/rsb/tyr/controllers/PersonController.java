/*
package org.rsb.tyr.controllers;

import java.util.Comparator;
import java.util.List;
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
    this.personService = personService;
  }

  @GetMapping("/")
  public String getAllPersons(Model model) {
    personService.calculateScores();
    List<Person> list = personService.getAllPersons();
    list.sort(Comparator.comparingDouble(Person::getScore).reversed());
    model.addAttribute("persons", list);
    return "person-list"; // Maps to src/main/resources/templates/person-list.html
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

  @PostMapping("/create")
  public String createPerson(@ModelAttribute Person person, Model model) {
    personService.createPerson(person);
    return "redirect:/api/list/"; // Redirects to list all persons
  }
}
*/
package org.rsb.tyr.controllers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.rsb.tyr.enums.AllegationType;
import org.rsb.tyr.enums.CountryType;
import org.rsb.tyr.enums.CrimeType;
import org.rsb.tyr.models.Person;
import org.rsb.tyr.models.PersonBuilder;
import org.rsb.tyr.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/list")
public class PersonController {

  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping("/")
  public String getAllPersons(Model model) {
    personService.calculateScores();
    List<Person> list = personService.getAllPersons();
    list.sort(Comparator.comparingDouble(Person::getScore).reversed());
    model.addAttribute("persons", list);
    return "person-list";
  }

  @GetMapping("/create")
  public String showCreateForm(Model model) {
    // Add enum values to the model for the form dropdowns
    model.addAttribute("countries", CountryType.values());
    model.addAttribute("crimes", CrimeType.values());
    model.addAttribute("allegations", AllegationType.values());
    return "submit-person";
  }

  @PostMapping("/create")
  public String createPerson(
      @RequestParam String name,
      @RequestParam String country,
      @RequestParam String crime,
      @RequestParam(required = false) String[] allegations,
      RedirectAttributes redirectAttributes) {

    try {
      // Convert string parameters to their respective enum types
      CountryType countryType = CountryType.valueOf(country);
      CrimeType crimeType = CrimeType.valueOf(crime);

      // Convert allegations array to Set<AllegationType>
      Set<AllegationType> allegationTypes = new HashSet<>();
      if (allegations != null && allegations.length > 0) {
        allegationTypes =
            Arrays.stream(allegations).map(AllegationType::valueOf).collect(Collectors.toSet());
      }

      // Use the PersonBuilder to create a new Person
      Person person =
          new PersonBuilder()
              .withName(name)
              .withCountry(countryType)
              .withCrime(crimeType)
              .withAllegations(allegationTypes)
              .build();

      // Save the person using the service
      personService.createPerson(person);

      // Add success message
      redirectAttributes.addFlashAttribute(
          "success", "Successfully created new person record for: " + name);

    } catch (IllegalArgumentException e) {
      // Handle invalid enum values
      redirectAttributes.addFlashAttribute("error", "Invalid input values: " + e.getMessage());
      return "redirect:/api/list/create";
    } catch (Exception e) {
      // Handle other errors
      redirectAttributes.addFlashAttribute("error", "Error creating person: " + e.getMessage());
      return "redirect:/api/list/create";
    }

    // Redirect to the list view after successful creation
    return "redirect:/api/list/";
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
