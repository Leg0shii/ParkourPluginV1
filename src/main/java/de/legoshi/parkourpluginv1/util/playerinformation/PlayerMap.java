package de.legoshi.parkourpluginv1.util.playerinformation;

import de.legoshi.parkourpluginv1.util.mapinformation.MapObject;

public class PlayerMap {

			private MapObject mapObject;
			private double timeRelative;
			private int failsrelative;

			public PlayerMap() {

						this.mapObject = new MapObject();
						this.timeRelative = 0;
						this.failsrelative = 0;

			}

			public MapObject getMapObject() { return mapObject; }

			public void setMapObject(MapObject mapObject) { this.mapObject = mapObject; }

			public double getTimeRelative() { return timeRelative; }

			public void setTimeRelative(double timeRelative) { this.timeRelative = timeRelative; }

			public int getFailsrelative() { return failsrelative; }

			public void setFailsrelative(int failsrelative) { this.failsrelative = failsrelative; }
}
