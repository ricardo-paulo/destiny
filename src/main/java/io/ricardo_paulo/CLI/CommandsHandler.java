package io.ricardo_paulo.CLI;

import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Command;

@Command(
        name = "",
        description = "Handler de Comandos",
        subcommands = {
                RouteCommand.class,
                HelpCommand.class,
                ClearCommand.class
        }
)
public class CommandsHandler implements Runnable {

    @Override
    public void run () {
        System.out.println("Por favor, insira um comando válido! Para ver as opções digite --help.");
    }

}
