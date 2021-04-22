package hw8.experiment;

import hw8.StreetSearcher;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver {

  // Update this to any other data file for benchmarking experiments or testing.
  private static String getDataFile() {
    //return "campus.paths.txt";
    //return "baseball.txt";
    return "baltimore.streets.txt";
  }

  // Change the returned String to run StreetSearcher
  // with a different starting vertex
  private static String getStartCoordinate() {
    //return "-76.620883,39.326204";
    //return "Home";
    return "-76.6107,39.2866";
  }

  // Change the returned String to run StreetSearcher
  // with a different ending vertex
  private static String getDestinationCoordinate() {
    //return "-76.620647,39.331158";
    //return "First";
    return "-76.6175,39.3296";
  }

  private static void profileStreetSearcher(StreetSearcher searcher, File data,
                                            String start, String dest) {
    BasicProfiler.reset();
    BasicProfiler.start();

    try {
      searcher.loadNetwork(data);
      searcher.findShortestPath(start, dest);
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file " + data.getName());
      return;
    } catch (IllegalArgumentException e) {
      System.err.println("Invalid Endpoint: " + e.getMessage());
      return;
    }

    BasicProfiler.stop();

    String description = String.format(
        "\nRan with %s from %s to %s", data.getName(), start, dest
    );

    System.out.println(BasicProfiler.getStatistics(description));
  }

  /**
   * Execution starts here.
   *
   * @param args command-line arguments not used here.
   */
  public static void main(String[] args) {
    StreetSearcher streetSearcher = new StreetSearcher();

    URL url = Thread.currentThread().getContextClassLoader().getResource("");
    String path = url.getPath().replace("%20", " ")
        .replace("classes", "resources");
    Path dataFile = Paths.get(path, getDataFile());
    // On Windows, use this:
    // Path dataFile = Paths.get(path.substring(1), getDataFile());

    profileStreetSearcher(streetSearcher, dataFile.toFile(),
        getStartCoordinate(), getDestinationCoordinate());
  }
}