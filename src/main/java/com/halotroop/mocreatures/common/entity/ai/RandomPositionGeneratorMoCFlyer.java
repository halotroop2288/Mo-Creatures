package com.halotroop.mocreatures.common.entity.ai;

import blue.endless.jankson.annotation.Nullable;
import com.halotroop.mocreatures.common.entity.animal.MoCAnimalEntity;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class RandomPositionGeneratorMoCFlyer {
	/**
	 * used to store a direction when the user passes a point to move towards or away from. WARNING: NEVER THREAD SAFE.
	 * MULTIPLE findTowards and findAway calls, will share this var
	 */
	private static Vec3d staticVector = Vec3d.ZERO;
	
	/**
	 * finds a random target within par1(x,z) and par2 (y) blocks
	 */
	@Nullable
	public static Vec3d findRandomTarget(MoCAnimalEntity creature, int xz, int y) {
		return findRandomTargetBlock(creature, xz, y, null);
	}
	
	/**
	 * finds a random target within par1(x,z) and par2 (y) blocks in the direction of the point par3
	 */
	@Nullable
	public static Vec3d findRandomTargetBlockTowards(MoCAnimalEntity creature, int xz, int y, Vec3d targetVec3) {
		staticVector = targetVec3.subtract(creature.x, creature.y, creature.z);
		return findRandomTargetBlock(creature, xz, y, staticVector);
	}
	
	
	/**
	 * finds a random target within par1(x,z) and par2 (y) blocks in the reverse direction of the point par3
	 */
	@Nullable
	public static Vec3d findRandomTargetBlockAwayFrom(MoCAnimalEntity creature, int xz, int y, Vec3d targetVec3) {
		staticVector = (new Vec3d(creature.x, creature.y, creature.z)).subtract(targetVec3);
		return findRandomTargetBlock(creature, xz, y, staticVector);
	}
	
	/**
	 * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction
	 * of par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
	 */
	@Nullable
	private static Vec3d findRandomTargetBlock(MoCAnimalEntity creature, int xz, int y, @Nullable Vec3d targetVec3) {
		EntityNavigation navigator = creature.getNavigation();
		Random random = creature.getRandom();
		boolean flag = false;
		int i = 0;
		int j = 0;
		int k = 0;
		float f = -99999.0F;
		boolean flag1;
		
		if (creature.hasHome()) {
			double d0 = creature.getHomePosition().getSquaredDistance(
					MathHelper.floor(creature.x),
					MathHelper.floor(creature.y),
					MathHelper.floor(creature.z), false) + 4.0D;
			double d1 = creature.getMaximumHomeDistance() + (float) xz;
			flag1 = d0 < d1 * d1;
		} else {
			flag1 = false;
		}
		
		for (int j1 = 0; j1 < 10; ++j1) {
			int l = random.nextInt(2 * xz + 1) - xz;
			int k1 = random.nextInt(2 * y + 1) - y;
			int i1 = random.nextInt(2 * xz + 1) - xz;
			
			if (targetVec3 == null || (double) l * targetVec3.x + (double) i1 * targetVec3.z >= 0.0D) {
				if (creature.hasHome() && xz > 1) {
					BlockPos bpos = creature.getHomePosition();
					
					if (creature.x > (double) bpos.getX()) {
						l -= random.nextInt(xz / 2);
					} else {
						l += random.nextInt(xz / 2);
					}
					
					if (creature.z > (double) bpos.getZ()) {
						i1 -= random.nextInt(xz / 2);
					} else {
						i1 += random.nextInt(xz / 2);
					}
				}
				
				BlockPos bpos1 = new BlockPos((double) l + creature.x, (double) k1 + creature.y, (double) i1 + creature.z);
				
				if ((!flag1 || creature.positionIsWithinHomeDistance(bpos1)))/* && navigator.canEntityStandOnPos(bpos1))*/ {
					float f1 = creature.getPathfindingFavor(bpos1);
					
					if (f1 > f) {
						f = f1;
						i = l;
						j = k1;
						k = i1;
						flag = true;
					}
				}
			}
		}
		
		if (flag) {
			return new Vec3d((double) i + creature.x, (double) j + creature.y, (double) k + creature.z);
		} else {
			return null;
		}
	}
}
