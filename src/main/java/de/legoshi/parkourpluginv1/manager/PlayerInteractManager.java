package de.legoshi.parkourpluginv1.manager;

import de.legoshi.parkourpluginv1.listener.*;

public class PlayerInteractManager {

    PlayerNetherStarClick netherStarClick;
    PlayerStepPressureplate playerStepPressureplate;
    PlayerRedDyeClick redDyeClick;
    PlayerGrayDyeClick playerGrayDyeClick;
    PlayerCyanDyeClick playerCyanDyeClick;
    PlayerPickAxeClick playerPickAxeClick;
    PlayerChestClick playerChestClick;

    public PlayerInteractManager() {

        netherStarClick = new PlayerNetherStarClick();
        playerStepPressureplate = new PlayerStepPressureplate();
        redDyeClick = new PlayerRedDyeClick();
        playerGrayDyeClick = new PlayerGrayDyeClick();
        playerCyanDyeClick = new PlayerCyanDyeClick();
        playerPickAxeClick = new PlayerPickAxeClick();
        playerChestClick = new PlayerChestClick();

    }

    public PlayerNetherStarClick getNetherStarClick() { return netherStarClick; }

    public PlayerStepPressureplate getPlayerStepPressureplate() { return playerStepPressureplate; }

    public PlayerRedDyeClick getRedDyeClick() { return redDyeClick; }

    public PlayerGrayDyeClick getPlayerGrayDyeClick() { return playerGrayDyeClick; }

    public PlayerCyanDyeClick getPlayerCyanDyeClick() { return playerCyanDyeClick; }

    public PlayerPickAxeClick getPlayerPickAxeClick() { return playerPickAxeClick; }

    public PlayerChestClick getPlayerChestClick() { return playerChestClick; }
}
