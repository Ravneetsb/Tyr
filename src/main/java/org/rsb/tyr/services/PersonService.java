package org.rsb.tyr.services;

import java.util.List;
import java.util.Optional;
import org.rsb.tyr.models.Person;
import org.rsb.tyr.processors.PersonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private final PersonProcessor personProcessor;

  @Autowired
  public PersonService(PersonProcessor personProcessor) {
    this.personProcessor = personProcessor;
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
}
