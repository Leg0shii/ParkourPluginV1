package de.legoshi.parkourpluginv1.util.playerinformation;

import java.util.ArrayList;
import java.util.Timer;

public class PlayerPlayStats {

			private int rank;

			private double ppcount;
			private ArrayList<PPMapObject> ppScoreList;
			private int failscount;
			private long playtime;
			private long playtimeSave;
			private Timer timer;
			private int playcount;
			private int scorecount;
			private double accuracy;

			public PlayerPlayStats() {

						this.rank = 0;
						this.ppcount = 0;
						this.ppScoreList = new ArrayList<>();
						this.failscount = 0;
						this.playtime = 0;
						this.playtimeSave = 0;
						this.timer = new Timer();
						this.playcount = 0;
						this.scorecount = 0;
						this.accuracy = 0;

			}

			public int getRank() { return rank; }

			public void setRank(int rank) { this.rank = rank; }

			public double getPpcount() { return ppcount; }

			public void setPpcount(double ppcount) { this.ppcount = ppcount; }

			public ArrayList<PPMapObject> getPpScoreList() { return ppScoreList; }

			public void setPpScoreList(ArrayList<PPMapObject> ppScoreList) { this.ppScoreList = ppScoreList; }

			public int getFailscount() { return failscount; }

			public void setFailscount(int failscount) { this.failscount = failscount; }

			public long getPlaytime() { return playtime; }

			public void setPlaytime(long playtime) { this.playtime = playtime; }

			public long getPlaytimeSave() { return playtimeSave; }

			public void setPlaytimeSave(long playtimeSave) { this.playtimeSave = playtimeSave; }

			public Timer getTimer() { return timer; }

			public void setTimer(Timer timer) { this.timer = timer; }

			public int getPlaycount() { return playcount; }

			public void setPlaycount(int playcount) { this.playcount = playcount; }

			public int getScorecount() { return scorecount; }

			public void setScorecount(int scorecount) { this.scorecount = scorecount; }

			public double getAccuracy() { return accuracy; }

			public void setAccuracy(double accuracy) { this.accuracy = accuracy; }

}
