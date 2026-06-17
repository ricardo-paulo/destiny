package io.ricardo_paulo.Service;

/*
 * Classe responsável por intermediar o cálculo de rotas.
 *
 * Dependências pendentes:
 * - Camada Data (ainda não implementada/integrada).
 * - DijkstraService.
 * - BellmanFordService.
 *
 * Integração futura:
 * 1. Receber acesso aos vértices e arestas da camada Data.
 * 2. Definir como obter o peso das rodovias.
 * 3. Integrar os algoritmos de roteamento.
 */

// essa classe é básicamente o mapa - tenso
// não calcula Dij e bell
// o "dataLayer" é apenas os dados carregados do json
// Método calculateBestRoute ele recebe os "pesos"
// ele meio que fala: Encontrar a rota da cidade 1 para a cidade 8
//usando Dijkstra
//considerando o menor tempo.
// o método EdgeWeight é o mais importante:
// quando ele executa o Dij e o BEll eles precisam saber qual o peso

import io.ricardo_paulo.Data.Data;
import io.ricardo_paulo.Data.Edge;
import io.ricardo_paulo.Data.Vertex;
import io.ricardo_paulo.enums.Algorithm;
import io.ricardo_paulo.enums.RouteCriteria;
import io.ricardo_paulo.util.InputNormalizer;

public class Graph {

    private final Data dataLayer = new Data();
    private final int[][] adjacencyMatrix;
    private final Edge[][] edgeMatrix;
    private final Vertex[] vertices;
    private final int numVertices;

    public Graph() {
        // Carrega a camada de dados UMA ÚNICA VEZ ao criar o mapa
        Data dataLayer = new Data();
        var data = dataLayer.getData();

        this.vertices = data.getVertices();
        this.numVertices = this.vertices.length;
        this.adjacencyMatrix = data.getAdjacencyMatrix();

        this.edgeMatrix = new Edge[numVertices][numVertices];
        for (Edge edge : data.getEdges()) {
            int v1 = edge.getVertexId1();
            int v2 = edge.getVertexId2();
            this.edgeMatrix[v1][v2] = edge;
            this.edgeMatrix[v2][v1] = edge; // Se o grafo for não-direcionado
        }
    }

    public RouteResult calculateBestRoute(
            int sourceId,
            int destinationId,
            Algorithm algorithm,
            RouteCriteria criteria) {

        int numVertices = this.vertices.length;

        if (sourceId < 0 || sourceId > 89 || destinationId < 0 || destinationId > 89)
            return new RouteResult();

        return switch (algorithm) {
            case DIJKSTRA -> new DijkstraService(this, numVertices)
                    .run(sourceId, destinationId, criteria);
            case BELLMAN_FORD -> new BellmanFordService(this, numVertices)
                    .run(sourceId, destinationId, criteria);
        };
    }

    public RouteResult calculateBestRoute(
            String sourceName,
            String destinationName,
            Algorithm algorithm,
            RouteCriteria criteria
    ) {
        Vertex[] vertices = this.vertices;
        Vertex resultSource = null;
        Vertex resultDestination = null;

        for (Vertex vertex : vertices) {

            String normVertexName = new InputNormalizer(vertex.getName()).getNormalized();

            if (normVertexName.equalsIgnoreCase(sourceName)) {
                resultSource = vertex;
            }

            if (normVertexName.equalsIgnoreCase(destinationName)) {
                resultDestination = vertex;
            }
        }

        if (resultSource != null && resultDestination != null) {
            return calculateBestRoute(resultSource.getId(), resultDestination.getId(), algorithm, criteria);
        }

        return new RouteResult();
    }

    public double getEdgeWeight(int source, int destination, RouteCriteria criteria) {
        if (adjacencyMatrix[source][destination] == 0) {
            return Double.MAX_VALUE;
        }

        Edge incidentRoad = edgeMatrix[source][destination];
        if (incidentRoad == null) return Double.MAX_VALUE;

        double distance = incidentRoad.getDistance();
        int speed = incidentRoad.getAvgPermittedSpeed();

        return switch (criteria) {
            case SHORTEST_DISTANCE ->
                    distance;
            case FASTEST ->
                    (speed <= 0) ? Double.MAX_VALUE : (distance / speed)
                            * getPavingMultiplier(incidentRoad.getPaving())
                            * getHolesMultiplier(incidentRoad.getRoles())
                            * (1 + (incidentRoad.getTolls() * 0.2))
                            * (1 + (incidentRoad.getPrfPosts() * 0.1))
                            * (incidentRoad.isInWorks() ? 1.3 : 1.0);
            case BEST_QUALITY ->
                    distance * getConditionMultiplier(incidentRoad.getGeneralCondition())
                            * getHolesMultiplier(incidentRoad.getRoles());
        };
    }

    protected Edge getIncidentEdge(int source, int destination) {
        Edge[] highways = dataLayer.getData().getEdges();
        Edge incidentRoad = null;

        for (Edge edge : highways) {
            boolean firstMatchCombination = edge.getVertexId1() == source && edge.getVertexId2() == destination;
            boolean secondMatchCombination = edge.getVertexId1() == destination && edge.getVertexId2() == source;

            if (firstMatchCombination || secondMatchCombination) {
                incidentRoad = edge;
                break;
            }
        }

        assert incidentRoad != null;
        return incidentRoad;
    }

    public Vertex getVertexByName(String rawVertexName) {
        String normalizedVertexName = new InputNormalizer(rawVertexName).getNormalized();
        Vertex[] vertices = dataLayer.getData().getVertices();

        for (Vertex v : vertices) {
            String normalizedV = new InputNormalizer(v.getName()).getNormalized();
            if (normalizedV.equalsIgnoreCase(normalizedVertexName))
                return v;
        }

        return new Vertex();
    }

    public Vertex getVertexById(int vertexId) {
        Vertex[] vertices = dataLayer.getData().getVertices();

        for (Vertex v : vertices) {
            if (v.getId() == vertexId)
                return v;
        }

        return new Vertex();
    }

    private double getConditionMultiplier(String condition) {
        return switch (condition.toLowerCase()) {

            case "otimo" -> 0.1;

            case "bom" -> 0.2;

            case "regular" -> 0.6;

            case "ruim" -> 1.1;

            case "pessimo" -> 2.0;

            case "nao_pavimentado" -> 3.5;

            default -> 1.0;

        };
    }
    private double getPavingMultiplier(String paving) {
        return switch (paving.toLowerCase()) {

            case "duplicada" -> 0.1;

            case "pavimentada" -> 0.2;

            case "implantada" -> 0.8;

            case "leito natural" -> 1.6;

            default -> 1.0;

        };
    }
    private double getHolesMultiplier(String holes) {
        return switch (holes.toLowerCase()) {

            case "alta" -> 2.0;

            case "media" -> 1.2;

            case "baixa" -> 0.8;

            case "inexistente/irrelevante" -> 0.1;

            default -> 1.0;

        };
    }


}
// Não tem um método para demais, pois são numericos já.