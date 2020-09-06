package de.legoshi.parkourpluginv1.util.playerinformation;

public class PlayerStatus {

			private boolean jumpmode;
			private boolean buildmode;
			private boolean dyeClick;
			private int page;

			private boolean buildCourse;

			public PlayerStatus() {

						this.jumpmode = false;
						this.buildmode = false;
						this.dyeClick = false;
						this.buildCourse = false;
						this.page = 0;

			}

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
