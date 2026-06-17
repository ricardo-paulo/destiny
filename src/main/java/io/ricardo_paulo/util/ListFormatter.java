package io.ricardo_paulo.util;

import io.ricardo_paulo.Data.Edge;
import io.ricardo_paulo.Data.Vertex;
import java.util.ArrayList;

public class ListFormatter {

    private final String HEADER = """
            .--------------------------------------------------------.
            |         ID          |               NOME               |
            *--------------------------------------------------------*
            """;

    private final String FOOTER = """
            '--------------------------------------------------------'
            """;

    private final String MIDDLE = "|%s|%s|\n";

    private final int ID_FIELD_CHARS = 21;
    private final int NAME_FIELD_CHARS = 34;

    private final ArrayList<String> ID_LIST = new ArrayList<>();
    private final ArrayList<String> NAME_LIST = new ArrayList<>();

    public ListFormatter(Vertex[] vertices) {

        for (Vertex v : vertices) {
            ID_LIST.add(String.valueOf(v.getId()));
            NAME_LIST.add(v.getName());
        }

    }

    public ListFormatter(Edge[] edges) {

        for (Edge e : edges) {
            ID_LIST.add(String.valueOf(e.getId()));
            NAME_LIST.add(e.getName());
        }

    }

    public String getFormattedList() {

        String result = "";

        result = result.concat(HEADER);
        for (int i = 0; i < ID_LIST.size(); i++) {
            String centeredId = center(ID_LIST.get(i), ID_FIELD_CHARS);
            String centeredName = center(NAME_LIST.get(i), NAME_FIELD_CHARS);

            result = result.concat(MIDDLE.formatted(centeredId, centeredName));
        }

        result = result.concat(FOOTER);

        return result;

    }

    private String center(String text, int totalChars) {

        int whiteSpaces = totalChars - text.length();
        int leftSpaces = whiteSpaces / 2;
        int rightSpaces = whiteSpaces - leftSpaces;

        return " ".repeat(leftSpaces) + text + " ".repeat(rightSpaces);

    }

}
