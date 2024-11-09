package org.rsb.tyr.processors;

import org.rsb.tyr.models.Person;
import org.rsb.tyr.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityProcessor {
  private final PersonRepository personRepository;
  private final CrimeRepository crimeRepository;
  private final AllegationRepository allegationRepository;
  private final CountryRepository countryRepository;
  private final UserRepository userRepository;

  @Autowired
  public SecurityProcessor(
      PersonRepository personRepository,
      CrimeRepository crimeRepository,
      AllegationRepository allegationRepository,
      CountryRepository countryRepository,
      UserRepository userRepository) {
    this.personRepository = personRepository;
    this.crimeRepository = crimeRepository;
    this.allegationRepository = allegationRepository;
    this.countryRepository = countryRepository;
    this.userRepository = userRepository;
  }

  public String check(String name) {
    Person person = personRepository.getByName(name);
    if (person == null) {
      return "PERSON NOT FOUND";
    } else {
      return "SECURITY ALERT: PERSON IS DANGEROUS. DANGER SCORE: " + person.getScore();
    }
  }

  public void allowEntry(String personName, String userName) {
    userRepository.allowEntry(userName);
    personRepository.allowEntry(personName);
  }

  public Boolean isEntryAllowed(String name) {
    return personRepository.isEntryAllowed(name);
  }

  public void denyEntry(String personName, String userName) {
    userRepository.denyEntry(userName);
    personRepository.disallowEntry(personName);
  }
}
