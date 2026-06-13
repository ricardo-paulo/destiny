package io.ricardo_paulo.Service;

import io.ricardo_paulo.enums.RouteCriteria;

import java.util.ArrayList;
import java.util.List;

public class DijkstraService {

    private final Graph graph;
    private final int numVertices;

    public DijkstraService(Graph graph, int numVertices) {
        this.graph = graph;
        this.numVertices = numVertices;
    }

    // metodo onde encontra a melhor rota com base no critério escolhido
    public RouteResult run(int source, int destination, RouteCriteria criteria) {
        double[] distances = new double[numVertices];
        int[] predecessor = new int[numVertices];
        boolean[] visited = new boolean[numVertices];


        //aqui inicializa os vetores
        for (int i = 0; i < numVertices; i++) {
            distances[i] = Double.MAX_VALUE;
            predecessor[i] = -1;
            visited[i] = false;
        }
        distances[source] = 0;

        // aqui vai buscar a próxima cidade com menor distância
        for (int i = 0; i < numVertices; i++) {
            int currentCity = -1;

            for (int j = 0; j < numVertices; j++) {
                if (!visited[j] && (currentCity == -1 || distances[j] < distances[currentCity])) {
                    currentCity = j;
                }
            }
            if (currentCity == -1 || distances[currentCity] == Double.MAX_VALUE) {
                break;
            }
            visited[currentCity] = true;

            //Relaxamento das arestas do vértice que está sendo visitado
            for (int destinationCity = 0; destinationCity < numVertices; destinationCity++) {
                double weight = graph.getEdgeWeight(currentCity, destinationCity, criteria);

                if (weight < Double.MAX_VALUE && distances[currentCity] + weight < distances[destinationCity]) {
                    distances[destinationCity] = distances[currentCity] + weight;
                    predecessor[destinationCity] = currentCity;
                }
            }
        }
        //Resultado da rota encontrada (em objeto)
        RouteResult result = new RouteResult();
        result.setCriteriaUsed(criteria);

        if (distances[destination] == Double.MAX_VALUE) {
            return result;
        }
        result.setTotalCost(distances[destination]);

        result.setPathIds(buildPath (predecessor, source, destination));
        return result;
    }

    //Aqui vai apenas reconstruir o caminho
    private List<Integer> buildPath(int[] predecessor, int source, int destination) {
        List<Integer> path = new ArrayList<>();

        for (int at = destination; at != -1; at = predecessor[at]) {
            path.add(0, at);
            if (at == source) {
                break;
            }
        }
        return path;
    }
}