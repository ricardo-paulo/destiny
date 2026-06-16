package io.ricardo_paulo.Data;

public class Edge {

    private final int ID;
    private final String NAME;
    private final int VERTEXID1;
    private final String VERTEXNAME1;
    private final int VERTEXID2;
    private final String VERTEXNAME2;
    private final String GENERALCONDITION;
    private final double DISTANCE;
    private final String PAVING;
    private final String HOLES;
    private final int TOLLS;
    private final int PRFPOSTS;
    private final boolean INWORKS;
    private final int AVGPERMITTEDSPEED;

    public Edge(
            int id, String name, int vertexId1,
            String vertexName1, int vertexId2, String vertexName2,
            String generalCondition, double distance, String paving,
            String roles, int tolls, int prfPosts,
            boolean inWorks, int avgPermittedSpeed) {
        this.ID = id;
        this.NAME = name;
        this.VERTEXID1 = vertexId1;
        this.VERTEXNAME1 = vertexName1;
        this.VERTEXID2 = vertexId2;
        this.VERTEXNAME2 = vertexName2;
        this.GENERALCONDITION = generalCondition;
        this.DISTANCE = distance;
        this.PAVING = paving;
        this.HOLES = roles;
        this.TOLLS = tolls;
        this.PRFPOSTS = prfPosts;
        this.INWORKS = inWorks;
        this.AVGPERMITTEDSPEED = avgPermittedSpeed;
    }

    public int getId() {
        return ID;
    }

    public String getName() {
        return NAME;
    }

    public int getVertexId1() {
        return VERTEXID1;
    }

    public String getVertexName1() {
        return VERTEXNAME1;
    }

    public int getVertexId2() {
        return VERTEXID2;
    }

    public String getVertexName2() {
        return VERTEXNAME2;
    }

    public String getGeneralCondition() {
        return GENERALCONDITION;
    }

    public double getDistance() {
        return DISTANCE;
    }

    public String getPaving() {
        return PAVING;
    }

    public String getRoles() {
        return HOLES;
    }

    public int getTolls() {
        return TOLLS;
    }

    public int getPrfPosts() {
        return PRFPOSTS;
    }

    public boolean isInWorks() {
        return INWORKS;
    }

    public int getAvgPermittedSpeed() {
        return AVGPERMITTEDSPEED;
    }

    @Override
    public String toString() {
        return String.format("""
                Edge {
                id=%d,
                name=%s,
                vertexId1=%d,
                vertexName1=%s,
                vertexId2=%d,
                vertexName2=%s,
                generalCondition=%s,
                distance=%.2f,
                paving=%s,
                holes=%s
                tolls=%d
                prfPosts=%d
                inWorks=%b
                avgPermittedSpeed=%d
                }""", ID, NAME, VERTEXID1, VERTEXNAME1, VERTEXID2,
                VERTEXNAME2, GENERALCONDITION, DISTANCE, PAVING, HOLES,
                TOLLS, PRFPOSTS, INWORKS, AVGPERMITTEDSPEED);
    }
}
