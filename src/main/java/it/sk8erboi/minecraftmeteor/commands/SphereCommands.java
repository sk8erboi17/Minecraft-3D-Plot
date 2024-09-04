package it.sk8erboi.minecraftmeteor.commands;

import it.sk8erboi.minecraftmeteor.MinecraftMeteor;
import it.sk8erboi.minecraftmeteor.utils.MathUtils;
import it.sk8erboi.minecraftmeteor.utils.NMSUtils;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SphereCommands implements CommandExecutor {
    private final AtomicInteger xReference = new AtomicInteger();  // Reference for x-coordinate offset
    private final AtomicInteger yReference = new AtomicInteger();  // Reference for y-coordinate offset
    private final AtomicInteger zReference = new AtomicInteger();  // Reference for z-coordinate offset

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        Player player = (Player) sender;

        if(args.length == 0){
            player.sendMessage("/3d spawn");
            player.sendMessage("/3d <x> <y> <z>");
            return true;
        }

        // Handle setting of reference coordinates
        if(args.length == 3){
            try{
                xReference.set(Integer.parseInt(args[0]));
                yReference.set(Integer.parseInt(args[1]));
                zReference.set(Integer.parseInt(args[2]));
                player.sendMessage("New coordinates:");
                player.sendMessage("x: " + xReference.get());
                player.sendMessage("y: " + yReference.get());
                player.sendMessage("z: " + zReference.get());
            }catch (NumberFormatException e){
                player.sendMessage("Invalid numbers!");
            }

            return true;
        }

        // Handle the "spawn" command
        if (args[0].equalsIgnoreCase("spawn")) {
            Location origin = player.getLocation();  // Get player's current location as the origin

            // Create offset locations for drawing the cartesian graphic
            Location y = player.getLocation().clone();
            y.setY(y.getY() + 50);

            Location x = player.getLocation().clone();
            x.setX(x.getX() + 50);

            Location z = player.getLocation().clone();
            z.setZ(z.getZ() + 50);

            AtomicReference<Location> finalLoc = new AtomicReference<>(origin.clone());

            // Run an asynchronous task to spawn particles
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Create particle lines extending from the origin
                    MathUtils.createParticleLine(player, origin, x, 1, EnumParticle.CLOUD);
                    MathUtils.createParticleLine(player, origin, y, 1, EnumParticle.CLOUD);
                    MathUtils.createParticleLine(player, origin, z, 1, EnumParticle.CLOUD);
                    //plot the pointer
                    finalLoc.set(origin.clone().add(xReference.get(),yReference.get(),zReference.get()));
                    MathUtils.createParticleLine(player, origin, finalLoc.get(), 1, EnumParticle.SMOKE_LARGE);
                }
            }.runTaskTimerAsynchronously(MinecraftMeteor.getInstance(), 0, 1);  // Schedule the task
            return true;
        }
        return false;
    }
}
