package org.rsb.tyr.controllers;

import java.util.Comparator;
import java.util.List;
import org.rsb.tyr.models.Person;
import org.rsb.tyr.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/denied")
public class PersonController {

  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping("/")
  public List<Person> getAllPersons() {
    personService.calculateScores();
    var list = personService.getAllPersons();
    list.sort(Comparator.comparingDouble(Person::getScore).reversed());
    return list;
  }

  @GetMapping("/{name}")
  public ResponseEntity<Person> getPersonByNameWithDetails(@PathVariable String name) {
    return personService
        .getPersonByNameWithDetails(name)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Person createPerson(@RequestBody Person person) {
    return personService.createPerson(person);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
    personService.deletePerson(id);
    return ResponseEntity.noContent().build();
  }
}
