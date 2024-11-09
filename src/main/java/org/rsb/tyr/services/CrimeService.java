package org.rsb.tyr.services;

import java.util.List;
import java.util.Optional;
import org.rsb.tyr.enums.CrimeType;
import org.rsb.tyr.models.Crime;
import org.rsb.tyr.repositories.CrimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrimeService {

  private final CrimeRepository crimeRepository;

  @Autowired
  public CrimeService(CrimeRepository crimeRepository) {
    this.crimeRepository = crimeRepository;
  }

  public List<Crime> getAllCrimes() {
    return crimeRepository.findAll();
  }

  public Crime getCrime(CrimeType type) {
    Optional<Crime> crime = crimeRepository.findByName(type.name());
    return crime.orElse(null);
  }
}
