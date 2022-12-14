package edu.brown.cs.mji13.stars;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import edu.brown.cs.mji13.command.Command;
import edu.brown.cs.mji13.command.REPLRunner;
import edu.brown.cs.mji13.gui.CommandHandler;
import edu.brown.cs.mji13.people.MockCSV;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;

import static java.util.Map.entry;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;

  /**
   * The common data-types shared by all the stars-related commands.
   */
  private final StarStorage starStorage = new StarStorage("storage");

  /**
   * The Hashmap of String Keys to which Command Object they correspond to.
   */
  private final Map<String, Command> commandMap = Map.ofEntries(
      entry("stars", new UpdateStarFile(starStorage)),
      entry("naive_radius", new NaiveRadius(starStorage)),
      entry("naive_neighbors", new NaiveNeighbors(starStorage)),
      entry("radius", new Radius(starStorage)),
      entry("neighbors", new Neighbors(starStorage)),
      entry("mock", new MockCSV())
  );

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // Creates the REPL Runner and stars the loop
    REPLRunner runner = new REPLRunner(commandMap);
    runner.run();
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // The Handlers for naive_radius searches
    CommandHandler naiveRadiusCords = new CommandHandler(commandMap.get("naive_radius"),
        new ArrayList<>(Arrays.asList("radius-input", "x-input", "y-input", "z-input")));
    CommandHandler naiveRadiusName = new CommandHandler(commandMap.get("naive_radius"),
        new ArrayList<>(Arrays.asList("radius-input", "name-input")));

    // The Handlers for naive_neighbors searches
    CommandHandler naiveNeighborsCords = new CommandHandler(commandMap.get("naive_neighbors"),
        new ArrayList<>(Arrays.asList("count-input", "x-input", "y-input", "z-input")));
    CommandHandler naiveNeighborsName = new CommandHandler(commandMap.get("naive_neighbors"),
        new ArrayList<>(Arrays.asList("count-input", "name-input")));

    // The Handlers for radius searches
    CommandHandler radiusCords = new CommandHandler(commandMap.get("radius"),
        new ArrayList<>(Arrays.asList("radius-input", "x-input", "y-input", "z-input")));
    CommandHandler radiusName = new CommandHandler(commandMap.get("radius"),
        new ArrayList<>(Arrays.asList("radius-input", "name-input")));

    // The Handlers for neighbors searches
    CommandHandler neighborsCords = new CommandHandler(commandMap.get("neighbors"),
        new ArrayList<>(Arrays.asList("count-input", "x-input", "y-input", "z-input")));
    CommandHandler neighborsName = new CommandHandler(commandMap.get("neighbors"),
        new ArrayList<>(Arrays.asList("count-input", "name-input")));

    // Setup Spark Routes
    Spark.get("/stars", new FrontHandler(), freeMarker);
    Spark.get("/naive-radius-cord", naiveRadiusCords, freeMarker);
    Spark.get("/naive-radius-name", naiveRadiusName, freeMarker);
    Spark.get("/naive-neighbors-cord", naiveNeighborsCords, freeMarker);
    Spark.get("/naive-neighbors-name", naiveNeighborsName, freeMarker);
    Spark.get("/radius-cord", radiusCords, freeMarker);
    Spark.get("/radius-name", radiusName, freeMarker);
    Spark.get("/neighbors-cord", neighborsCords, freeMarker);
    Spark.get("/neighbors-name", neighborsName, freeMarker);
  }

  /**
   * Handle requests to the front page of our Stars website.
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of(
          "title", "Stars: Query the database",
          "message", ""
      );
      return new ModelAndView(variables, "modal.ftl");
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

}
