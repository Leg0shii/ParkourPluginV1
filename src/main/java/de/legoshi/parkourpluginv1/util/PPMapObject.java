package de.legoshi.parkourpluginv1.util;

public class PPMapObject {

    double pp;
    MapObject mapObject;

    public PPMapObject(double pp, MapObject mapObject) {
        this.pp = pp;
        this.mapObject = mapObject;
    }

    public double getPp() {
        return pp;
    }

    public void setPp(double pp) {
        this.pp = pp;
    }

    public MapObject getMapObject() {
        return mapObject;
    }

    public void setMapObject(MapObject mapObject) {
        this.mapObject = mapObject;
    }

}
