package edu.brown.cs.mji13.gui;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.mji13.command.Command;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.ArrayList;
import java.util.Map;

/**
 * The Handler of GUI GET Request for the Command Datatype.
 */
public class CommandHandler implements TemplateViewRoute {
  /**
   * The Command behind this specific handler.
   */
  private final Command cmd;
  /**
   * The list of arguments (keywords) that the Handler queries from the GET request's
   * query map.
   */
  private final ArrayList<String> args;

  /**
   * Constructs the CommandHandler with the given Command and argument.
   *
   * @param cmd  - the Command behind the Handler
   * @param args - the list of keywords to check for in the order.
   */
  public CommandHandler(Command cmd, ArrayList<String> args) {
    this.cmd = cmd;
    this.args = args;
  }

  /**
   * The Method that handles the GET Request from the forms in the FTL Template,
   * executes the Command based on the parameters given and returns the result.
   *
   * @param req  - the request from the GET
   * @param res - the response
   * @return the HTML to be passed down and used in the Front End.
   */
  @Override
  public ModelAndView handle(Request req, Response res) {
    // Gets the results from the Request
    QueryParamsMap qm = req.queryMap();
    ArrayList<String> argValues = new ArrayList<>();
    // Search through each String in args and find their corresponding value
    args.forEach((key) -> argValues.add(qm.value(key)));

    // Run the corresponding value in the Command
    String message = cmd.executeForGUI(argValues);

    // Returns the Command Output for GUI back to the Front End
    Map<String, Object> variables = ImmutableMap.of(
        "title", "Stars: Query the database",
        "message", message
    );
    return new ModelAndView(variables, "modal.ftl");
  }
}
