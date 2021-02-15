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

public class CommandHandler {
  /**
   * The map of keywords to the specific Command Object to execute.
   *
   */
  private final Map<String, Command> commandMap;

  public CommandHandler(Map<String, Command> commandMap) {
    this.commandMap = commandMap;
  }

  /**
   *
   */
  public static class NeighborsHandler implements TemplateViewRoute {
    String msg = "neighbors";

    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of(
          "title", "Stars: Query the database",
          "message", msg
      );
      return new ModelAndView(variables, "query.ftl");
    }
  }

  public static class RadiusHandler implements TemplateViewRoute {
    String msg = "radius";
    private final Map<String, Command> commandMap;
    public RadiusHandler(Map<String, Command> commandMap) {
      this.commandMap = commandMap;
    }

    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      Map<String, String[]> responseMap = qm.toMap();
      System.out.println(responseMap.keySet());
      ArrayList<String> args = new ArrayList<>();
      System.out.println(Arrays.toString(responseMap.get("radius-input")));
      System.out.println(Arrays.toString(responseMap.get("y-input")));

      String rad = qm.value("radius-input");
      String x = qm.value("x-input");
      String y = qm.value("y-input");
      String z = qm.value("z-input");

      args.add(rad);
      args.add(x);
      args.add(y);
      args.add(z);

      commandMap.get("radius").execute(args);

      Map<String, Object> variables = ImmutableMap.of(
          "title", "Stars: Query the database",
          "message", msg
      );
      return new ModelAndView(variables, "query.ftl");
    }
  }
}
