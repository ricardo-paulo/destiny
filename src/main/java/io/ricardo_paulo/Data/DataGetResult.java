package io.ricardo_paulo.Data;

public class DataGetResult {

    private final Edge[] edges;
    private final Vertex[] vertices;
    private final int[][] adjacencyMatrix;
    private final int[][] incidenceMatrix;

    public DataGetResult(Edge[] edges, Vertex[] vertices, int[][] adjacencyMatrix, int[][] incidenceMatrix) {
        this.edges = edges;
        this.vertices = vertices;
        this.adjacencyMatrix = adjacencyMatrix;
        this.incidenceMatrix = incidenceMatrix;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }
}
