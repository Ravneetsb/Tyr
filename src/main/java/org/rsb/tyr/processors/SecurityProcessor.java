package org.rsb.tyr.processors;

import org.rsb.tyr.models.Person;
import org.rsb.tyr.repositories.AllegationRepository;
import org.rsb.tyr.repositories.CountryRepository;
import org.rsb.tyr.repositories.CrimeRepository;
import org.rsb.tyr.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityProcessor {
  private final PersonRepository personRepository;
  private final CrimeRepository crimeRepository;
  private final AllegationRepository allegationRepository;
  private final CountryRepository countryRepository;

  @Autowired
  public SecurityProcessor(
      PersonRepository personRepository,
      CrimeRepository crimeRepository,
      AllegationRepository allegationRepository,
      CountryRepository countryRepository) {
    this.personRepository = personRepository;
    this.crimeRepository = crimeRepository;
    this.allegationRepository = allegationRepository;
    this.countryRepository = countryRepository;
  }

  public String check(String name) {
    Person person = personRepository.getByName(name);
    if (person == null) {
      // TODO: Implement insertCheck
    } else {
      return "SECURITY ALERT: PERSON IS DANGEROUS. DANGER SCORE: " + person.getScore();
    }
    return "NONE";
  }

  private String insertCheck(Person p) {
    return null;
  }

  public void allowEntry(String name) {
    personRepository.allowEntry(name);
  }

  public Boolean isEntryAllowed(String name) {
    return personRepository.isEntryAllowed(name);
  }

  public void denyEntry(String name) {
    personRepository.disallowEntry(name);
  }
}
