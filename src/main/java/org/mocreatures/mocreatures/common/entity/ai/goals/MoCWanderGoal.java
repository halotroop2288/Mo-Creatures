package org.mocreatures.mocreatures.common.entity.ai.goals;

import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.mocreatures.mocreatures.common.MoCMain;
import org.mocreatures.mocreatures.common.MoCTools;
import org.mocreatures.mocreatures.common.entity.MoCreature;
import org.mocreatures.mocreatures.common.entity.ai.MoCFlyerRandomPositionGenerator;

public class MoCWanderGoal extends WanderAroundGoal {
	private final MobEntityWithAi entity;
	private Vec3d position;
	private double speed;
	private int executionChance;
	private boolean mustUpdate;
	
	public MoCWanderGoal(MobEntityWithAi creature, double speed) {
		this(creature, speed, 120);
	}
	
	public MoCWanderGoal(MobEntityWithAi creature, double speed, int chance) {
		super(creature, speed, chance);
		this.entity = creature;
		this.speed = speed;
		this.executionChance = chance;
		// set mutex bits to 1
	}
	
	@Override
	public boolean canStart() {
		if (this.entity.getNavigation().isIdle()) {
			return false;
		}
		if (this.entity.hasPassengers() && !(this.entity instanceof MoCreature)) {
			return false;
		}
		
		if (!this.mustUpdate) {
			if (this.entity.age >= 100) {
//				MoCMain.logger.devInfo("exiting path finder !mustUpdate + Age > 100" + this.entity);
				return false;
			}
			
			if (this.entity.getRandom().nextInt(this.executionChance) != 0) {
//				MoCMain.logger.devInfo(this.entity + "exiting due executionChance, age = " + this.entity.age
//						+ ", executionChance = " + this.executionChance);
				return false;
			}
		}
		
		Vec3d vec3 = null;
		
		if (entity.canFly()) {
			vec3 = MoCFlyerRandomPositionGenerator.findRandomTarget(this.entity, 10, 12);
			
			if (vec3 != null && this.entity.getNavigation() instanceof BirdNavigation) {
				int distToFloor = MoCTools.distanceToFloor(this.entity);
				int finalYHeight = distToFloor + MathHelper.floor(vec3.y - this.entity.y);
				if ((finalYHeight < ((MoCreature.Flying) this.entity).minFlyingHeight())) {
					MoCMain.logger.devInfo("vector height " + finalYHeight + " smaller than min flying height "
							+ ((MoCreature.Flying) this.entity).minFlyingHeight());
					return false;
				}
				if ((finalYHeight > ((MoCreature.Flying) this.entity).maxFlyingHeight())) {
					MoCMain.logger.devInfo("vector height " + finalYHeight + " bigger than max flying height "
							+ ((MoCreature.Flying) this.entity).maxFlyingHeight());
					return false;
				}
			}
		}
		
		if (vec3 == null) {
			MoCMain.logger.devInfo("exiting path finder null Vec3");
			return false;
		} else {
			MoCMain.logger.devInfo("found vector " + vec3.x + ", " + vec3.y + ", " + vec3.z);
			this.position = vec3;
			this.mustUpdate = false;
			return true;
		}
	}
	
	@Override
	public boolean shouldContinue() {
		return !this.entity.getNavigation().isIdle() && !entity.hasPassengers();
	}
	
	@Override
	public void start() {
		this.entity.getNavigation().startMovingTo(position.x, position.y, position.z, this.speed);
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	/**
	 * Makes task to bypass chance
	 */
	public void makeUpdate() {
		this.mustUpdate = true;
	}
	
	/**
	 * Changes task random possibility for execution
	 */
	public void setExecutionChance(int newChance) {
		this.executionChance = newChance;
	}
}
