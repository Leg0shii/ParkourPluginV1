package de.legoshi.parkourpluginv1.util.playerinformation;

import org.bukkit.entity.Player;

public class PlayerObject {

    private Player player;
    private PlayerMap playerMap; //saves informations depending the map player is playing
    private PlayerPlayStats playerPlayStats; //all stats that player has
    private PlayerStatus playerStatus; //status of player (jumpmode/buildmode)


    public PlayerObject(Player player) {

        this.player = player;
        this.playerMap = new PlayerMap();
        this.playerPlayStats = new PlayerPlayStats();
        this.playerStatus = new PlayerStatus();

    }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) { this.player = player; }

    public PlayerMap getPlayerMap() { return playerMap; }

    public void setPlayerMap(PlayerMap playerMap) { this.playerMap = playerMap; }

    public PlayerPlayStats getPlayerPlayStats() { return playerPlayStats; }

    public void setPlayerPlayStats(PlayerPlayStats playerPlayStats) { this.playerPlayStats = playerPlayStats; }

    public PlayerStatus getPlayerStatus() { return playerStatus; }

    public void setPlayerStatus(PlayerStatus playerStatus) { this.playerStatus = playerStatus; }

}

