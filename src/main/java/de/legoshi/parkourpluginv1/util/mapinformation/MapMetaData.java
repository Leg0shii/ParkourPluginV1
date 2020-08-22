package de.legoshi.parkourpluginv1.util.mapinformation;

import org.bukkit.Location;

public class MapMetaData {

			private Location spawn;
			private String mapstatus;
			private String builder;
			private String name;
			private String mapType;

			public MapMetaData() {

						this.spawn = null;
						this.mapstatus = "";
						this.builder = "";
						this.name = "";
						this.mapType = "";

			}

			public MapMetaData(Location spawn, String mapstatus, String builder, String name, String mapType) {

						this.spawn = spawn;
						this.mapstatus = mapstatus;
						this.builder = builder;
						this.name = name;
						this.mapType = mapType;

			}

			public Location getSpawn() { return spawn; }

			public void setSpawn(Location spawn) { this.spawn = spawn; }

			public String getMapstatus() { return mapstatus; }

			public void setMapstatus(String mapstatus) { this.mapstatus = mapstatus; }

			public String getBuilder() { return builder; }

			public void setBuilder(String builder) { this.builder = builder; }

			public String getName() { return name; }

			public void setName(String name) { this.name = name; }

			public String getMapType() { return mapType; }

			public void setMapType(String mapType) { this.mapType = mapType; }

}
