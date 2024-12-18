package kz.hxncus.mc.advancedapi.utility;

import kz.hxncus.mc.advancedapi.bukkit.generator.EmptyChunkGenerator;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@UtilityClass
public final class WorldUtil {
	public World getOrCreateWorld(final WorldCreator worldCreator) {
		String worldName = worldCreator.name();
		if (WorldUtil.copyEmptyWorld(worldName)) {
			return worldCreator.createWorld();
		}
		return Bukkit.getWorld(worldName);
	}
	
	public boolean copyWorld(final File source, final File target) {
		if (!target.exists()) {
			target.mkdirs();
		}
		
		for (File file : source.listFiles()) {
			File newFile = new File(target, file.getName());
			String fileName = file.getName();
			if (fileName.equals("session.lock") || fileName.equals("uid.dat")) {
				continue;
			}
			if (file.isDirectory()) {
				WorldUtil.copyWorld(file, newFile);
			} else {
				try {
					InputStream in = new FileInputStream(file);
                    OutputStream out = new FileOutputStream(newFile);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
					in.close();
					out.close();
				} catch (IOException ignored) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean copyEmptyWorld(final String worldName) {
		File emptyWorldDir = WorldUtil.getOrCreateEmptyWorld();
		if (emptyWorldDir == null) {
			return false;
		}
		
		File targetFile = new File(Bukkit.getWorldContainer(), worldName);
		if (targetFile.exists()) {
			return true;
		}

		return copyWorld(emptyWorldDir, targetFile);
	}
	
	public File getOrCreateEmptyWorld() {
		File emptyWorldDir = new File(Bukkit.getWorldContainer(), "to_copy");
		if (emptyWorldDir.exists()) {
			return emptyWorldDir;
		}
		World toCopyWorld = new WorldCreator("to_copy")
			.environment(Environment.NORMAL)
			.keepSpawnInMemory(false)
			.generator(EmptyChunkGenerator.getInstance())
			.generateStructures(false)
			.createWorld();
		toCopyWorld.setStorm(false);
		toCopyWorld.setThundering(false);
		toCopyWorld.setSpawnFlags(false, false);
		toCopyWorld.setDifficulty(Difficulty.PEACEFUL);
		toCopyWorld.setKeepSpawnInMemory(false);
		toCopyWorld.setAutoSave(false);

		toCopyWorld.setGameRule(GameRule.KEEP_INVENTORY, false);
        toCopyWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        toCopyWorld.setGameRule(GameRule.DO_FIRE_TICK, false);
		toCopyWorld.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);

		return emptyWorldDir;
	}
}
