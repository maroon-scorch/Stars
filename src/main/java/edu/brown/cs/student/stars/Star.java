package edu.brown.cs.student.stars;

public class Star {

    private String starID;
    private String starName;
    private final double x, y, z;

    public Star(String starID, String starName, double x, double y, double z) {
        this.starID = starID;
        this.starName = starName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double distanceTo(double xPos, double yPos, double zPos) {
        double xDiff = x - xPos;
        double yDiff = y - yPos;
        double zDiff = z - zPos;

        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) + Math.pow(zDiff, 2));
    }

    public String getName() {
        return starName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String toString() {
        return starID;
    }

}
