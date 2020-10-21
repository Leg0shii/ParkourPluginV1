package de.legoshi.parkourpluginv1.util.playerinformation;

import de.legoshi.parkourpluginv1.util.FW;
import de.legoshi.parkourpluginv1.util.Replay;
import de.legoshi.parkourpluginv1.util.fakeplayer.NPC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class PlayerObject {

    private Player player;
    private PlayerMap playerMap; //saves informations depending the map player is playing
    private PlayerPlayStats playerPlayStats; //all stats that player has
    private PlayerStatus playerStatus; //status of player (jumpmode/buildmode)

    private NPC npc;
    private int taskID;
    private FW fw;
    private int index;

    private Replay replay;


    public PlayerObject(Player player) {

        this.player = player;
        this.playerMap = new PlayerMap();
        this.playerPlayStats = new PlayerPlayStats();
        this.playerStatus = new PlayerStatus();

        this.npc = null;
        this.taskID = -1;
        this.fw = null;
        this.index = 0;

        this.replay = null;

    }

    public Replay getReplay() {
        return replay;
    }

    public void setReplay(Replay replay) {
        this.replay = replay;
    }

    public int getTaskID() { return taskID; }

    public void setTaskID(int taskID) { this.taskID = taskID; }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public FW getFw() { return fw; }

    public void setFw(FW fw) { this.fw = fw; }

    public NPC getNpc() { return npc; }

    public void setNpc(NPC npc) { this.npc = npc; }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) { this.player = player; }

    public PlayerMap getPlayerMap() { return playerMap; }

    public void setPlayerMap(PlayerMap playerMap) { this.playerMap = playerMap; }

    public PlayerPlayStats getPlayerPlayStats() { return playerPlayStats; }

    public void setPlayerPlayStats(PlayerPlayStats playerPlayStats) { this.playerPlayStats = playerPlayStats; }

    public PlayerStatus getPlayerStatus() { return playerStatus; }

    public void setPlayerStatus(PlayerStatus playerStatus) { this.playerStatus = playerStatus; }

}

