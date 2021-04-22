package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  // TODO You may need to add fields/constructor here!
  //private HashMap<Vertex<V>, ArrayList<Edge<E>>> incidenceList;
  private HashSet<Vertex<V>> vertices;
  private HashSet<Edge<E>> edges;
  //or use hash set for vertices

  /**
   * Constructor for sparse graph.
   */
  public SparseGraph() {
    //incidenceList = new HashMap<>();
    vertices = new HashSet<>();
    edges = new HashSet<>();
  }

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts and edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }




  private boolean hasVertex(VertexNode<V> vertex) {
    for (Vertex<V> theVertex : vertices) {
      if (theVertex.get().equals(vertex.data)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasEdge(EdgeNode<E> edge) {
    for (Edge<E> theEdge : edges) {
      EdgeNode<E> realEdge = convert(theEdge);
      if (realEdge.to.equals(edge.to) && realEdge.from.equals(edge.from)) {
        return true;
      }
    }
    return false;
  }




  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    if (v == null) {
      throw new InsertionException();
    }
    VertexNode<V> vertex = new VertexNode<>(v);
    vertex.owner = this;
    if (hasVertex(vertex)) {
      throw new InsertionException();
    }
    vertices.add(vertex);
    //incidenceList.put(vertex, null);
    return vertex;
  }

  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {

    if (from == null || to == null) {
      throw new PositionException();
    }
    if (from.equals(to)) {
      throw new InsertionException();
    }
    VertexNode<V> first = convert(from);
    VertexNode<V> second = convert(to);
    EdgeNode<E> edge = new EdgeNode<>(first, second, e);
    if (!hasVertex(first) || !hasVertex(second)) {
      throw new PositionException();
    }
    if (hasEdge(edge)) {
      throw new InsertionException();
    }
    insertEdgeHelper(from, to, first, second, edge);
    return edge;
  }

  private void insertEdgeHelper(Vertex<V> from,
                                Vertex<V> to, VertexNode<V> first,
                                VertexNode<V> second, EdgeNode<E> edge) {
    first.outgoing.put(to, edge);
    second.incoming.put(from, edge);
    edge.owner = this;
    edges.add(edge);
  }

  /*
  private void getIncidenceList() {

  }
   */

  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    if (v == null) {
      throw new PositionException();
    }
    VertexNode<V> vertex = convert(v);
    if (!hasVertex(vertex)) {
      throw new PositionException();
    }

    if (vertex.outgoing.isEmpty() && vertex.incoming.isEmpty()) {
      V data = vertex.data;
      vertices.remove(vertex);
      return data;
    } else {
      throw new RemovalException();
    }
  }

  /*
  @Override
  public E remove(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edge = convert(e);
    Edge<E> temp = null;
    EdgeNode<E> temp2 = null;
    for (Edge<E> thisEdge : outgoing(edge.from)) {
      temp = thisEdge;
      temp2 = convert(thisEdge);
      if (temp2.to.equals(edge.to) && temp2.data.equals(edge.data)) {
        break;
      }
    }
    if (temp != null) {
      edgeRemove(temp2, edge, temp);
    }
    return edge.data;
  }

  private void edgeRemove(EdgeNode<E> temp2, EdgeNode<E> edge2, Edge<E> temp) {
    temp2.from.outgoing.remove(temp);
    temp2.to.incoming.remove(temp);
    edge2.owner = null;
    edges.remove(edge2);
  }
   */


  @Override
  public E remove(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edge = convert(e);
    if (!hasEdge(edge)) {
      throw new PositionException();
    }
    E data = edge.data;
    edges.remove(edge);
    return data;
  }

  @Override
  public Iterable<Vertex<V>> vertices() {
    return Collections.unmodifiableSet(vertices);
  }

  @Override
  public Iterable<Edge<E>> edges() {
    return Collections.unmodifiableSet(edges);
  }

  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    if (v == null) {
      throw new PositionException();
    }
    VertexNode<V> vertex = convert(v);
    if (!hasVertex(vertex)) {
      throw new PositionException();
    }
    Map<Vertex<V>, Edge<E>> outgoing2 =
            Collections.unmodifiableMap(vertex.outgoing);
    return outgoing2.values();
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    if (v == null) {
      throw new PositionException();
    }
    VertexNode<V> vertex = convert(v);
    if (!hasVertex(vertex)) {
      throw new PositionException();
    }
    Map<Vertex<V>, Edge<E>> incoming2 =
            Collections.unmodifiableMap(vertex.incoming);
    return incoming2.values();
  }




  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edge = convert(e);
    if (!hasEdge(edge)) {
      throw new PositionException();
    }
    return edge.from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edge = convert(e);
    if (!hasEdge(edge)) {
      throw new PositionException();
    }
    return edge.to;
  }




  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    if (v == null) {
      throw new PositionException();
    }
    VertexNode<V> vertex = convert(v);
    if (!hasVertex(vertex)) {
      throw new PositionException();
    }
    vertex.label = l;
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edge = convert(e);
    if (!hasEdge(edge)) {
      throw new PositionException();
    }
    edge.label = l;
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    if (v == null) {
      throw new PositionException();
    }
    VertexNode<V> vertex = convert(v);
    if (!hasVertex(vertex)) {
      throw new PositionException();
    }
    if (vertex.label == null) {
      return null;
    }
    return vertex.label;
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edge = convert(e);
    if (!hasEdge(edge)) {
      throw new PositionException();
    }
    if (edge.label == null) {
      return null;
    }
    return edge.label;
  }

  @Override
  public void clearLabels() {
    for (Vertex<V> theVertex : vertices) {
      label(theVertex, null);
    }
    for (Edge<E> theEdge : edges) {
      label(theEdge, null);
    }
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }





  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;
    Map<Vertex<V>, Edge<E>> incoming;
    Map<Vertex<V>, Edge<E>> outgoing;
    //Map<Vertex<V>, Edge<E>> incoming2;
    //Map<Vertex<V>, Edge<E>> outgoing2;

    VertexNode(V v) {
      this.data = v;
      this.label = null;
      this.incoming = new HashMap<>();
      this.outgoing = new HashMap<>();
      //incoming2 = Collections.unmodifiableMap(incoming);
      //outgoing2 = Collections.unmodifiableMap(outgoing);
    }

    @Override
    public V get() {
      return this.data;
    }
  }




  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;

    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    @Override
    public E get() {
      return this.data;
    }
  }
}
