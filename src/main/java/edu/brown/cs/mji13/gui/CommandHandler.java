package edu.brown.cs.mji13.gui;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.mji13.command.Command;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class CommandHandler implements TemplateViewRoute {
  /**
   * The map of keywords to the specific Command Object to execute.
   *
   */
  private final Command cmd;
  private final ArrayList<String> args;

  public CommandHandler(Command cmd, ArrayList<String> args) {
    this.cmd = cmd;
    this.args = args;
  }

  @Override
  public ModelAndView handle(Request req, Response res) {
    QueryParamsMap qm = req.queryMap();
    ArrayList<String> argValues = new ArrayList<>();
    args.forEach((key) -> argValues.add(qm.value(key)));

    String message = cmd.executeForGUI(argValues);
//    cmd.execute(argValues);
//    ArrayList<String> message = cmd.getMessages();
//    cmd.clearMessage();

    Map<String, Object> variables = ImmutableMap.of(
        "title", "Stars: Query the database",
        "message", message
    );
    return new ModelAndView(variables, "modal.ftl");
  }
}
