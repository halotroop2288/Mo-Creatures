package org.mocreatures.mocreatures.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.mocreatures.mocreatures.common.registry.MoCTags;

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
	
	public static BlockPos ReturnNearestBlockPos(Entity entity, Block block, double range) {
		double shortestDistance = -1D;
		int x = -9999;
		int y = -1;
		int z = -1;
		
		Box box = entity.getBoundingBox().expand(range);
		int i = MathHelper.floor(box.x1);
		int j = MathHelper.floor(box.x2 + 1.0D);
		int k = MathHelper.floor(box.y1);
		int l = MathHelper.floor(box.y2 + 1.0D);
		int i1 = MathHelper.floor(box.z1);
		int j1 = MathHelper.floor(box.z2 + 1.0D);
		for (int k1 = i; k1 < j; k1++) {
			for (int l1 = k; l1 < l; l1++) {
				for (int i2 = i1; i2 < j1; i2++) {
					BlockPos pos = new BlockPos(k1, l1, i2);
					BlockState blockstate = entity.world.getBlockState(pos);
					if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getBlock() == block)) {
						double distance = entity.squaredDistanceTo(k1, l1, i2);
						if (shortestDistance == -1D) {
							x = k1;
							y = l1;
							z = i2;
							shortestDistance = distance;
						}
						
						if (distance < shortestDistance) {
							x = k1;
							y = l1;
							z = i2;
							shortestDistance = distance;
						}
					}
				}
			}
		}
		
		if (entity.x > x) {
			x -= 2;
		} else {
			x += 2;
		}
		if (entity.z > z) {
			z -= 2;
		} else {
			z += 2;
		}
		
		return new BlockPos(x, y, z);
	}
	
}
