package org.rsb.tyr.controllers;

import org.rsb.tyr.models.Person;
import org.rsb.tyr.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping
  public List<Person> getAllPersons() {
    return personService.getAllPersons();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
    return personService
        .getPersonById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Person> getPersonByName(@PathVariable String name) {
    return personService
        .getPersonByName(name)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/name/details/{name}")
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
