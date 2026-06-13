package io.ricardo_paulo.Service;
// classe resultado, para armazenar e exibir a rota encontrada pelo Dij e Bell
// aqui vamos listar o que é armazenado nos ID'S
// ARMAZENA O "CUSTO DE CADA ROTA, o -1 indica que não temos rota. :(
// toCLIString - mostra o texto no terminal.
// switch - só alegria!
// foi inserido get e set para - mas não é obrigatório - caso queira pagar - manter o cod menor - livre

import io.ricardo_paulo.enums.RouteCriteria;

import java.util.ArrayList;
import java.util.List;

public class RouteResult {

    private List<Integer> pathIds = new ArrayList<>();
    private double totalCost = -1;
    private RouteCriteria criteriaUsed;

    public List<Integer> getPathIds() {
        return pathIds;
    }
    public void setPathIds(List<Integer> pathIds) {
        this.pathIds = pathIds;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public RouteCriteria getCriteriaUsed() {
        return criteriaUsed;
    }
    public void setCriteriaUsed(RouteCriteria criteriaUsed) {
        this.criteriaUsed = criteriaUsed;
    }
    public String toCLIString() {

        if (totalCost == -1) {
            return "Nenhuma rota encontrada.";
        }
        String unit;
        switch (criteriaUsed) {
            case FASTEST_TIME:
                unit = " horas";
                break;
            case SHORTEST_DISTANCE:
                unit = " km";
                break;
            default:
                unit = " pontos de custo";
        }
        return "Rota (IDs): " + pathIds +
                "\nCusto: " +
                String.format("%.2f", totalCost) +
                unit;
    }
}