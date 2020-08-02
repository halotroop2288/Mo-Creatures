package com.halotroop.mocreatures.common.entity.ai.goals;

import com.halotroop.mocreatures.common.MoCMain;
import com.halotroop.mocreatures.common.MoCTools;
import com.halotroop.mocreatures.common.entity.MoCEntity;
import com.halotroop.mocreatures.common.entity.ai.RandomPositionGeneratorMoCFlyer;
import com.halotroop.mocreatures.common.entity.ambient.AntEntity;
import com.halotroop.mocreatures.common.entity.animal.MoCAnimalEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MoCWanderGoal2 extends Goal {
	private final AnimalEntity entity;
	private Vec3d position;
	private double speed;
	private int executionChance;
	private boolean mustUpdate;
	
	public MoCWanderGoal2(AnimalEntity creature, double speed) {
		this(creature, speed, 120);
	}
	
	public MoCWanderGoal2(AnimalEntity creature, double speed, int chance) {
		this.entity = creature;
		this.speed = speed;
		this.executionChance = chance;
		// set mutex bits
	}
	
	@Override
	public boolean canStart() {
		if (this.entity instanceof MoCEntity && ((MoCEntity) this.entity).isMovementCeased()) {
			return false;
		}
		if (this.entity.hasPassengers() && !(this.entity instanceof AntEntity || this.entity instanceof MoCEntity)) {
			return false;
		}
		
		if (!this.mustUpdate) {
			if (this.entity.age >= 100) {
//				MoCMain.logger.info("exiting path finder !mustUpdate + Age > 100" + this.entity);
				return false;
			}
			
			if (this.entity.getRandom().nextInt(this.executionChance) != 0) {
				MoCMain.logger.info(this.entity + "exiting due executionChance, age = " + this.entity.age
						+ ", executionChance = " + this.executionChance);
				return false;
			}
		}
		
		Vec3d vec3 = null;
		
		if (entity instanceof MoCAnimalEntity) {
			vec3 = RandomPositionGeneratorMoCFlyer.findRandomTarget((MoCAnimalEntity) this.entity, 10, 12);
			
			if (vec3 != null && this.entity.getNavigation() instanceof BirdNavigation) {
				int distToFloor = MoCTools.distanceToFloor(this.entity);
				int finalYHeight = distToFloor + MathHelper.floor(vec3.y - this.entity.y);
				if ((finalYHeight < ((MoCEntity) this.entity).minFlyingHeight())) {
					MoCMain.logger.info("vector height " + finalYHeight + " smaller than min flying height "
							+ ((MoCEntity) this.entity).minFlyingHeight());
					return false;
				}
				if ((finalYHeight > ((MoCEntity) this.entity).maxFlyingHeight())) {
					MoCMain.logger.info("vector height " + finalYHeight + " bigger than max flying height "
							+ ((MoCEntity) this.entity).maxFlyingHeight());
					return false;
				}
				
			}
		}
		
		if (vec3 == null) {
//			MoCMain.logger.info("exiting path finder null Vec3");
			return false;
		} else {
			MoCMain.logger.info("found vector " + vec3.x + ", " + vec3.y + ", " + vec3.z);
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
//		MoCMain.logger.info(entity + " has forced update");
		this.mustUpdate = true;
	}
	
	/**
	 * Changes task random possibility for execution
	 */
	public void setExecutionChance(int newChance) {
		this.executionChance = newChance;
	}
}
