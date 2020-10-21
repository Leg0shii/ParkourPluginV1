package de.legoshi.parkourpluginv1.util.playerinformation;

import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;

import java.util.ArrayList;

public class PlayerMap {

			private MapObject mapObject;
			private double timeRelative;
			private int failsrelative;
			private double ppcountRelative;
			private ArrayList<MapObject> mapObjectArrayList;

			public PlayerMap() {

						this.mapObject = new MapObject();
						this.timeRelative = 0;
						this.failsrelative = 0;
						this.mapObjectArrayList = new ArrayList<>();
						this.ppcountRelative = 100000000;

			}

			public double getPpcountRelative() { return ppcountRelative; }

			public void setPpcountRelative(double ppcountRelative) { this.ppcountRelative = ppcountRelative; }

			public ArrayList<MapObject> getMapObjectArrayList() { return mapObjectArrayList; }

			public void setMapObjectArrayList(ArrayList<MapObject> mapObjectArrayList) { this.mapObjectArrayList = mapObjectArrayList; }

			public MapObject getMapObject() { return mapObject; }

			public void setMapObject(MapObject mapObject) { this.mapObject = mapObject; }

			public double getTimeRelative() { return timeRelative; }

			public void setTimeRelative(double timeRelative) { this.timeRelative = timeRelative; }

			public int getFailsrelative() { return failsrelative; }

			public void setFailsrelative(int failsrelative) { this.failsrelative = failsrelative; }
}
