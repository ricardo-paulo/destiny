package io.ricardo_paulo.CLI;

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

    @Parameters(paramLabel = "<destino>", description = "Cidade de Destino.")
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
        System.out.printf("Calculando rota: %s -> %s\n", origin, destiny);
        System.out.printf("Considerando como prioridade: %s\n", priority);
    }

}
