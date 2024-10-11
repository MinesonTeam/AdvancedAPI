package kz.hxncus.mc.advancedapi.bukkit.world;

import kz.hxncus.mc.advancedapi.utility.FunctionalUtil;
import kz.hxncus.mc.advancedapi.utility.tuples.Pair;
import kz.hxncus.mc.advancedapi.utility.tuples.Triplet;
import lombok.Builder;
import lombok.ToString;
import org.bukkit.Difficulty;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.metadata.MetadataValue;

import java.util.Map;

/**
 * Class World settings.
 *
 * @author Hxncus
 * @since 1.0.0
 */
@Builder
@ToString
public class WorldSettings {
	private Boolean autoSave;
	private Map<Triplet<Integer, Integer, Integer>, Biome> tripletBiomeMap;
	private Map<Triplet<Integer, Integer, Integer>, BlockData> tripletBlockDataMap;
	private Map<Pair<Integer, Integer>, Boolean> chunkBoolMap;
	private Integer clearWeatherDuration;
	private Difficulty difficulty;
	private Long fullTime;
	private Boolean hardcore;
	private Boolean keepSpawnInMemory;
	private Map<String, MetadataValue> strMetadataValMap;
	private Boolean storm;
	private Long time;
	
	/**
	 * Apply simple world.
	 *
	 * @param world the world
	 * @return the simple world
	 */
	public SimpleWorld apply(final SimpleWorld world) {
		FunctionalUtil.ifFalseAccept(() -> this.autoSave == null, worlds -> worlds.setAutoSave(this.autoSave), world);
		FunctionalUtil.ifFalseAccept(() -> this.tripletBiomeMap == null, consumerWorld -> {
			for (final Map.Entry<Triplet<Integer, Integer, Integer>, Biome> entry : this.tripletBiomeMap.entrySet()) {
				final Triplet<Integer, Integer, Integer> key = entry.getKey();
				consumerWorld.setBiome(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue());
			}
		}, world);
//		FunctionalUtil.acceptIfTrue(() -> this.tripletBlockDataMap != null, consumerWorld -> {
//			for (final Map.Entry<Triplet<Integer, Integer, Integer>, BlockData> entry : this.tripletBlockDataMap.entrySet()) {
//				final Triplet<Integer, Integer, Integer> key = entry.getKey();
//				consumerWorld.setBlockData(key.getLeft(), key.getMiddle(), key.getRight(), entry.getValue());
//			}
//		}, world);
//		FunctionalUtil.acceptIfTrue(() -> this.chunkBoolMap != null, consumerWorld -> {
//			for (final Map.Entry<Pair<Integer, Integer>, Boolean> entry : this.chunkBoolMap.entrySet()) {
//				final Pair<Integer, Integer> key = entry.getKey();
//				consumerWorld.setChunkForceLoaded(key.getLeft(), key.getRight(), entry.getValue());
//			}
//		}, world);
//		FunctionalUtil.acceptIfTrue(() -> this.clearWeatherDuration != null, consumerWorld -> consumerWorld.setClearWeatherDuration(this.clearWeatherDuration), world);
//		FunctionalUtil.acceptIfTrue(() -> this.difficulty != null, consumerWorld -> consumerWorld.setDifficulty(this.difficulty), world);
//		FunctionalUtil.acceptIfTrue(() -> this.fullTime != null, consumerWorld -> consumerWorld.setFullTime(this.fullTime), world);
//		FunctionalUtil.acceptIfTrue(() -> this.hardcore != null, consumerWorld -> consumerWorld.setHardcore(this.hardcore), world);
//		FunctionalUtil.acceptIfTrue(() -> this.storm != null, consumerWorld -> consumerWorld.setStorm(this.storm), world);
		return world;
	}
}
