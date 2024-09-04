package it.sk8erboi.minecraftmeteor.utils;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSUtils {

    //use particle for drawing
    public static void sendParticle(Player player, EnumParticle particle, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                particle, true,
                (float) x, (float) y, (float) z,
                offsetX, offsetY, offsetZ,
                speed, count
        );
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }


}
