package com.halotroop.mocreatures.common.entity.ambient;

import com.halotroop.mocreatures.common.MoCMain;
import com.halotroop.mocreatures.common.MoCTools;
import com.halotroop.mocreatures.common.entity.MoCreature;
import com.halotroop.mocreatures.common.entity.ai.goals.MoCWanderGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class InsectEntity extends AnimalEntity {
	private int climbCounter;
	
	protected InsectEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static void faceLocation(MobEntity entity, int i, int j, int k, float maxIncrement) {
		// TODO: Correctly label math-y stuff
		double var1 = i + 0.5D - entity.x;
		double var2 = k + 0.5D - entity.z;
		double var3 = j + 0.5D - entity.y;
		double var4 = MathHelper.sqrt(var1 * var1 + var2 * var2);
		float yawModifier = (float) (Math.atan2(var2, var1) * 180.0D / Math.PI) - 90.0F;
		float pitchModifier = (float) (-(Math.atan2(var3, var4) * 180.0D / Math.PI));
		entity.pitch = -updateRotation(entity.pitch, pitchModifier, maxIncrement);
		entity.yaw = updateRotation(entity.yaw, yawModifier, maxIncrement);
	}
	
	private static float updateRotation(float currentRotation, float intendedRotation, float maxIncrement) {
		float rotationModifier = intendedRotation - currentRotation;
		
		while (rotationModifier < -180.0F) rotationModifier += 360.0F;
		while (rotationModifier >= 180.0F) rotationModifier -= 360.0F;
		
		if (rotationModifier > maxIncrement) rotationModifier = maxIncrement;
		if (rotationModifier < -maxIncrement) rotationModifier = -maxIncrement;
		
		return currentRotation + rotationModifier;
	}
	
	@Override
	public boolean canBeLeashedBy(PlayerEntity playerEntity) {
		return false;
	}
	
	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(5, new MoCWanderGoal(this, this.getMovementSpeed()));
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(4.0D);
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(0.25D);
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
	}
	
	public abstract static class Flying extends InsectEntity implements MoCreature.Flying {
		private static final TrackedData<Boolean> IS_FLYING;
		
		static {
			IS_FLYING = DataTracker.registerData(InsectEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		}
		
		protected Flying(EntityType<? extends AnimalEntity> entityType, World world) {
			super(entityType, world);
		}
		
		public boolean isFlying() {
			return this.dataTracker.get(IS_FLYING);
		}
		
		public void setFlying(boolean flag) {
			this.dataTracker.set(IS_FLYING, flag);
		}
		
		@Override
		public boolean canFly() {
			return true;
		}
		
		@Override
		protected void initDataTracker() {
			super.initDataTracker();
			this.dataTracker.startTracking(IS_FLYING, false);
		}
	}
	
	public static class SeekFoodGoal extends Goal {
		private final MobEntity user;
		private boolean hasFood;
		private ItemEntity closestFood;
		private final boolean herbivore;
		
		public SeekFoodGoal(MobEntity user, boolean herbivore) {
			this.user = user;
			this.herbivore = herbivore;
			this.hasFood = user.hasPassengers();
		}
		
		@Override
		public void start() {
			MoCMain.logger.info("Found food. Should start navigating now.");
			if (!closestFood.hasVehicle()) {
				float f = closestFood.distanceTo(this.user);
				if (f > 1.0F) {
					int i = MathHelper.floor(closestFood.x);
					int j = MathHelper.floor(closestFood.y);
					int k = MathHelper.floor(closestFood.z);
					faceLocation(this.user, i, j, k, 30F);
					
					this.user.getNavigation().startMovingTo(closestFood, f);
					return;
				}
				if (f < 1.0F) {
					this.closestFood.startRiding(this.user);
					hasFood = this.user.hasPassengers();
				}
			}
		}
		
		@Override
		public boolean shouldContinue() {
			return !user.getNavigation().isIdle();
		}
		
		@Override
		public boolean canStart() {
			if (!hasFood) { // If the user does NOT have food
				this.closestFood = MoCTools.getClosestFood(this.user, 8D); // Find the nearest food
				return (!(this.closestFood == null || this.closestFood.removed) // Check if the food still exists
						&& isEdible(closestFood.getStack().getItem()) // If so, check if it's edible
						&& !this.closestFood.hasVehicle()); // If so, check if it has a vehicle
				// Else, give up and try again next tick.
			}
			// Else, check if the food is still there and if so start navigating to it.
			return this.closestFood != null;
		}
		
		private boolean isEdible(Item item) {
			return (this.herbivore ?
					MoCTools.isItemEdibleForHerbivores(item) :
					MoCTools.isItemEdibleForCarnivores(item));
		}
	}
}
