package org.rsb.tyr.services;

import java.util.List;
import java.util.Optional;
import org.rsb.tyr.models.Person;
import org.rsb.tyr.processors.PersonProcessor;
import org.rsb.tyr.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private final PersonProcessor personProcessor;
  private final PersonRepository personRepository;

  @Autowired
  public PersonService(PersonProcessor personProcessor, PersonRepository personRepository) {
    this.personProcessor = personProcessor;
    this.personRepository = personRepository;
  }

  public List<Person> getAllPersons() {
    return personProcessor.findAll();
  }

  public Optional<Person> getPersonById(Long id) {
    return personProcessor.findById(id);
  }

  public Person createPerson(Person person) {
    return personProcessor.save(person);
  }

  public void deletePerson(Long id) {
    personProcessor.deleteById(id);
  }

  public Optional<Person> getPersonByName(String name) {
    return Optional.ofNullable(personProcessor.getByName(name));
  }

  public Optional<Person> getPersonByNameWithDetails(String name) {
    Optional<Person> p = personProcessor.getByNameWithDetails(name);
    return p;
  }

  public void calculateScores() {
    personProcessor.calculateScores();
  }

  public Double getScore(String name) {
    return personProcessor.getScore(name);
  }

  public List<Person> getByScores() {
    return personProcessor.getByScores();
  }
}
