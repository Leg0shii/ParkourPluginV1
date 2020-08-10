package de.legoshi.parkourpluginv1;

import de.legoshi.parkourpluginv1.commands.*;
import de.legoshi.parkourpluginv1.listener.*;
import de.legoshi.parkourpluginv1.manager.*;
import de.legoshi.parkourpluginv1.util.*;
import org.bukkit.Bukkit;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    //Im KingBranBran
    public static Main instance;
    public AsyncMySQL mySQL;
    public MySQLManager mySQLManager;
    public PlayerManager playerManager;
    public CheckpointManager checkpointManager;
    public MapObjectMananger mapObjectMananger;
    public Inventory inventory;
    public PlayerInteractManager playerInteractManager;
    public UpdateSchedular updateSchedular;
    public ScoreboardHelper scoreboardHelper;

    @Override
    public void onEnable() {

        //testcomment

        initializeMethods();
        this.mySQL = mySQLManager.initializeTables();
        mapObjectMananger.getAllMapsFromDB();

        updateSchedular.onRun();

        ListenerRegistration();
        CommandRegistration();

    }

    @Override
    public void onDisable() {

        mySQLManager.savingAllPlayerDataToDB();

    }

    private void CommandRegistration() {

        getCommand("createRankedMap").setExecutor(new CreateRankedMap());
        getCommand("ppstats").setExecutor(new PPStatsCommand());
        getCommand("pptop").setExecutor(new PPTopCommand());
        getCommand("maptop").setExecutor(new MapTopCommand());
        getCommand("bestpp").setExecutor(new PPPlaysCommand());

    }

    private void ListenerRegistration() {

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new BlockDestroyListener(), this);
        pm.registerEvents(new ItemDropListener(), this);
        pm.registerEvents(new PlayerDamageListener(), this);

        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new QuitListener(), this);

        pm.registerEvents(new PlayerInteractListener(), this);

        pm.registerEvents(new RedDyeInventoryClick(), this);
        pm.registerEvents(new InventoryClickListener(), this);

    }

    private void reload() {

    }

    public static Main getInstance() {
        return instance;
    }

    public void initializeMethods() {

        instance = this;
        playerManager = new PlayerManager();
        checkpointManager = new CheckpointManager();
        mapObjectMananger = new MapObjectMananger();
        inventory = new Inventory();
        playerInteractManager = new PlayerInteractManager();
        updateSchedular = new UpdateSchedular();
        scoreboardHelper = new ScoreboardHelper();
        mySQLManager = new MySQLManager();


    }

}
