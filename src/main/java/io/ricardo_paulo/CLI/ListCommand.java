package io.ricardo_paulo.CLI;

import io.ricardo_paulo.Data.Edge;
import io.ricardo_paulo.Data.Vertex;
import io.ricardo_paulo.Main;
import io.ricardo_paulo.enums.ComponentType;
import io.ricardo_paulo.util.ListFormatter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.ArrayList;
import java.util.Objects;

@Command(
        name = "list",
        mixinStandardHelpOptions = true,
        version = "list 1.0",
        description = "Lista todos os itens de um determinado componente. Podendo ser: cidade, vértices ou rodovias."
)
public class ListCommand implements Runnable {

    @Parameters(paramLabel = "<componente>", description = "O tipo de componente a ser listado.")
    private ComponentType componentType;

    @Override
    public void run () {

        ListFormatter listFormatter;

        if (Objects.requireNonNull(componentType) == ComponentType.VERTICES) {
            Vertex[] vertices = Main.graph.getVertices();
            listFormatter = new ListFormatter(vertices);
            System.out.println(listFormatter.getFormattedList());

        } else if (Objects.requireNonNull(componentType) == ComponentType.CITIES) {

                Vertex[] vertices = Main.graph.getVertices();
                Vertex[] cities = getCities(vertices);
                listFormatter = new ListFormatter(cities);
                System.out.println(listFormatter.getFormattedList());

        } else if (Objects.requireNonNull(componentType) == ComponentType.HIGHWAYS) {

            Edge[] edges = Main.graph.getEdges();
            listFormatter = new ListFormatter(edges);
            System.out.println(listFormatter.getFormattedList());

        }

    }

    private Vertex[] getCities(Vertex[] allVertices) {

        ArrayList<Vertex> cities = new ArrayList<>();

        for (Vertex v : allVertices) {

            if (!v.getName().contains("/"))
                cities.add(v);

        }

        return cities.toArray(new Vertex[0]);

    }

}