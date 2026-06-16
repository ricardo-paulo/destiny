package io.ricardo_paulo.CLI;

import io.ricardo_paulo.Main;
import io.ricardo_paulo.Service.RouteResult;
import io.ricardo_paulo.enums.Algorithm;
import io.ricardo_paulo.enums.RouteCriteria;
import io.ricardo_paulo.util.InputNormalizer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(
        name = "route",
        mixinStandardHelpOptions = true,
        version = "route 1.2",
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

        if (isNumber(origin)) {

            routeResult = Main.graph.calculateBestRoute(
                    Integer.parseInt(origin),
                    Integer.parseInt(destiny),
                    algorithm,
                    routeCriteria
            );

        } else {
            String noAccentOrigin = new InputNormalizer(origin).getNormalized();
            String noAccentDestiny = new InputNormalizer(destiny).getNormalized();

            routeResult = Main.graph.calculateBestRoute(
                    noAccentOrigin,
                    noAccentDestiny,
                    algorithm,
                    routeCriteria
            );
        }

        System.out.println(routeResult.routeExists());

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
        return input.chars().allMatch(Character::isDigit);
    }

    private boolean isText (String input) {
        return input.chars().allMatch(Character::isAlphabetic);
    }

}
