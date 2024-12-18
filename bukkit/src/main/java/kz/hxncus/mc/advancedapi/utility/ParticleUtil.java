package kz.hxncus.mc.advancedapi.utility;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ParticleUtil {
    public void drawLine(Location start, Location end, Particle particle, double space) {
        double distance = start.distance(end);
        Vector direction = end.toVector().subtract(start.toVector()).normalize();
        
        for (double i = 0; i < distance; i += space) {
            direction.multiply(i);
            start.add(direction);
            start.getWorld().spawnParticle(particle, start, 1, 0, 0, 0, 0);
            start.subtract(direction);
            direction.normalize();
        }
    }

    public void drawCircle(Location center, double radius, Particle particle) {
        for (double t = 0; t < Math.PI * 2; t += 0.1) {
            double x = radius * Math.cos(t);
            double z = radius * Math.sin(t);
            center.getWorld().spawnParticle(
                particle,
                center.clone().add(x, 0, z),
                1, 0, 0, 0, 0
            );
        }
    }
}
