package org.rsb.tyr.enums;

public enum CrimeType {
  HUMAN_TRAFFICKING("Human Trafficking"),
  DRUG_TRAFFICKING("Drug Trafficking"),
  WILDLIFE_SMUGGLING("Wildlife Smuggling"),
  TERRORISM("Terrorism"),
  FRAUD("Fraud"),
  MONEY_LAUNDERING("Money Laundering"),
  NONE("None");

  private final String displayName;

  CrimeType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
