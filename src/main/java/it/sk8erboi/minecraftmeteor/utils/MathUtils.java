package it.sk8erboi.minecraftmeteor.utils;

import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MathUtils {

    //calculate distance between two points
    public static void createParticleLine(Player player, Location pointA, Location pointB, double particleSpacing, EnumParticle... particleEffect) {
        Vector direction = pointB.toVector().subtract(pointA.toVector()).normalize();
        double distance = pointA.distance(pointB);
        int particles = (int) (distance / particleSpacing);

        for (int i = 0; i < particles; i++) {
            Location location = pointA.clone().add(direction.clone().multiply(particleSpacing * i));
            for (EnumParticle enumParticle : particleEffect) {
                NMSUtils.sendParticle(player,enumParticle,location.getX(),location.getY(),location.getZ(),0,0,0,0,0);
            }
        }
    }
}
