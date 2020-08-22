package de.legoshi.parkourpluginv1.util.mapinformation;

import org.bukkit.Location;

public class MapObject {

    int ID;
    MapMetaData mapMetaData;
    MapJudges mapJudges;

    public MapObject() {

        this.ID = 0;
        this.mapMetaData = new MapMetaData();
        this.mapJudges = new MapJudges();

    }

    public MapObject(int ID, MapMetaData mapMetaData, MapJudges mapJudges) {

        this.ID = ID;
        this.mapMetaData = mapMetaData;
        this.mapJudges = mapJudges;

    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public MapMetaData getMapMetaData() { return mapMetaData; }

    public void setMapMetaData(MapMetaData mapMetaData) { this.mapMetaData = mapMetaData; }

    public MapJudges getMapJudges() { return mapJudges; }

    public void setMapJudges(MapJudges mapJudges) { this.mapJudges = mapJudges; }
}
