package de.legoshi.parkourpluginv1.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FW {

    private File f;
    private YamlConfiguration c;

    public FW(String FilePath, String FileName) {

        this.f = new File(FilePath, FileName);
        this.c = YamlConfiguration.loadConfiguration(this.f);

    }

    public FW setValue(String ValuePath, Object Value) {

        c.set(ValuePath, Value);
        return this;

    }

    public FW setLocation(Location location, int num) {

        c.set(num + ".world", location.getWorld().getName());
        c.set(num + ".x", location.getX());
        c.set(num + ".y", location.getY());
        c.set(num + ".z", location.getZ());
        c.set(num + ".yaw", location.getYaw());
        c.set(num + ".pitch", location.getPitch());

        return this;

    }

    public Location getLocation(Player player, int pos) {

        World world = Bukkit.getServer().getWorld(c.getString(pos + ".world") + "_" + player.getName());
        double x = c.getDouble(pos + ".x");
        double y = c.getDouble(pos + ".y");
        double z = c.getDouble(pos + ".z");
        float yaw = (float) c.getDouble(pos + ".yaw");
        float pitch = (float) c.getDouble(pos + ".pitch");

        Location location = new Location(world, x, y, z, yaw, pitch);

        return location;

    }

    public boolean exist() {
        return f.exists();
    }

    public int getInt(String ValuePath) {
        return c.getInt(ValuePath);
    }

    public String getString(String ValuePath) {
        return c.getString(ValuePath);
    }

    public boolean getBoolean(String ValuePath) {
        return c.getBoolean(ValuePath);
    }

    public long getLong(String ValuePath) {
        return c.getLong(ValuePath);
    }

    public List<String> getStringList(String ValuePath) {
        return c.getStringList(ValuePath);
    }

    public Set<String> getKeys(boolean deep) {
        return c.getKeys(deep);
    }

    public ConfigurationSection getConfigurationSection(String Section) {
        return c.getConfigurationSection(Section);
    }

    public FW save() {

        try { this.c.save(this.f); }
        catch (IOException ignored) {}

        return this;

    }

}
