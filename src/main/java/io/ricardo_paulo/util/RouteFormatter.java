package io.ricardo_paulo.util;

public class RouteFormatter {

    private static final String nextSeparator = """
                                         │
                                         ▼
            """;

    private final String from;
    private final String to;
    private String warnings = "";
    private final double distance;
    private final int tolls;
    private final int maxVel;
    private final String highway;

    public RouteFormatter(String from, String to, double distance, int tolls, int maxVel, String highway) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.tolls = tolls;
        this.maxVel = maxVel;
        this.highway = highway;
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
                 '--------------------------------------------------------'""";

        if (warnings.isBlank())
            warnings = "Sem avisos.";

        return model.formatted(from, distance, highway, maxVel, to, tolls, warnings);
    }

    public static String getNonExistent() {

        return """
                
                 .--------------------------------------------------------.
                 |                   A ROTA NÃO EXISTE!                   |
                 |                                                        |
                 |           Verifique se as cidades inseridas            |
                 |             foram digitadas corretamente.              |
                 '--------------------------------------------------------'""";
    }

}
