package com.halotroop.mocreatures.common;

import com.halotroop.mocreatures.common.registry.MoCTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class MoCTools {
	public static int distanceToFloor(Entity entity) {
		int i = MathHelper.floor(entity.x);
		int j = MathHelper.floor(entity.y);
		int k = MathHelper.floor(entity.z);
		for (int x = 0; x < 64; x++) {
			Block block = entity.world.getBlockState(new BlockPos(i, j - x, k)).getBlock();
			if (block != Blocks.AIR) return x;
		}
		
		return 0;
	}
	
	public static ItemEntity getClosestFood(Entity entity, double d) {
		double d1 = -1D;
		ItemEntity itemEntity = null;
		List<Entity> list = entity.world.getEntities(entity, entity.getBoundingBox().expand(d, d, d));
		for (Entity value : list) {
			if (!(value instanceof ItemEntity)) {
				continue;
			}
			ItemEntity itemEntity1 = (ItemEntity) value;
			if (!isItemEdibleForHerbivores(itemEntity1.getStack().getItem())) {
				continue;
			}
			double d2 = itemEntity1.squaredDistanceTo(entity.x, entity.y, entity.z);
			if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
				d1 = d2;
				itemEntity = itemEntity1;
			}
		}
		return itemEntity;
	}
	
	public static boolean isItemEdibleForHerbivores(Item item) {
		return ((item.getFoodComponent() != null && !item.getFoodComponent().isMeat()) // Covers all non-meat foods
				|| MoCTags.ItemTag.HERBIVORE_FOOD.tag.contains(item)); // Should cover all non-human-edible herbivore foods (as long as the modpack author adds everything applicable to the tag).
	}
	
	public static boolean isItemEdibleForCarnivores(Item item) {
		return (item.getFoodComponent() != null && item.getFoodComponent().isMeat() // Covers all meats
				|| MoCTags.ItemTag.CARNIVORE_FOOD.tag.contains(item)); // Should cover all non-human-edible carnivore foods (as long as the modpack author adds everything applicable to the tag).
	}
	
	public static String biomeName(World world, BlockPos pos) {
		Biome Biome = world.getBiome(pos);
		// TODO works?
		
		if (Biome != null)
			return Biome.getName().asString();
		else {
			MoCMain.logger.error("Biome is null! Can't get biome name with MoCTools!");
			return "";
		}
	}
}
