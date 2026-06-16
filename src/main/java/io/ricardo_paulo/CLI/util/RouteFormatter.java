package io.ricardo_paulo.CLI.util;

public class RouteFormatter {

    private static final String nextSeparator = """
                                         │
                                         ▼
            """;

    private String from;
    private String to;
    // TODO Incluir os avisos de acordo com o que receber do Grafo.
    private String warnings;
    private float distance;
    private int tolls;
    private int maxVel;
    private String highway;

    public RouteFormatter () {

        // Dados de teste. Substituir por um objeto real passado por parâmetro.
        from = "Gurupi";
        to = "Cariri do Tocantins";
        warnings = "";
        distance = 22.4f;
        tolls = 0;
        maxVel = 80;
        highway = "BR-153";

    }

    public static String getNextSeparator () {
        return nextSeparator;
    }

    public void setInWorksWarn (boolean state) {

        String warnString = "Está em obras. ";
        boolean warnExists = warnings.contains(warnString);

        if (state && !warnExists) {
            warnings = warnings.concat(warnString);
        } else if (!state && warnExists) {
            warnings = warnings.replace(warnString, "");
        }

    }

    public void setHolesWarn (boolean state) {

        String warnString = "Contém de buracos na rodovia. ";
        boolean warnExists = warnings.contains(warnString);

        if (state && !warnExists) {
            warnings = warnings.concat(warnString);
        } else if (!state && warnExists) {
            warnings = warnings.replace(warnString, "");
        }
    }

    public String getResult () {
        String model = """
                 .--------------------------------------------------------.
                 | DE: %-27.27s| DISTANCIA: %7.2f Km |
                 | RODOVIA: %-22.22s| VÉL. MÁXIMA: %8d |
                 | PARA: %-25.25s| PEDÁGIOS: %11d |
                 |--------------------------------------------------------|
                 | AVISOS: %-47.47s|
                 '--------------------------------------------------------'
                """;

        if (warnings.isBlank())
            warnings = "Sem avisos.";

        return model.formatted(from, distance, highway, maxVel, to, tolls, warnings);
    }

}
