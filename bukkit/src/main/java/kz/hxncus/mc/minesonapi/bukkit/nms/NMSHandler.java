package kz.hxncus.mc.minesonapi.bukkit.nms;

import kz.hxncus.mc.minesonapi.api.bukkit.nms.NMSChunk;
import kz.hxncus.mc.minesonapi.utility.VersionUtil;

import java.lang.reflect.InvocationTargetException;

public enum NMSHandler {
	;
	public static NMSChunk chunk;
	
	public static NMSChunk getChunk() {
		if (chunk != null) {
			return chunk;
		}
		try {
			return (NMSChunk) Class.forName("kz.hxncus.mc.minesonapi.nms.Chunk_" + VersionUtil.NMS_VERSION)
			                       .getConstructor()
			                       .newInstance();
		} catch (final ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
		               IllegalAccessException |
		               InstantiationException e) {
			throw new RuntimeException(e);
		}
	}
}
