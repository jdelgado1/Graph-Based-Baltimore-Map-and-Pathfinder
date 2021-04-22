package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public abstract class GraphTest {

  protected Graph<String, String> graph;

  @BeforeEach
  public void setupGraph() {
    this.graph = createGraph();
  }

  protected abstract Graph<String, String> createGraph();

  @Test
  @DisplayName("insert(v) returns a vertex with given data")
  public void canGetVertexAfterInsert() {

    Vertex<String> v1 = graph.insert("v1");
    assertEquals(v1.get(), "v1");
  }

  @Test
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertVertexThrowsInsertionExceptionWhenVIsNull() {
    try {
      graph.insert(null);
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertVertexThrowsInsertionExceptionWhenVIsAlreadyIn() {
    graph.insert("v1");
    try {
      graph.insert("v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenfirstVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(null, v, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenSecondVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(v, null, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsInsertionExceptionSelfLoopCreated() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(v, v, "e");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsInsertionExceptionDuplicateEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "e");
    try {
      graph.insert(v1, v2, "e");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionRemovedVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    graph.insert(v2, v3, "e");
    graph.remove(v1);
    try {
      graph.insert(v1, v2, "e2");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void getStartOfEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
    assertEquals(v1, graph.from(e1));
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void getEndOfEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
    assertEquals(v2, graph.to(e1));
  }


  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void fromPositionException() {
    try {
      graph.from(null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }


  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void toPositionException() {
    try {
      graph.to(null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void outgoingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v1, v3, "v1-v3");
    assertEquals(e1.get(), "v1-v2");
    assertEquals(e2.get(), "v1-v3");
    System.out.println(graph.outgoing(v1));
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void incomingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v3");
    Edge<String> e2 = graph.insert(v1, v3, "v2-v3");
    assertEquals(e1.get(), "v1-v3");
    assertEquals(e2.get(), "v2-v3");
    graph.incoming(v3);
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void outgoingPositionException() {
    try {
      graph.outgoing(null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void incomingPositionException() {
    try {
      graph.incoming(null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void removeVertexSimple() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals("v1", graph.remove(v1));
    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void removeEdgeSimple() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals("v1-v2", graph.remove(e1));
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void removeSameEdgeAgainThrowsException() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    //Vertex<String> v4 = graph.insert("v4");
    //Vertex<String> v5 = graph.insert("v5");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    //Edge<String> e3 = graph.insert(v1, v3, null);

    //graph.remove(v3);
    graph.remove(e1);
    //Edge<String> e11 = graph.insert(v1, v2, "v1-v2");
    graph.remove(e2);
    //graph.remove(v3);
    try {
      graph.remove(e2);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }


  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void removeSameEdgeAgainThrowsException2() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    //Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Vertex<String> v5 = graph.insert("v5");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    //Edge<String> e2 = graph.insert(v2, v3, "v2-v3");

    graph.remove(e1);
    e1 = graph.insert(v4, v5, "v4-v5");
    //graph.remove(e11);
    graph.remove(e1);
    /*
    try {
      graph.remove(e2);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }

     */
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void removeVertexWithEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    graph.insert(v1, v2, "v1-v2");
    graph.insert(v1, v3, "v1-v3");
    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void labelVertexSimple() {
    Vertex<String> v1 = graph.insert("v1");
    graph.label(v1, "first");
    assertEquals("first", graph.label(v1));
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void labelEdgeSimple() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    graph.label(e1, "first");
    assertEquals("first", graph.label(e1));
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void labelVertexThrowsPositionExceptionInvalidVertex() {
    Vertex<String> v1 = null;
    try {
      graph.label(v1, "first");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void labelEdgeThrowsPositionExceptionInvalidEdge() {
    Edge<String> e1 = null;
    try {
      graph.label(e1, "first");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void getLabelVertexThrowsPositionExceptionInvalidVertex() {
    Vertex<String> v1 = null;
    try {
      graph.label(v1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void getLabelEdgeThrowsPositionExceptionInvalidEdge() {
    Edge<String> e1 = null;
    try {
      graph.label(e1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void testingVerticesIterator() {
    graph.insert("v1");
    graph.insert("v2");
    for (Vertex<String> v: graph.vertices()) {
      System.out.println(v.get());
    }
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void testingEdgesIterator() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "v1-v2");
    graph.insert(v2, v1, "v2-v1");
    for (Edge<String> e: graph.edges()) {
      System.out.println(e.get());
    }

  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void printing1() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    //Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    Vertex<String> v5 = graph.insert("v5");
    //Vertex<String> v6 = graph.insert("v6");

    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    //Edge<String> e2 = graph.insert(v2, v3, "v2-v3");
    //Edge<String> e3 = graph.insert(v3, v4, "v3-v4");
    Edge<String> e4 = graph.insert(v4, v5, "v4-v5");
    //Edge<String> e5 = graph.insert(v5, v6, "v5-v6");
    //Edge<String> e6 = graph.insert(v6, v1, "v6-v1");

    for (Vertex<String> v: graph.vertices()) {
      System.out.println(v.get());
    }

    for (Edge<String> e: graph.edges()) {
      System.out.println(e.get());
    }

    System.out.println(graph.toString());
    graph.remove(e1);
    System.out.println(graph.toString());
    graph.remove(e4);
    System.out.println(graph.toString());

  }


  // TODO add more tests here.
}
