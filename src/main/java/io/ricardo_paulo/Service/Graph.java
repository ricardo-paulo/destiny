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

public class Graph {

    private final Data dataLayer = new Data();

    public RouteResult calculateBestRoute(
            int sourceId,
            int destinationId,
            Algorithm algorithm,
            RouteCriteria criteria) {

        int numVertices = dataLayer.getData().getVertices().length;

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
        Vertex[] vertices = dataLayer.getData().getVertices();
        Vertex resultSource = null;
        Vertex resultDestination = null;

        for (Vertex vertex : vertices) {
            if (vertex.getName().equalsIgnoreCase(sourceName)) {
                resultSource = vertex;
            }

            if (vertex.getName().equalsIgnoreCase(destinationName)) {
                resultDestination = vertex;
            }
        }

        if (resultSource != null && resultDestination != null) {
            return calculateBestRoute(resultSource.getId(), resultDestination.getId(), algorithm, criteria);
        }

        return new RouteResult();
    }

    public double getEdgeWeight(int source, int destination, RouteCriteria criteria) {

        int vertexAdjacency = dataLayer.getData().getAdjacencyMatrix()[source][destination];

        if (vertexAdjacency == 0) {
            return Double.MAX_VALUE;
        }

        Edge incidentRoad = getIncidentEdge(source, destination);

        double distance = incidentRoad.getDistance();
        int speed = incidentRoad.getAvgPermittedSpeed();

        return switch (criteria) {
            case SHORTEST_DISTANCE -> distance;
            case FASTEST -> {
                if (speed <= 0) {
                    yield Double.MAX_VALUE;
                }
                yield distance / speed;
            }
            case BEST_QUALITY -> {
                double qualityFactor =
                        getConditionMultiplier(incidentRoad.getGeneralCondition());
                yield distance * qualityFactor;
            }
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

    private double getConditionMultiplier(String condition) {
        if (condition == null) {
            return 1.5;
        }

        switch (condition.toLowerCase()) {
            case "otimo":
                return 1.0;
            case "bom":
                return 1.2;
            case "regular":
                return 1.5;
            case "ruim":
                return 2.0;
            case "pessimo":
                return 3.0;
            case "nao_pesquisado":
                return 1.5;
            default:
                return 1.5;
        }
    }

}
