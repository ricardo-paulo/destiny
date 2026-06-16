package io.ricardo_paulo.Data;

public class Vertex {

    private final int ID;
    private final String NAME;

    public Vertex(int id, String name) {
        this.ID = id;
        this.NAME = name;
    }

    public Vertex() {
        this.ID = -1;
        this.NAME = "";
    }

    public int getId() {
        return ID;
    }

    public String getName() {
        return NAME;
    }

    @Override
    public String toString() {
        return "Vertex{id=" + ID + ", name='" + NAME + "'}";
    }
}
