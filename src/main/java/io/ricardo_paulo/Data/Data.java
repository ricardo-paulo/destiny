package io.ricardo_paulo.Data;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Data {

    private static final String VERTICES_PATH   = "/data/vertices.json";
    private static final String ADJACENCY_PATH  = "/data/adjacency.json";
    private static final String INCIDENCE_PATH  = "/data/incidence.json";
    private static final String EDGES_PATH = "/data/highways.json";

    public DataGetResult getData() {
        Edge[] edges = loadEdges();
        Vertex[] vertices = loadVertices();
        int totalVertices = vertices.length;
        int[][] adjacencyMatrix = loadAdjacency(totalVertices);
        int[][] incidenceMatrix = loadIncidence(totalVertices);

        return new DataGetResult(edges, vertices, adjacencyMatrix, incidenceMatrix);
    }


    private Edge[] loadEdges() {
        String jsonText = readFile(EDGES_PATH);
        String[] blocks = splitObjects(jsonText);

        ArrayList<Edge> list = new ArrayList<>();

        for (String block : blocks) {
            int id = Integer.parseInt(Objects.requireNonNull(extractValue(block, "id")));
            String name = extractValue(block, "highwayName");
            int vertexId1 = Integer.parseInt(Objects.requireNonNull(extractValue(block, "vertexId1")));
            String vertexName1 = extractValue(block, "vertexName1");
            int vertexId2 = Integer.parseInt(Objects.requireNonNull(extractValue(block, "vertexId2")));
            String vertexName2 = extractValue(block, "vertexName2");

            String generalCondition = extractValue(block, "generalCondition");
            double distance = Double.parseDouble(Objects.requireNonNull(extractValue(block, "distance")).replace(",", "."));
            String paving = extractValue(block, "paving");
            String holes = extractValue(block, "holes");
            int tolls = Integer.parseInt(Objects.requireNonNull(extractValue(block, "tolls")));
            int prfPosts = Integer.parseInt(Objects.requireNonNull(extractValue(block, "prfPosts")));
            boolean inWorks = Boolean.parseBoolean(Objects.requireNonNull(extractValue(block, "inWorks")));
            int avgPermittedSpeed = Integer.parseInt(Objects.requireNonNull(extractValue(block, "avgPermittedSpeed")));

            list.add(new Edge(id, name, vertexId1, vertexName1, vertexId2,
                    vertexName2, generalCondition, distance, paving, holes,
                    tolls, prfPosts, inWorks, avgPermittedSpeed));
        }

        return list.toArray(new Edge[0]);
    }

    
    private Vertex[] loadVertices() {
        String jsonText = readFile(VERTICES_PATH);
        String[] blocks = splitObjects(jsonText);

        ArrayList<Vertex> list = new ArrayList<>();

        for (String block : blocks) {
            int id = Integer.parseInt(Objects.requireNonNull(extractValue(block, "vertexId")));
            String name = extractValue(block, "vertexName");
            list.add(new Vertex(id, name));
        }

        return list.toArray(new Vertex[0]);
    }

    
    private int[][] loadAdjacency(int size) {
        String jsonText = readFile(ADJACENCY_PATH);

        int[][] matrix = new int[size + 1][size + 1];

        String[] blocks = splitObjects(jsonText);

        for (String block : blocks) {
            int rowId = Integer.parseInt(Objects.requireNonNull(extractValue(block, "vertexId")));

            String[] pairs = block.split(",");
            for (String pair : pairs) {
                pair = pair.trim();
                if (pair.contains("\"vertexId\"")) continue;

                String key = extractKey(pair);
                String value = extractRawValue(pair);

                if (key == null || value == null) continue;

                try {
                    int colId = Integer.parseInt(key.trim());
                    int cell = Integer.parseInt(value.trim());
                    if (colId >= 1 && colId <= size) {
                        matrix[rowId][colId] = cell;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        return matrix;
    }

    
    private int[][] loadIncidence(int totalVertices) {
        String jsonText = readFile(INCIDENCE_PATH);

        String[] blocks = splitObjects(jsonText);
        int totalHighways = blocks.length;

        
        int[][] matrix = new int[totalHighways + 1][totalVertices + 1];

        for (String block : blocks) {
            int rowId = Integer.parseInt(Objects.requireNonNull(extractValue(block, "highwayId")));

            String[] pairs = block.split(",");
            for (String pair : pairs) {
                pair = pair.trim();
                if (pair.contains("\"highwayId\"")) continue;

                String key   = extractKey(pair);
                String value = extractRawValue(pair);

                if (key == null || value == null) continue;

                try {
                    int colId = Integer.parseInt(key.trim());
                    int cell  = Integer.parseInt(value.trim());
                    if (colId >= 1 && colId <= totalVertices) {
                        matrix[rowId][colId] = cell;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        return matrix;
    }

    
    private String readFile(String path) {
        InputStream inputStream = Data.class.getResourceAsStream(path);

        if (inputStream == null)
            throw new RuntimeException("Erro crítico: O arquivo data/vertices.json não foi encontrado dentro do resources!");

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    
    private String[] splitObjects(String json) {
        ArrayList<String> blocks = new ArrayList<>();
        int depth = 0;
        int start = -1;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (depth == 0) start = i;
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0 && start != -1) {
                    blocks.add(json.substring(start + 1, i));
                    start = -1;
                }
            }
        }

        return blocks.toArray(new String[0]);
    }

    
    private String extractValue(String block, String key) {
        String search = "\"" + key + "\"";
        int keyIndex = block.indexOf(search);
        if (keyIndex == -1) return null;

        int colon = block.indexOf(":", keyIndex);
        if (colon == -1) return null;

        int valueStart = block.indexOf("\"", colon) + 1;
        int valueEnd   = block.indexOf("\"", valueStart);
        if (valueStart == 0 || valueEnd == -1) return null;

        return block.substring(valueStart, valueEnd);
    }

    
    private String extractKey(String pair) {
        int first = pair.indexOf("\"");
        int second = pair.indexOf("\"", first + 1);
        if (first == -1 || second == -1) return null;
        return pair.substring(first + 1, second);
    }

   
    private String extractRawValue(String pair) {
        int colon = pair.indexOf(":");
        if (colon == -1) return null;

        String after = pair.substring(colon + 1).trim();
        if (after.startsWith("\"")) {
            int s = after.indexOf("\"") + 1;
            int e = after.indexOf("\"", s);
            if (e == -1) return null;
            return after.substring(s, e);
        }

        return after.replace("}", "").trim();
    }
}
