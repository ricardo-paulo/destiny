package io.ricardo_paulo.util;

public class RouteFormatter {

    private static final String NEXT_SEPARATOR = """
                                         │
                                         ▼
            """;

    private final String from;
    private final String to;
    private String warnings = "";
    private final double distance;
    private final int estimatedTime;
    private final int tolls;
    private final int maxVel;
    private final String highway;

    public RouteFormatter(String from, String to, double distance, int estimatedTime, int tolls, int maxVel, String highway) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.tolls = tolls;
        this.maxVel = maxVel;
        this.highway = highway;
    }

    public static String getNextSeparator () {
        return NEXT_SEPARATOR;
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
                 | DE: %-23.23s| DISTANCIA: %11.2f Km |
                 | RODOVIA: %-18.18s| VÉL. MÁXIMA: %7d Km/h |
                 | PARA: %-20.20s | TEMPO ESTIMADO: %5d min |
                 |                            | PEDÁGIOS: %15d |
                 |--------------------------------------------------------|
                 | AVISOS: %-47.47s|
                 '--------------------------------------------------------'
                 """;

        if (warnings.isBlank())
            warnings = "Sem avisos.";

        return model.formatted(from, distance, highway, maxVel, to, estimatedTime, tolls, warnings);
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
