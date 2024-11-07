/*
package org.rsb.tyr.models;

import org.rsb.tyr.enums.CrimeType;
import org.rsb.tyr.enums.AllegationType;
import org.rsb.tyr.enums.CountryType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PersonBuilder {
  */
/** The crime committed by the person. *//*

  private CrimeType crimeType = CrimeType.NONE;

  */
/** Allegation(s) against the person. *//*

  private Set<AllegationType> allegationTypes = Collections.emptySet();

  */
/** Country of origin. *//*

  private CountryType countryType = CountryType.GERMANY;

  */
/** Name of the person. *//*

  private String name;

  */
/** Default constructor. *//*

  public PersonBuilder() {
    // Does nothing. Default constructor.
  }

  */
/**
   * Sets the crime committed by the person.
   *
   * @param crimeType crime committed by the person
   * @return this builder
   *//*

  public PersonBuilder withCrime(CrimeType crimeType) {
    this.crimeType = crimeType;
    return this;
  }

  public PersonBuilder withCountry(CountryType countryType) {
    this.countryType = countryType;
    return this;
  }

  public PersonBuilder withAllegations(Set<AllegationType> allegationTypes) {
    this.allegationTypes = new HashSet<>(allegationTypes);
    return this;
  }

  public PersonBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public Person build() {
    return new Person(
        new Crime(crimeType.name()),
        new Country(countryType.name()),
        new HashSet<>(Allegation.from(allegationTypes)),
        name);
  }
}
*/
