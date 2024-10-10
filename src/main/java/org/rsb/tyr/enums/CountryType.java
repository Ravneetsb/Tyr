package org.rsb.tyr.enums;

public enum CountryType {
  AFGHANISTAN("Afghanistan"),
  AUSTRIA("Austria"),
  BRAZIL("Brazil"),
  CHINA("China"),
  GERMANY("Germany"),
  INDIA("India"),
  ISRAEL("Israel"),
  KENYA("Kenya"),
  PALESTINE("Palestine"),
  RUSSIA("Russia"),
  UKRAINE("Ukraine");

  private final String displayName;

  CountryType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
