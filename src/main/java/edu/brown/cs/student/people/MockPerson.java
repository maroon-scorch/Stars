package edu.brown.cs.student.people;

import edu.brown.cs.student.validations.ArgsInformation;
import edu.brown.cs.student.validations.ArgsValidator;
import edu.brown.cs.student.validations.StringValFunctions;
import edu.brown.cs.student.validations.StringValidation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 * MockPerson Object for representing a MockPerson in the "mock <csv>" command.
 */
public class MockPerson implements StringValFunctions {

  /**
   * The list of fields MockPerson stores:
   * first name, last name, birth date, email, gender, and street address.
   */
  private String firstName, lastName, birthDate, email, gender, streetAddress;

  /**
   * Specifications on the requirements on the argument passed to the command:
   * first name - any String
   * last name - any String
   * date - mm/dd/yyyy (leading zeroes can be omitted)
   * email - [username]@[domain]
   * gender - any String
   * street address - number name suffix
   * See custom StringValidation methods at the Bottom.
   */
  private final Map<Integer, ArgsInformation[]> stringEntryValidation
      = Map.ofEntries(
      entry(6, new ArgsInformation[] {new ArgsInformation(
          "person",
          new String[] {"firstName: any string", "lastName: any string", "date: mm/dd/yyyy",
              "email: [username]@[domain]", "gender: any string",
              "street address: number name suffix"},
          new StringValidation[] {this::pass, this::pass, this::isMockDate, this::isMockEmail,
              this::pass, this::isMockStreetAddress},
          new String[] {"ERROR: Field FirstName is malformed",
              "ERROR: Field LastName is malformed",
              "ERROR: Date must exists and be in format mm/dd/yyyy (zero can be omitted)",
              "ERROR: Email Address must be in format [username]@[domain],"
                  + " domain should have at least one period",
              "ERROR: Field Gender is malformed",
              "ERROR: Street Address should start with numbers and be alphanumeric"
          }
      )}));

  /**
   * Creates a MockPerson Object with the names given.
   *
   * @param firstName     First Name of Person
   * @param lastName      Last Name of Person
   * @param birthDate     Birthdate of Person
   * @param email         Email of Person
   * @param gender        Gender of Person
   * @param streetAddress Street Address of Person
   */
  public MockPerson(String firstName, String lastName, String birthDate, String email,
                    String gender, String streetAddress) {
    ArrayList<String> stringArgs = new ArrayList<>();
    stringArgs.add(firstName);
    stringArgs.add(lastName);
    stringArgs.add(birthDate);
    stringArgs.add(email);
    stringArgs.add(gender);
    stringArgs.add(streetAddress);

    // Passes the ArrayList of Arguments to the Argument Validator
    ArgsValidator argsValidator = new ArgsValidator("mock-person:",
        stringEntryValidation);
    Optional<String> opMethodName = argsValidator.testArgs(stringArgs);
    if (opMethodName.isEmpty()) {
      throw new IllegalArgumentException("ERROR: The fields of the row does not"
          + " match the specifications of mock-person");
    }

    // If no errors are thrown, they passed the Argument Validation, precedes to construct
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.email = email;
    this.gender = gender;
    this.streetAddress = streetAddress;
  }


  /**
   * Formats the String to a default value is the input is empty.
   *
   * @param input
   * @return "[Undeclared/Empty]" if input is empty; otherwise, the input
   */
  public String formatEmpty(String input) {
    return (input.isEmpty()) ? "[Undeclared/Empty]" : input;
  }


  /**
   * Gives the String format of MockPerson if printed.
   *
   * @return the toString version of MockPerson
   */
  public String toString() {
    return "First Name: " + formatEmpty(firstName)
        + ", Last Name: " + formatEmpty(lastName)
        + ", Birth Date: " + formatEmpty(birthDate)
        + ", Email Address: " + formatEmpty(email)
        + ", Gender: " + formatEmpty(gender)
        + ", Street Address: " + formatEmpty(streetAddress);
  }


  /**
   * HashMap of the Number of Days in a Month, used in checking if Date is valid.
   */
  private final Map<Integer, Integer> monthToDays = Map.ofEntries(
      entry(1, 31),
      entry(2, 28),
      entry(3, 31),
      entry(4, 30),
      entry(5, 31),
      entry(6, 30),
      entry(7, 31),
      entry(8, 31),
      entry(9, 30),
      entry(10, 31),
      entry(11, 30),
      entry(12, 31));

  /**
   * Determines if the String Date is in the format mm/dd/yy, zero can be omitted.
   *
   * @param input
   * @return True if format is matched and date is in range
   */
  public Boolean isMockDate(String input) {
    boolean dateIsInRightFomat = input.matches(
        "([1-9]|0[1-9]|1[012])[/]([1-9]|0[1-9]|[12][0-9]|3[01])[/]\\d\\d\\d\\d");
    return (dateIsInRightFomat && isMockDateInRage(input));
  }

  /**
   * Given a string date in the format "mm/dd/yy" checks if the day and months are in range.
   *
   * @param input
   * @return True if date is in range
   */
  private Boolean isMockDateInRage(String input) {
    String[] dateArray = input.split("/");
    int month = Integer.parseInt(dateArray[0]);
    int day = Integer.parseInt(dateArray[1]);
    int year = Integer.parseInt(dateArray[2]);

    int monthDays = monthToDays.get(month);
    return ((day <= monthDays)
        || ((month == 2) && (day == (monthToDays.get(2) + 1)) && (year % 4 == 0)));
  }

  /**
   * Given a string email, checks if the email is valid.
   *
   * @param input
   * @return True if email is in the regex form specified = [username]@[domain]
   */
  public Boolean isMockEmail(String input) {
    // Referenced from https://www.tutorialspoint.com/validate-email-address-in-java
    return (input.isEmpty()
        || input.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w-_]+\\.)+[\\w]+[\\w]$"));
  }

  /**
   * Given a string street address, checks if the street address is valid.
   *
   * @param input
   * @return True if street address is in form - [street number] [any]
   */
  public Boolean isMockStreetAddress(String input) {
    return (input.isEmpty()
        || input.matches("(\\d+)[ ]([A-Za-z0-9\\s]+)"));
  }

}
