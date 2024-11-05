package org.rsb.tyr.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/** Authenticator class to hash and validate passwords using BCrypt */
public class Authenticator {

  public interface PlainPassword {}

  public interface HashedPassword {}

  /**
   * Hashes the password using BCrypt
   *
   * @param password the plain password to hash
   * @return the hashed password
   */
  public static Password<HashedPassword> hashPassword(Password<PlainPassword> password) {
    String hashed = BCrypt.hashpw(password.getValue(), BCrypt.gensalt());
    return new Password<>(hashed);
  }

  /**
   * Validates the password against the hashed password
   *
   * @param password the plain password to validate
   * @param hashedPassword the hashed password to validate against
   * @return true if the password is valid, false otherwise
   */
  public static boolean validatePassword(
      Password<PlainPassword> password, Password<HashedPassword> hashedPassword) {
    return BCrypt.checkpw(password.getValue(), hashedPassword.getValue());
  }

  /**
   * Phantom type to enforce type safety on passwords
   *
   * @param <T>
   */
  public static class Password<T> {
    private final String value;

    private Password(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static Password<PlainPassword> plain(String password) {
      return new Password<>(password);
    }

    public static Password<HashedPassword> hashed(String hashedPassword) {
      return new Password<>(hashedPassword);
    }
  }
}
