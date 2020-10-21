package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.listener.*;
import de.legoshi.parkourpluginv1.listener.cancellistener.PlayerChestClick;
import de.legoshi.parkourpluginv1.listener.itemclicks.*;
import org.bukkit.entity.Player;

public class PlayerInteractManager {

    PlayerNetherStarClick netherStarClick;
    PlayerStepPressureplate playerStepPressureplate;
    PlayerRedDyeClick redDyeClick;
    PlayerGrayDyeClick playerGrayDyeClick;
    PlayerCyanDyeClick playerCyanDyeClick;
    PlayerPickAxeClick playerPickAxeClick;
    PlayerChestClick playerChestClick;
    PlayerTDClick playerTDClick;
    PlayerGlowstoneClick playerGlowstoneClick;
    PlayerWoolClick playerWoolClick;
    PlayerFarmlandStep playerFarmlandStep;

    public PlayerInteractManager() {

        netherStarClick = new PlayerNetherStarClick();
        playerStepPressureplate = new PlayerStepPressureplate();
        redDyeClick = new PlayerRedDyeClick();
        playerGrayDyeClick = new PlayerGrayDyeClick();
        playerCyanDyeClick = new PlayerCyanDyeClick();
        playerPickAxeClick = new PlayerPickAxeClick();
        playerChestClick = new PlayerChestClick();
        playerTDClick = new PlayerTDClick();
        playerGlowstoneClick = new PlayerGlowstoneClick();
        playerWoolClick = new PlayerWoolClick();
        playerFarmlandStep = new PlayerFarmlandStep();

    }

    public PlayerFarmlandStep getPlayerFarmlandStep() {return playerFarmlandStep; }

    public PlayerWoolClick getPlayerWoolClick() { return playerWoolClick; }

    public PlayerGlowstoneClick getPlayerGlowstoneClick() { return playerGlowstoneClick; }

    public PlayerTDClick getPlayerTDClick() { return playerTDClick; }

    public PlayerNetherStarClick getNetherStarClick() { return netherStarClick; }

    public PlayerStepPressureplate getPlayerStepPressureplate() { return playerStepPressureplate; }

    public PlayerRedDyeClick getRedDyeClick() { return redDyeClick; }

    public PlayerGrayDyeClick getPlayerGrayDyeClick() { return playerGrayDyeClick; }

    public PlayerCyanDyeClick getPlayerCyanDyeClick() { return playerCyanDyeClick; }

    public PlayerPickAxeClick getPlayerPickAxeClick() { return playerPickAxeClick; }

    public PlayerChestClick getPlayerChestClick() { return playerChestClick; }
}
