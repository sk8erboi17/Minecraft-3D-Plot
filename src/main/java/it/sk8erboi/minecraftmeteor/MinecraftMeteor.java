package it.sk8erboi.minecraftmeteor;

import it.sk8erboi.minecraftmeteor.commands.SphereCommands;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftMeteor extends JavaPlugin {

    private static MinecraftMeteor instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("3d").setExecutor(new SphereCommands());
    }

    public static MinecraftMeteor getInstance() {
        return instance;
    }
}
