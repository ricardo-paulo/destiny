package io.ricardo_paulo.CLI;

import io.ricardo_paulo.Data.Edge;
import io.ricardo_paulo.Data.Vertex;
import io.ricardo_paulo.Main;
import io.ricardo_paulo.Service.RouteResult;
import io.ricardo_paulo.enums.Algorithm;
import io.ricardo_paulo.enums.RouteCriteria;
import io.ricardo_paulo.util.InputNormalizer;
import io.ricardo_paulo.util.RouteFormatter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.ArrayList;

@Command(
        name = "route",
        mixinStandardHelpOptions = true,
        version = "route 1.3",
        description = "Traça rotas com base em um grafo de rodovias e cidades."
)
public class RouteCommand implements Runnable {

    @Parameters(paramLabel = "<origem>", description = "Cidade de origem.")
    private String origin;

    @Parameters(paramLabel = "<destino>", description = "Cidade de destino.")
    private String destiny;

    @Parameters(
            paramLabel = "<algoritmo>",
            description = "Algorítmo usado na busca. Disponíveis: ${COMPLETION-CANDIDATES}"
    )
    private Algorithm algorithm;

    @Parameters(
            paramLabel = "<routeCriteria>",
            description = "A prioridade usada para calcular a melhor rota. Disponíveis: ${COMPLETION-CANDIDATES}"
    )
    private RouteCriteria routeCriteria;

    @Override
    public void run () {

        if (isInvalidInput(origin)) {
            System.out.println("A origem passada por parâmetro é inválida!");
            return;
        } else if (isInvalidInput(destiny)) {
            System.out.println("O destino passado por parâmetro é inválido!");
            return;
        }

        if (!validInputTypes(origin, destiny)) {
            System.out.println("Os parâmetros passados são inválidos! Utilize apenas IDs ou apenas nomes.");
            return;
        }

        System.out.printf("Calculando rota: %s -> %s\n", origin, destiny);
        System.out.printf("Considerando como prioridade: %s\n", routeCriteria);

        RouteResult routeResult;
        boolean inputIsId;

        if (isNumber(origin)) {

            routeResult = Main.graph.calculateBestRoute(
                    Integer.parseInt(origin),
                    Integer.parseInt(destiny),
                    algorithm,
                    routeCriteria
            );

            inputIsId = true;

        } else {
            String noAccentOrigin = new InputNormalizer(origin).getNormalized();
            String noAccentDestiny = new InputNormalizer(destiny).getNormalized();

            routeResult = Main.graph.calculateBestRoute(
                    noAccentOrigin,
                    noAccentDestiny,
                    algorithm,
                    routeCriteria
            );

            inputIsId = false;

        }

        Edge[] routeEdges = routeResult.getEdges();
        ArrayList<String[]> nameSets;

        if (inputIsId) {
            nameSets = getNameSets(
                    routeEdges,
                    Main.graph.getVertexById(Integer.parseInt(origin)));
        } else {
            nameSets = getNameSets(
                    routeEdges,
                    Main.graph.getVertexByName(origin)
            );
        }

        if (nameSets.isEmpty())
            System.out.println(RouteFormatter.getNonExistent());

        for (int i = 0; i < routeEdges.length; i++) {

            int estimatedTime = routeResult.getEstimatedTimes()[i];

            RouteFormatter routeFormatter = getRouteFormatter(nameSets, i, routeEdges, estimatedTime);
            System.out.println(routeFormatter.getResult());
            if (i != routeEdges.length - 1) {
                System.out.println(RouteFormatter.getNextSeparator());
            }

        }

    }

    private static RouteFormatter getRouteFormatter(ArrayList<String[]> nameSets, int i,
                                                    Edge[] routeEdges, int estimatedTime) {
        RouteFormatter routeFormatter = new RouteFormatter(
                nameSets.get(i)[0],
                nameSets.get(i)[1],
                routeEdges[i].getDistance(),
                estimatedTime,
                routeEdges[i].getTolls(),
                routeEdges[i].getAvgPermittedSpeed(),
                routeEdges[i].getName()
        );

        if (routeEdges[i].isInWorks())
            routeFormatter.setInWorksWarn(true);

        if (!routeEdges[i].getRoles().equals("insistente/irrelevante"))
            routeFormatter.setHolesWarn(true);
        return routeFormatter;
    }

    private ArrayList<String[]> getNameSets (Edge[] routeEdges, Vertex inputOrigin) {

        ArrayList<String[]> nameSets = new ArrayList<>();
        int predecessor = -1;

        for (Edge currentEdge : routeEdges) {

            String[] set = new String[2];

            if (predecessor == -1) {

                if (currentEdge.getVertexId1() == inputOrigin.getId()) {

                    set[0] = new InputNormalizer(inputOrigin.getName()).getNormalized();
                    set[1] = new InputNormalizer(currentEdge.getVertexName2()).getNormalized();
                    predecessor = currentEdge.getVertexId2();

                } else if (currentEdge.getVertexId2() == inputOrigin.getId()) {

                    set[0] = new InputNormalizer(inputOrigin.getName()).getNormalized();
                    set[1] = new InputNormalizer(currentEdge.getVertexName1()).getNormalized();
                    predecessor = currentEdge.getVertexId1();

                }

            } else {

                if (predecessor == currentEdge.getVertexId1()) {

                    set[0] = new InputNormalizer(currentEdge.getVertexName1()).getNormalized();
                    set[1] = new InputNormalizer(currentEdge.getVertexName2()).getNormalized();
                    predecessor = currentEdge.getVertexId2();

                } else {

                    set[0] = new InputNormalizer(currentEdge.getVertexName2()).getNormalized();
                    set[1] = new InputNormalizer(currentEdge.getVertexName1()).getNormalized();
                    predecessor = currentEdge.getVertexId1();

                }

            }

            nameSets.add(set);

        }

        return nameSets;

    }

    private boolean isInvalidInput(String noAccentInput) {
        return !(isNumber(noAccentInput) || isText(noAccentInput));
    }

    private boolean validInputTypes(String origin, String destiny) {

        boolean allId = isNumber(origin)
                && isNumber(destiny);
        boolean allText = isText(origin)
                && isText(destiny);

        // Comparação XOR (exclusive OR)
        return allId ^ allText;

    }

    private boolean isNumber (String input) {
        return input.chars().noneMatch(Character::isAlphabetic);
    }

    private boolean isText (String input) {
        return input.chars().noneMatch(Character::isDigit);
    }

}
