package hw8;

import exceptions.InsertionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * Search for the shortest path between two endpoints using
 * Djikstra's. We use a HashMap to store all the vertices so we can
 * find them by name (i.e. their coordinates) when inserting for a
 * fast duplicates check.
 *
 * <p>Vertex data is the coordinates, stored as a String.
 * Vertex label is the Edge into it on the path found.
 * Edge data is the road name, stored as a String.
 * Edge label is the road length, stored as a Double.</p>
 */
public final class StreetSearcher {

  // useful for marking distance to nodes, or use Double.POSITIVE_INFINITY
  private static final double MAX_DISTANCE = 1e18;

  private Map<String, Vertex<String>> vertices;
  private SparseGraph<String, String> graph;
  //private Map<Vertex<String>, Vertex<String>> previous;

  /**
   * Creates a StreetSearcher object.
   */
  public StreetSearcher() {
    vertices = new HashMap<>();
    graph = new SparseGraph<>();
    //previous = new HashMap<>();
  }

  // Get the path by tracing labels back from end to start.
  private List<Edge<String>> getPath(Vertex<String> end,
                                     Vertex<String> start) {
    if (graph.label(end) != null) {
      List<Edge<String>> path = new ArrayList<>();

      Vertex<String> cur = end;
      Edge<String> road;
      while (cur != start) {
        road = (Edge<String>) graph.label(cur);  // unchecked cast ok
        path.add(road);
        cur = graph.from(road);
      }
      return path;
    }
    return null;
  }

  // Print the path found.
  private void printPath(List<Edge<String>> path,
                         double totalDistance) {
    if (path == null) {
      System.out.println("No path found");
      return;
    }

    System.out.println("Total Distance: " + totalDistance);
    for (int i = path.size() - 1; i >= 0; i--) {
      System.out.println(path.get(i).get() + " "
          + graph.label(path.get(i)));
    }
  }


  /**
   * Utilizes Dijkstra Algorithm to find the shortest path.
   *
   * @param startName starting vertex name
   * @param endName   ending vertex name
   */
  public void findShortestPath(String startName, String endName) {
    checkValidEndpoint(startName);
    checkValidEndpoint(endName);

    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);

    double totalDist;  // totalDist must be update below

    Map<Vertex<String>, Double> distance = new HashMap<>();
    //Map<Vertex<String>, Vertex<String>> previous = new HashMap<>();
    Set<Vertex<String>> explored = new HashSet<>();
    PriorityQueue<VertexDistancePair> pq = new PriorityQueue<>();



    addPQ(distance, start, pq);



    dijkstraHelper(pq, end, explored, distance);






    totalDist = distance.get(end);
    List<Edge<String>> path = getPath(end, start);
    printPath(path, totalDist);
  }



  private void addPQ(Map<Vertex<String>, Double> distance,
                     Vertex<String> start,
                     PriorityQueue<VertexDistancePair> pq) {
    for (Vertex<String> vertex : vertices.values()) {

      if (vertex.get().equals(start.get())) {
        distance.put(vertex, 0.0);

      } else {
        distance.put(vertex, MAX_DISTANCE);

      }
      //previous.put(vertex, null);
      VertexDistancePair vertDist = new
              VertexDistancePair(vertex, distance.get(vertex));
      pq.add(vertDist);
    }

    distance.put(start, 0.0);
  }

  private void dijkstraHelper(PriorityQueue<VertexDistancePair> pq,
                              Vertex<String> end, Set<Vertex<String>> explored,
                              Map<Vertex<String>, Double> distance) {
    while (!pq.isEmpty()) {
      VertexDistancePair vdp = pq.poll();
      Vertex<String> smallest = vdp.vertex;
      if (smallest.equals(end)) {
        break;
      }
      if (explored.contains(smallest)) {
        continue;
      }
      explored.add(smallest);
      double smDistance = vdp.distance;
      dijkstraHelper2(smallest, pq, explored, distance, smDistance);
    }
  }

  private void dijkstraHelper2(Vertex<String> smallest,
                               PriorityQueue<VertexDistancePair> pq,
                               Set<Vertex<String>> explored,
                               Map<Vertex<String>, Double> distance,
                               double smDistance) {
    for (Edge<String> edge : graph.outgoing(smallest)) {
      Vertex<String> outgoingVert = graph.to(edge);
      double roadLength = (double) graph.label(edge);
      if (!explored.contains(outgoingVert)) {
        double theDist = smDistance + roadLength;
        VertexDistancePair vdp2 = new
                VertexDistancePair(outgoingVert, theDist);
        pq.add(vdp2);
        if (theDist < distance.get(outgoingVert)) {
          graph.label(outgoingVert, edge);
          distance.put(outgoingVert, theDist);
          //previous.put(outgoingVert, smallest);
        }
      }
    }
  }


  private static class VertexDistancePair
          implements Comparable<VertexDistancePair> {
    Vertex<String> vertex;
    double distance;

    VertexDistancePair(Vertex<String> vertex, double distance) {
      this.vertex = vertex;
      this.distance = distance;
    }

    @Override
    public int compareTo(VertexDistancePair other) {
      return (int) (Math.signum(this.distance - other.distance));
    }
  }









  // Add an endpoint to the network if it is a new endpoint
  private Vertex<String> addLocation(String name) {
    if (!vertices.containsKey(name)) {
      Vertex<String> v = graph.insert(name);
      vertices.put(name, v);
      return v;
    }
    return vertices.get(name);
  }

  /**
   * Load network from data file.
   *
   * @param data File must be a list of edges
   *             with distances, in the format
   *             specified in the homework instructions.
   * @throws FileNotFoundException thrown if invalid file provided
   */
  public void loadNetwork(File data)
      throws FileNotFoundException {

    int numRoads = 0;

    // Read in from file fileName
    Scanner input = new Scanner(new FileInputStream(data));
    while (input.hasNext()) {

      // Parse the line in to <end1> <end2> <road-distance> <road-name>
      String[] tokens = input.nextLine().split(" ");
      String fromName = tokens[0];
      String toName = tokens[1];
      double roadDistance = Double.parseDouble(tokens[2]);
      String roadName = tokens[3];

      boolean roadAdded = addRoad(fromName, toName, roadDistance, roadName);
      if (roadAdded) {
        numRoads += 2;
      }
    }

    System.out.println("Network Loaded!");
    System.out.println("Loaded " + numRoads + " roads");
    System.out.println("Loaded " + vertices.size() + " endpoints");
  }

  private boolean addRoad(
      String fromName, String toName, double roadDistance, String roadName
  ) {
    // Get the from and to endpoints, adding if necessary
    Vertex<String> from = addLocation(fromName);
    Vertex<String> to = addLocation(toName);

    // Add the road to the network - We assume all roads are two-way and
    // ignore if we've already added the road as a reverse of another
    try {

      Edge<String> road = graph.insert(from, to, roadName);
      Edge<String> backwardsRoad = graph.insert(to, from, roadName);

      // Label each road with it's weight
      graph.label(road, roadDistance);
      graph.label(backwardsRoad, roadDistance);

    } catch (InsertionException ignored) {
      return false;
    }

    return true;
  }

  private void checkValidEndpoint(String endpointName) {
    if (!vertices.containsKey(endpointName)) {
      throw new IllegalArgumentException(endpointName);
    }
  }
}
