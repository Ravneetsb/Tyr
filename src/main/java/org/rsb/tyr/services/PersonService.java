package org.rsb.tyr.services;

import java.util.HashSet;
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
    String name = formatEnum(person.getName());
    HashSet<String> allegations = new HashSet<>();
    for (var allegation : person.getAllegations()) {
      allegations.add(formatEnum(allegation.getName()));
    }
    String crime = formatEnum(person.getCrime().getName());
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

  public String formatEnum(String enumValue) {
    String[] words = enumValue.split("_");
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < words.length; i++) {
      if (i > 0) {
        result.append(" ");
      }
      String word = words[i].toLowerCase();
      result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
    }
    return result.toString();
  }
}
