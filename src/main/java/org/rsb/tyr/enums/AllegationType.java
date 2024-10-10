package org.rsb.tyr.enums;

public enum AllegationType {
  HUMAN_TRAFFICKING("Human Trafficking"),
  DRUG_TRAFFICKING("Drug Trafficking"),
  WILDLIFE_SMUGGLING("Wildlife Smuggling"),
  TERRORISM("Terrorism"),
  FRAUD("Fraud"),
  MONEY_LAUNDERING("Money Laundering"),
  CORRUPTION("Corruption"),
  NONE("None");

  private final String displayName;

  AllegationType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
