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

import io.ricardo_paulo.enums.Algorithm;
import io.ricardo_paulo.enums.RouteCriteria;

public class Graph {

    private DataLayer dataLayer;

    public Graph(DataLayer dataLayer) {
        this.dataLayer = dataLayer;
    }

    public RouteResult calculateBestRoute(
            int sourceId,
            int destinationId,
            Algorithm algorithm,
            RouteCriteria criteria) {

        int numVertices = dataLayer.getVertices().length;
        switch (algorithm) {
            case DIJKSTRA:
                return new DijkstraService(this, numVertices)
                        .run(sourceId, destinationId, criteria);
            case BELLMAN_FORD:
                return new BellmanFordService(this, numVertices)
                        .run(sourceId, destinationId, criteria);
            default:
                throw new IllegalArgumentException("Unsupported algorithm.");
        }
    }

    public double getEdgeWeight(
            int source,
            int destination,
            RouteCriteria criteria) {
        Rodovia road =
                dataLayer.getMatrizAdjacencia()[source][destination];
        if (road == null) {
            return Double.MAX_VALUE;
        }
        double distance =
                Double.parseDouble(
                        road.getDistance().replace(",", "."));
        double speed =
                Double.parseDouble(
                        road.getAvgPermittedSpeed());
        switch (criteria) {
            case SHORTEST_DISTANCE:
                return distance;
            case FASTEST_TIME:
                if (speed <= 0) {
                    return Double.MAX_VALUE;
                }
                return distance / speed;
            case BEST_QUALITY:
                double qualityFactor =
                        getConditionMultiplier(
                                road.getGeneralCondition());
                return distance * qualityFactor;
            default:
                return distance;
        }
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

    public DataLayer getDataLayer() {
        return dataLayer;
    }
}
