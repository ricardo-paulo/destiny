package io.ricardo_paulo.CLI;

import io.ricardo_paulo.CLI.util.Algorithm;
import io.ricardo_paulo.CLI.util.InputNormalizer;
import io.ricardo_paulo.CLI.util.Priority;
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
            paramLabel = "<priority>",
            description = "A prioridade usada para calcular a melhor rota. Disponíveis: ${COMPLETION-CANDIDATES}"
    )
    private Priority priority;

    @Override
    public void run () {

        String noAccentOrigin = new InputNormalizer(origin).getNormalized();
        String noAccentDestiny = new InputNormalizer(destiny).getNormalized();

        if (!isValidInput(noAccentOrigin)) {
            System.out.println("A origem passada por parâmetro é inválida!");
        } else if (!isValidInput(noAccentDestiny)) {
            System.out.println("O destino passado por parâmetro é inválido!");
        } else {
            System.out.printf("Calculando rota: %s -> %s\n", origin, destiny);
            System.out.printf("Considerando como prioridade: %s\n", priority);
        }

    }

    private boolean isValidInput (String noAccentInput) {
        boolean isNumber = noAccentInput.chars().allMatch(Character::isDigit);
        boolean isText = noAccentInput.chars().allMatch(Character::isAlphabetic);

        return isNumber || isText;
    }

}
