package controller.command;

/**
 * Represents the function to execute a command offered by command objects.
 */
public interface ImageCommand {

  /**
   * Executes this command on the given image and stores the result with the given name.
   * @param inputName the name of the image to be modified by this command.
   * @param resultName the name to give to the resulting image
   */
  void execute(String inputName, String resultName);

}
