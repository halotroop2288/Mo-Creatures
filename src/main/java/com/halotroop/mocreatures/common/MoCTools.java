package com.halotroop.mocreatures.common;

import com.halotroop.mocreatures.common.registry.MoCItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class MoCTools {
	public static int distanceToFloor(AnimalEntity entity) {
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
			if (!isItemEdible(itemEntity1.getStack().getItem())) {
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
	
	
	public static boolean isItemEdible(Item item) {
//		return (item instanceof ItemFood) || (item instanceof ItemSeeds) || item == Items.WHEAT || item == Items.SUGAR
//				|| item == Items.CAKE || item == Item.EGG;
		return (item.isFood() || MoCItemTags.HERBIVORE_FOOD.contains(item));
	}
	
	public static boolean isItemEdibleForCarnivores(Item item) {
//		return item == Items.BEEF || item == Items.CHICKEN || item == Items.COOKED_BEEF
//				|| item == Items.COOKED_CHICKEN || item == Items.COOKED_COD || item == Items.COOKED_SALMON
//				|| item == Items.RABBIT || item == Items.COOKED_MUTTON || item == Items.COOKED_PORKCHOP
//				|| item == Items.MUTTON || item == Items.COOKED_RABBIT || item == Items.COD || item == Items.SALMON
//				|| item == Items.PORKCHOP;
		return (item.isFood() || MoCItemTags.CARNIVORE_FOOD.contains(item));
	}
}
