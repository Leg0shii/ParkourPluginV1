package de.legoshi.parkourpluginv1.util;

import de.legoshi.parkourpluginv1.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Timer;
import java.util.UUID;

public class PlayerObject {

    private Player player;

    private int rank;

    private int failscount;
    private int failsrelative;

    private long playtimeSave;
    private double courseTime;
    private long playtime;

    private double ppcount;
    private ArrayList<PPMapObject> ppScoreList;

    private int playcount;
    private int scorecount;
    private double accuracy;
    private boolean jumpmode;
    private boolean buildmode;
    private boolean dyeClick;

    private boolean buildCourse;

    private MapObject mapObject;
    private UUID uuid;
    private Timer timer;

    private Main instance = Main.getInstance();
    private AsyncMySQL mySQL = instance.mySQL;

    public PlayerObject(UUID uuid, int playcount, double ppcount, long playtime, int scorecount, double accuracy, int failscount) {

        this.uuid = uuid;
        this.dyeClick = false;
        this.rank = 0;
        this.playcount = playcount;
        this.failsrelative = 0;
        this.courseTime = 0;
        this.playtimeSave = 0;
        this.ppcount = ppcount;
        this.playtime = playtime;
        this.scorecount = scorecount;
        this.accuracy = accuracy;
        this.jumpmode = false;
        this.failscount = failscount;
        this.ppScoreList = null;
        this.ppScoreList = new ArrayList<>();
        this.mapObject = new MapObject();
        this.timer = new Timer();
        this.player = Bukkit.getPlayer(uuid);
        this.buildmode = false;
        this.buildCourse = true;

    }

    public boolean isBuildCourse() { return buildCourse; }

    public void setBuildCourse(boolean buildCourse) { this.buildCourse = buildCourse; }

    public boolean isBuildmode() { return buildmode; }

    public void setBuildmode(boolean buildmode) { this.buildmode = buildmode; }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) { this.player = player; }

    public boolean isDyeClick() { return dyeClick; }

    public void setDyeClick(boolean dyeClick) {
        this.dyeClick = dyeClick;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public ArrayList<PPMapObject> getPpScoreList() {
        return ppScoreList;
    }

    public void setPpScoreList(ArrayList<PPMapObject> ppScoreList) {
        this.ppScoreList = ppScoreList;
    }

    public int getFailsrelative() {
        return failsrelative;
    }

    public void setFailsrelative(int failsrelative) {
        this.failsrelative = failsrelative;
    }

    public double getTimerelative() {
        return courseTime;
    }

    public void setTimerelative(double timeRelative) {
        this.courseTime = timeRelative;
    }

    public double getPpcount() { return ppcount; }

    public long getPlaytimeSave() { return playtimeSave; }

    public void setPlaytimeSave(long playtimeSave) {
        this.playtimeSave = playtimeSave;
    }

    public MapObject getMapObject() { return mapObject; }

    public void setMapObject(MapObject mapObject) { this.mapObject = mapObject; }

    public int getFailscount() { return failscount; }

    public void setFailscount(int failscount) {
        this.failscount = failscount;
    }

    public boolean isJumpmode() {
        return jumpmode;
    }

    public void setJumpmode(boolean jumpmode) {
        this.jumpmode = jumpmode;
    }

    public void setPpcount(double ppcount) {
        this.ppcount = ppcount;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public long getPlaytime() { return playtime; }

    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    public int getScorecount() {
        return scorecount;
    }

    public void setScorecount(int scorecount) {
        this.scorecount = scorecount;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public UUID getUuid() {
        return uuid;
    }

}

