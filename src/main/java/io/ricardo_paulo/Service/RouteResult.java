package io.ricardo_paulo.Service;

import io.ricardo_paulo.Data.Edge;
import io.ricardo_paulo.enums.RouteCriteria;
import java.util.ArrayList;

public class RouteResult {

    private Graph graph;
    private ArrayList<Integer> pathIds = new ArrayList<>();
    private double totalCost = -1;
    private RouteCriteria criteriaUsed = null;

    public ArrayList<Integer> getPathIds() {
        return pathIds;
    }

    public Edge[] getEdges() {

        if (!routeExists())
            return new Edge[0];

        Edge[] result = new Edge[pathIds.size() - 1];

        for (int i = 1; i < pathIds.size(); i++) {
            result[i - 1] = graph.getIncidentEdge(pathIds.get(i - 1), pathIds.get(i));
        }

        return result;
    }

    protected void setPathIds(ArrayList<Integer> pathIds) {
        this.pathIds = pathIds;
    }

    public double getTotalCost() {
        return totalCost;
    }

    protected void setTotalCost(double totalCost) {
        if (totalCost >= -1)
            this.totalCost = totalCost;
    }

    public RouteCriteria getCriteriaUsed() {
        return criteriaUsed;
    }

    protected void setCriteriaUsed(RouteCriteria criteriaUsed) {
        this.criteriaUsed = criteriaUsed;
    }

    protected void setGraph(Graph graph) {
        this.graph = graph;
    }

    public boolean routeExists() {
        return !pathIds.isEmpty() && !(totalCost == -1) && criteriaUsed != null;
    }

    public Integer[] getEstimatedTimes() {
        Edge[] edges = getEdges();
        ArrayList<Integer> result = new ArrayList<>();

        for (Edge edge : edges) {
            result.add((int) (edge.getDistance()/edge.getAvgPermittedSpeed() * 60));
        }

        return result.toArray(new Integer[0]);
    }

}