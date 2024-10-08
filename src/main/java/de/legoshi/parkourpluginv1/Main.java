package de.legoshi.parkourpluginv1;

import de.legoshi.parkourpluginv1.commands.*;
import de.legoshi.parkourpluginv1.commands.insidemapcommand.SetSpawn;
import de.legoshi.parkourpluginv1.commands.insidemapcommand.UpdateMapPPCommand;
import de.legoshi.parkourpluginv1.gui.MapCreateGUI;
import de.legoshi.parkourpluginv1.gui.MapEditGUI;
import de.legoshi.parkourpluginv1.gui.MapSelectGUI;
import de.legoshi.parkourpluginv1.listener.*;
import de.legoshi.parkourpluginv1.listener.cancellistener.*;
import de.legoshi.parkourpluginv1.manager.*;
import de.legoshi.parkourpluginv1.util.*;
import de.legoshi.parkourpluginv1.util.fakeplayer.NPC;
import org.bukkit.Bukkit;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    public static Main instance;
    private NPC npc;
    public AsyncMySQL mySQL;
    public MySQLManager mySQLManager;
    public PlayerManager playerManager;
    public CheckpointManager checkpointManager;
    public MapObjectMananger mapObjectMananger;
    public Inventory inventory;
    public PlayerInteractManager playerInteractManager;
    public UpdateSchedular updateSchedular;
    public ScoreboardHelper scoreboardHelper;
    public PlayerTag playerTag;
    public TabTagCreator tabTagCreator;
    public TitelManager titelManager;
    public PlayerStepPressureplate playerStepPressureplate;
    public WorldSaver worldSaver;
    public MapSelectGUI mapSelectGUI;
    public MapCreateGUI buildCreateGUI;
    public MapEditGUI mapEditGUI;
    public PerformanceCalculator performanceCalculator;
    public ItemCreator itemCreator;
    public Replay replay;

    public Location spawn;
    public String spawnName;
    public Location standardSpawn;

    @Override
    public void onEnable() {

        initializeMethods();

        this.mySQL = mySQLManager.initializeTables(); //Initializes all required sql tables

        updateSchedular.onRun(); //updates playertime every 30min
        mySQLManager.keepMySQLbusy(); //so DB doesnt loose connection

        createFolders();

        ListenerRegistration();
        CommandRegistration();

    }

    @Override
    public void onDisable() {

        mySQLManager.savingAllPlayerDataToDB();

        for(Player all : Bukkit.getOnlinePlayers()) {

            World playerWorld = all.getWorld();

            if(!playerWorld.getName().equals("world")) {

                try {
                    instance.worldSaver.moveAllPlayer(playerWorld);
                    instance.worldSaver.zipWorld(playerWorld);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private void CommandRegistration() {

        getCommand("ppstats").setExecutor(new PPStatsCommand());
        getCommand("pptop").setExecutor(new PPTopCommand());
        getCommand("pptopmap").setExecutor(new MapTopCommand());
        getCommand("ppbest").setExecutor(new PPBestCommand());
        getCommand("pphelp").setExecutor(new HelpCommand());
        getCommand("ppmap").setExecutor(new PPMapCommand());
        getCommand("invite").setExecutor(new MapInviteCommand());
        getCommand("accept").setExecutor(new MapAcceptCommand());
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("ppupdate").setExecutor(new UpdateMapPPCommand());
        getCommand("nv").setExecutor(new NightVisionCommand());
        getCommand("replay").setExecutor(new ReplayCommand());

    }

    private void ListenerRegistration() {

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new EntitySpawnListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new BlockDestroyListener(), this);
        pm.registerEvents(new RedstoneListener(), this);
        pm.registerEvents(new PortalCreateListener(), this);
        pm.registerEvents(new ItemDropListener(), this);
        pm.registerEvents(new PlayerDamageListener(), this);
        pm.registerEvents(new MinecartListener(), this);
        pm.registerEvents(new LeafDecayListener(), this);
        pm.registerEvents(new BlockGrowListener(), this);
        pm.registerEvents(new LiquidSpreadListener(), this);
        pm.registerEvents(new IceMeltListener(), this);

        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new QuitListener(), this);

        pm.registerEvents(new PlayerInteractListener(), this);

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
        playerTag = new PlayerTag();
        tabTagCreator = new TabTagCreator();
        titelManager = new TitelManager();
        playerStepPressureplate = new PlayerStepPressureplate();
        worldSaver = new WorldSaver();
        mapSelectGUI = new MapSelectGUI();
        mapEditGUI = new MapEditGUI();
        buildCreateGUI = new MapCreateGUI();
        performanceCalculator = new PerformanceCalculator();
        itemCreator = new ItemCreator();
        replay = new Replay();
        spawn = new Location(Bukkit.getWorld("world"), -619, 5, 10, -160, 5);
        standardSpawn = new Location(null, 8.5, 4, 8.5);
        spawnName = "world";

    }

    public void createFolders() {

        File pkbfile = new File("./ParkourBuild");
        if(pkbfile.mkdir()) { Bukkit.getConsoleSender().sendMessage("Successfully created Folder: ParkourBuild"); }
        else { Bukkit.getConsoleSender().sendMessage("Folder: ParkourBuild Already exist"); }

        File pkmapsfile = new File("./ParkourMapsPlayers");
        if(pkmapsfile.mkdir()) { Bukkit.getConsoleSender().sendMessage("Successfully created Folder: ParkourMapsPlayers"); }
        else { Bukkit.getConsoleSender().sendMessage("Folder: ParkourMapsPlayers Already exist"); }

        File replayfolder = new File("./ParkourReplays");
        if(replayfolder.mkdir()) { Bukkit.getConsoleSender().sendMessage("Successfully created Folder: ParkourReplays"); }
        else { Bukkit.getConsoleSender().sendMessage("Folder: ParkourReplays Already exist"); }

    }

}
