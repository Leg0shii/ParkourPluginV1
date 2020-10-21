package de.legoshi.parkourpluginv1.util.playerinformation;

public class PlayerStatus {

			private boolean jumpmode;
			private boolean buildmode;
			private boolean replaymode;
			private boolean replayStart;
			private boolean spectatemode;
			private boolean dyeClick;
			private boolean gsClick;
			private int page;
			private boolean hasCP;
			private boolean nightvision;
			private boolean cppress;

			private boolean buildCourse;

			public PlayerStatus() {

						this.jumpmode = false;
						this.buildmode = false;
						this.dyeClick = false;
						this.buildCourse = false;
						this.gsClick = false;
						this.page = 0;
						this.hasCP = false;
						this.nightvision = false;
						this.cppress = false;
						this.replaymode = false;
						this.spectatemode = false;
						this.replayStart = false;
			}

			public boolean isReplayStart() { return replayStart; }

			public void setReplayStart(boolean replayStart) { this.replayStart = replayStart; }

			public boolean isReplaymode() { return replaymode; }

			public void setReplaymode(boolean replaymode) { this.replaymode = replaymode; }

			public boolean isSpectatemode() { return spectatemode; }

			public void setSpectatemode(boolean spectatemode) { this.spectatemode = spectatemode; }

			public boolean isCppress() { return cppress; }

			public void setCppress(boolean cppress) { this.cppress = cppress; }

			public boolean isNightvision() { return nightvision; }

			public void setNightvision(boolean nightvision) { this.nightvision = nightvision; }

			public boolean isHasCP() { return hasCP; }

			public void setHasCP(boolean hasCP) { this.hasCP = hasCP; }

			public boolean isGsClick() { return gsClick; }

			public void setGsClick(boolean gsClick) { this.gsClick = gsClick; }

			public int getPage() { return page; }

			public void setPage(int page) { this.page = page; }

			public boolean isJumpmode() { return jumpmode; }

			public void setJumpmode(boolean jumpmode) { this.jumpmode = jumpmode; }

			public boolean isBuildmode() { return buildmode; }

			public void setBuildmode(boolean buildmode) { this.buildmode = buildmode; }

			public boolean isDyeClick() { return dyeClick; }

			public void setDyeClick(boolean dyeClick) { this.dyeClick = dyeClick; }

			public boolean isBuildCourse() { return buildCourse; }

			public void setBuildCourse(boolean buildCourse) { this.buildCourse = buildCourse; }

}
