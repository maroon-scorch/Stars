package edu.brown.cs.student.stars;

public class ArgsInformation {
  private final String uniqueName;
  private final String[] argsFormat;
  private final String[] regexRequirements;
  private final String[] errorMessages;

  public ArgsInformation(String uniqueName, String[] argsFormat, String[] regexRequirements, String[] errorMessages) {
    if ((regexRequirements.length != errorMessages.length)
        || (argsFormat.length != regexRequirements.length)) {
      throw new IllegalArgumentException("The length of "
          + "Regex Requirements and Error Messages should be the same");
    }
    this.uniqueName = uniqueName;
    this.argsFormat = argsFormat;
    this.regexRequirements = regexRequirements;
    this.errorMessages = errorMessages;
  }

  public String getUniqueName() {
    return uniqueName;
  }

  public String[] getArgsFormat() {
    return argsFormat;
  }

  public String[] getRegexRequirements() {
    return regexRequirements;
  }

  public String[] getErrorMessages() {
    return errorMessages;
  }

}
