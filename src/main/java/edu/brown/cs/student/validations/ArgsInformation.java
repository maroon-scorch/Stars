package edu.brown.cs.student.validations;

/**
 * ArgsInformation Object that stores values of the Information required
 * to be passed into the ArgsValidator Object.
 */
public class ArgsInformation {
  /**
   * The uniqueName of the Method that would be returned when all tests in the list of
   * StringValidation passes. This is the identifer used in switch cases for Commands.
   */
  private final String uniqueName;

  /**
   * The format of how the args would be that's printed when an error is triggered.
   * For example, if ["a: integer", "b: name", "c: boolean"] is passed, if Error is triggered
   * this would print something similar to:
   * "ERROR: the String arguments should be in format [a: integer] [b: name] [c: boolean].
   */
  private final String[] argsFormat;

  /**
   * The StringValidation methods each argument would be evaluated against,
   * The list of arguments are considered valid when all StringValidation return true.
   * Argument at Index 0 would be tested against Requirements at Index 0, and so on
   */
  private final StringValidation[] requirements;

  /**
   * The Error Message to be printed when a requirement returns false.
   */
  private final String[] errorMessages;

  /**
   * Creates an ArgsInformation Object, if the lengths of the three Arrays are inconsistent,
   * raise an Error.
   *
   * @param uniqueName    the uniqueName of the Constructor
   * @param argsFormat    the argsFormat of the Constructor
   * @param requirements  the requirements of the Constructor
   * @param errorMessages the errorMessages of the Constructor
   */
  public ArgsInformation(String uniqueName, String[] argsFormat, StringValidation[] requirements,
                         String[] errorMessages) {
    if ((requirements.length != errorMessages.length)
        || (argsFormat.length != requirements.length)) {
      throw new IllegalArgumentException("The length of "
          + "Regex Requirements and Error Messages should be the same");
    }
    this.uniqueName = uniqueName;
    this.argsFormat = argsFormat.clone();
    this.requirements = requirements.clone();
    this.errorMessages = errorMessages.clone();
  }

  /**
   * Gets the Unique Name.
   *
   * @return uniqueName
   */
  public String getUniqueName() {
    return uniqueName;
  }

  /**
   * Gets the argsFormat.
   *
   * @return argsFormat
   */
  public String[] getArgsFormat() {
    return argsFormat.clone();
  }

  /**
   * Gets the requirements.
   *
   * @return requirements
   */
  public StringValidation[] getRequirements() {
    return requirements.clone();
  }

  /**
   * Gets the errorMessages.
   *
   * @return errorMessages
   */
  public String[] getErrorMessages() {
    return errorMessages.clone();
  }

}
