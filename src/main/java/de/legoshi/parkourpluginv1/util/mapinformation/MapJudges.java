package de.legoshi.parkourpluginv1.util.mapinformation;

public class MapJudges {

			private double highestPP;
			private double difficulty;
			private int minFails;
			private double minTime;

			public MapJudges() {

						this.highestPP = 0;
						this.difficulty = 0;
						this.minFails = 0;
						this.minTime = 0;

			}

			public MapJudges(double highestPP, double difficulty, int minFails, double minTime) {

						this.highestPP = highestPP;
						this.difficulty = difficulty;
						this.minFails = minFails;
						this.minTime = minTime;

			}

			public double getHighestPP() { return highestPP; }

			public void setHighestPP(double highestPP) { this.highestPP = highestPP; }

			public double getDifficulty() { return difficulty; }

			public void setDifficulty(double difficulty) { this.difficulty = difficulty; }

			public int getMinFails() { return minFails; }

			public void setMinFails(int minFails) { this.minFails = minFails; }

			public double getMinTime() { return minTime; }

			public void setMinTime(double minTime) { this.minTime = minTime; }

}
