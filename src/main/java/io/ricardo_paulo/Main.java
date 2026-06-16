package io.ricardo_paulo;

import io.ricardo_paulo.CLI.CommandsHandler;
import io.ricardo_paulo.Service.Graph;
import picocli.CommandLine;
import java.util.Scanner;

public class Main {

    public static Graph graph = new Graph();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Olá, bem vindo(a) de volta!");
        System.out.println("Digite help para obter ajuda com os comandos e suas formas de uso.");

        while (true) {

            CommandsHandler cmdContainer = new CommandsHandler();
            CommandLine cmd = new CommandLine(cmdContainer);

            cmd.setCaseInsensitiveEnumValuesAllowed(true);

            System.out.print("\ndestiny-cli>");
            String rawInput = scanner.nextLine().trim();

            if (rawInput.equalsIgnoreCase("exit")) {
                System.out.println("Finalizando sessão. Até mais!");
                break;
            }

            String[] options = rawInput.split("\\s+(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            cmd.execute(options);

        }

        scanner.close();

    }
}