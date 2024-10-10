package org.rsb.tyr.processors;

import java.util.List;
import java.util.Optional;
import org.rsb.tyr.models.Person;
import org.rsb.tyr.repositories.AllegationRepository;
import org.rsb.tyr.repositories.CountryRepository;
import org.rsb.tyr.repositories.CrimeRepository;
import org.rsb.tyr.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonProcessor {
  private PersonRepository personRepository;
  private CrimeRepository crimeRepository;
  private CountryRepository countryRepository;
  private AllegationRepository allegationRepository;

  @Autowired
  public PersonProcessor(
      PersonRepository personRepository,
      CrimeRepository crimeRepository,
      CountryRepository countryRepository,
      AllegationRepository allegationRepository) {
    this.personRepository = personRepository;
    this.crimeRepository = crimeRepository;
    this.countryRepository = countryRepository;
    this.allegationRepository = allegationRepository;
  }

  public List<Person> getAllPersons() {
    return personRepository.findAll();
  }

  public Optional<Person> getPersonById(Long id) {
    return personRepository.findById(id);
  }

  public Person createPerson(Person person) {
    return personRepository.save(person);
  }

  public void deletePerson(Long id) {
    personRepository.deleteById(id);
  }

  public Optional<Person> getPersonByName(String name) {
    return Optional.ofNullable(personRepository.getByName(name));
  }

  public Optional<Person> getPersonByNameWithDetails(String name) {
    Optional<Person> p = personRepository.getByNameWithDetails(name);
    Person n = p.get();
    n.setCrime(crimeRepository.getCrime(name));
    return Optional.of(n);
  }

  public List<Person> findAll() {
    return personRepository.findAll();
  }

  public Optional<Person> findById(Long id) {
    return personRepository.findById(id);
  }

  public Person save(Person person) {
    return personRepository.save(person);
  }

  public void deleteById(Long id) {
    personRepository.deleteById(id);
  }

  public Person getByName(String name) {
    return personRepository.getByName(name);
  }

  public Optional<Person> getByNameWithDetails(String name) {
    Optional<Person> p = personRepository.getByNameWithDetails(name);
    Person n = p.orElse(null);
    if (n != null) {
      n.setCrime(crimeRepository.getCrime(name));
      n.setCountry(countryRepository.findByName(name));
      n.setAllegations(allegationRepository.findByName(name));
    }
    return Optional.ofNullable(n);
  }
}
