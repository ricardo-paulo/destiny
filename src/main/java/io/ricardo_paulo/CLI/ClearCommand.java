package io.ricardo_paulo.CLI;

import picocli.CommandLine.Command;

@Command(
        name = "clear",
        mixinStandardHelpOptions = true,
        version = "clear 1.0",
        description = "Limpa o console."
)
public class ClearCommand implements Runnable {

    @Override
    public void run () {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
