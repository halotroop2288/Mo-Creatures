package org.mocreatures.mocreatures.common.entity.ambient;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.mocreatures.mocreatures.common.MoCMain;
import org.mocreatures.mocreatures.common.MoCTools;
import org.mocreatures.mocreatures.common.entity.MoCreature;
import org.mocreatures.mocreatures.common.entity.ai.goals.MoCWanderGoal;

public abstract class InsectEntity extends AnimalEntity implements MoCreature {
	protected MoCWanderGoal wander;
	
	private int climbCounter;
	
	protected InsectEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static void faceLocation(MobEntity entity, int i, int j, int k, float maxIncrement) {
		double var1 = i + 0.5D - entity.x;
		double var2 = k + 0.5D - entity.z;
		double var3 = j + 0.5D - entity.y;
		double var4 = MathHelper.sqrt(var1 * var1 + var2 * var2);
		float yawModifier = (float) (Math.atan2(var2, var1) * 180.0D / Math.PI) - 90.0F;
		float pitchModifier = (float) (-(Math.atan2(var3, var4) * 180.0D / Math.PI));
		entity.pitch = -updateRotation(entity.pitch, pitchModifier, maxIncrement);
		entity.yaw = updateRotation(entity.yaw, yawModifier, maxIncrement);
	}
	
	// FIXME: This can probably be simplified with some math-y stuff.
	private static float updateRotation(float currentRotation, float intendedRotation, float maxIncrement) {
		float rotMod = intendedRotation - currentRotation;
		
		while (rotMod < -180.0F) rotMod += 360.0F;
		while (rotMod >= 180.0F) rotMod -= 360.0F;
		
		if (rotMod > maxIncrement) rotMod = maxIncrement;
		if (rotMod < -maxIncrement) rotMod = -maxIncrement;
		
		return currentRotation + rotMod;
	}
	
	@Override
	public boolean canBeLeashedBy(PlayerEntity playerEntity) {
		return false;
	}
	
	@Override
	protected void initGoals() {
		super.initGoals();
		wander = new MoCWanderGoal(this, this.getMovementSpeed());
		this.goalSelector.add(5, wander);
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
	
	@Override
	public void tick() {
		super.tick();
		
		if (world.isClient) { // Client stuff
			if (this.climbCounter > 0 && ++this.climbCounter > 8) {
				this.climbCounter = 0;
			}
		} else { // Server stuff
			if (this.isClimbing() && !this.onGround) {
				// Animation???
			}
		}
	}
	
	@Override
	public PassiveEntity createChild(PassiveEntity passiveEntity) {
		return null;
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
		
		@Override
		public void tick() {
			super.tick();
			
			if (!world.isClient) {
				if (isAttractedToLight() && this.random.nextInt(50) == 0) {
					BlockPos ai = MoCTools.ReturnNearestBlockPos(this, Blocks.TORCH, 8D);
					if (ai.getX() > -1000) {
						this.getNavigation().findPathTo(ai, 1);
					}
				}
				// Makes the entity start flying if a large mob is around
				if (!isFlying()) {
					if (this.random.nextInt(getFlyingFreq()) == 0) {
						for (Entity fear : this.world.getEntities(this, this.getBoundingBox().expand(4D, 4D, 4D))) {
							if ((fear instanceof LivingEntity)
									&& fear.getWidth() >= 0.4F && fear.getHeight() >= 0.4f && canSee(fear)) {
								this.startFlying();
							}
						}
					}
				}
				
				// Makes the entity start flying at random
				if (isFlying() && (this.random.nextInt(200) == 0)) {
					this.startFlying();
				}
				
				// This makes the flying insect move all the time in the air
				if (isFlying()) {
					if (this.navigation.isIdle() && !isMovementCeased() && getTarget() == null) {
						this.wander.makeUpdate();
					}
				}
			}
		}
		
		private void startFlying() {
			this.setFlying(true);
			this.wander.makeUpdate();
		}
		
		/**
		 * @return how often this creature starts flying
		 */
		protected int getFlyingFreq() { return 20; }
		
		public abstract boolean isAttractedToLight();
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
			MoCMain.logger.devInfo("Found food. Should start navigating now.");
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
