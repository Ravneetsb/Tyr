package org.rsb.tyr.models;

import org.rsb.tyr.enums.CrimeType;
import org.rsb.tyr.enums.Allegation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PersonBuilder {
  /** The crime committed by the person. */
  private CrimeType crimeType = CrimeType.NONE;

  /** Allegation(s) against the person. */
  private Set<Allegation> allegations = Collections.emptySet();

  /** Country of origin. */
  private Country country;

  /** Name of the person. */
  private String name;

  /** Default constructor. */
  public PersonBuilder() {
    // Does nothing. Default constructor.
  }

  /**
   * Sets the crime committed by the person.
   *
   * @param crimeType crime committed by the person
   * @return this builder
   */
  public PersonBuilder withCrime(CrimeType crimeType) {
    this.crimeType = crimeType;
    return this;
  }

  public PersonBuilder withCountry(Country country) {
    this.country = country;
    return this;
  }

  public PersonBuilder withAllegations(Set<Allegation> allegations) {
    this.allegations = new HashSet<>(allegations);
    return this;
  }

  public PersonBuilder withName(String name) {
    this.name = name;
    return this;
  }
}
