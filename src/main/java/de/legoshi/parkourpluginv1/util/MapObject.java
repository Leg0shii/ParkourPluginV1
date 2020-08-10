package de.legoshi.parkourpluginv1.util;

import org.bukkit.Location;

public class MapObject {

    String name;
    double highestPP;
    int ID;
    double difficulty;
    double stars;
    int minFails;
    double minTime;
    Location spawn;

    public MapObject() {

    }

    public MapObject(String name, int ID, double difficulty, double stars, int minFails, double minTime, Location spawn) {

        this.name = name;
        this.ID = ID;
        this.difficulty = difficulty;
        this.stars = stars;
        this.minFails = minFails;
        this.minTime = minTime;
        this.spawn = spawn;
        this.highestPP = 0;

    }

    public double getHighestPP() { return highestPP; }

    public void setHighestPP(double highestPP) { this.highestPP = highestPP; }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public int getMinFails() {
        return minFails;
    }

    public void setMinFails(int minFails) {
        this.minFails = minFails;
    }

    public double getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }
}
