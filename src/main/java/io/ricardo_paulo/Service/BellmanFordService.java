package io.ricardo_paulo.Service;

import io.ricardo_paulo.enums.RouteCriteria;
import java.util.ArrayList;

class BellmanFordService {

    private final Graph graph;
    private final int numVertices;

    public BellmanFordService(Graph graph, int numVertices) {
        this.graph = graph;
        this.numVertices = numVertices;
    }

    // Método que encontra a melhor rota com base no critério escolhido
    public RouteResult run(int source, int destination, RouteCriteria criteria) {
        double[] distances = new double[numVertices];
        int[] predecessor = new int[numVertices];

        // inicializa os vetores de distância e predecessores
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            predecessor[i] = -1;
        }
        distances[source] = 0;

        // Relaxamento das arestas do grafo
        for (int i = 0; i < numVertices - 1; i++) {
            for (int currentCity = 0; currentCity < numVertices; currentCity++) {
                if (distances[currentCity] == Double.MAX_VALUE) {
                    continue;
                }

                // Verifica se existe um caminho melhor passando pela cidade atual
                for (int destinationCity = 0; destinationCity < numVertices; destinationCity++) {
                    double weight = graph.getEdgeWeight(currentCity, destinationCity, criteria);

                    if (weight < Double.MAX_VALUE && distances[currentCity] + weight < distances[destinationCity]) {
                        distances[destinationCity] = distances[currentCity] + weight;
                        predecessor[destinationCity] = currentCity;
                    }
                }
            }
        }
        // Monta o resultado da rota encontrada
        RouteResult result = new RouteResult();
        result.setCriteriaUsed(criteria);
        if (distances[destination] == Double.MAX_VALUE) {
            return result;
        }
        result.setTotalCost(distances[destination]);
        result.setPathIds(buildPath(predecessor, source, destination));
        result.setGraph(graph);
        return result;
    }

    // Reconstrói o caminho
    private ArrayList<Integer> buildPath( int[] predecessor, int source, int destination) {
        ArrayList<Integer> path = new ArrayList<>();
        for (int at = destination; at != -1; at = predecessor[at]) {
            path.add(0, at);
            if (at == source) {
                break;
            }
        }
        return path;
    }
}
