package org.mocreatures.mocreatures.common.entity.ambient;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.CollisionView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public abstract class MoCAmbientEntity extends AnimalEntity {
	protected static final TrackedData<Boolean> ADULT;
	protected static final TrackedData<Integer> AGE;
	protected static final TrackedData<String> NAME_STR;
	
	protected static final EntityAttribute JUMP_STRENGTH;
	
	static {
		ADULT = DataTracker.registerData(AnimalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		NAME_STR = DataTracker.registerData(AnimalEntity.class, TrackedDataHandlerRegistry.STRING);
		AGE = DataTracker.registerData(AnimalEntity.class, TrackedDataHandlerRegistry.INTEGER);
		
		JUMP_STRENGTH = (new ClampedEntityAttribute(null, "jumpStrength", 0.7D, 0.0D, 2.0D))
				.setName("Jump Strength").setTracked(true);
	}
	
	protected MoCAmbientEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.7F);
		this.getAttributeInstance(JUMP_STRENGTH).setBaseValue(this.getJumpPower());
	}
	
	public void faceLocation(int i, int j, int k, float f) {
		double l = i + 0.5D - this.x;
		double m = j + 0.5D - this.y;
		double n = k + 0.5D - this.z;
		double o = MathHelper.sqrt(l * l + n * n);
		float p = (float) (Math.atan2(n, l) * 180.0D / Math.PI) - 90.0F;
		float q = (float) (-(Math.atan2(m, o) * 180.0D / Math.PI));
		this.pitch = -this.updateRotation(this.pitch, q, f);
		this.yaw = this.updateRotation(this.yaw, p, f);
	}
	
	private float updateRotation(float currentRotation, float intendedRotation, float maxIncrement) {
		float var4 = intendedRotation - currentRotation;
		
		while (var4 < -180.0F) var4 += 360.0F;
		while (var4 >= 180.0F) var4 -= 360.0F;
		
		if (var4 > maxIncrement) var4 = maxIncrement;
		if (var4 < -maxIncrement) var4 = -maxIncrement;
		
		return currentRotation + var4;
	}
	
	/**
	 * @return mount jumping power
	 */
	public double getJumpPower() {
		return 0.4D;
	}
	
	/**
	 * sound played when an untamed mount buckles rider
	 */
	protected abstract SoundEvent getAngrySound();
	
	public boolean isAdult() {
		return this.dataTracker.get(ADULT);
	}
	
	public abstract int getFrequency();
	
	@Override
	public boolean canSpawn(CollisionView world) {
		if (this.getFrequency() <= 0) {
			return false;
		}
		BlockPos pos = new BlockPos(MathHelper.floor(this.x), MathHelper.floor(getBoundingBox().y1), this.z);
		
		Biome biome = world.getBiome(pos);
		
		if (biome.equals(Biomes.JUNGLE) || biome.equals(Biomes.JUNGLE_HILLS)) {
			return getCanSpawnHereJungle();
		}
		
		return super.canSpawn(world);
	}
	
	public boolean getCanSpawnHereJungle() {
		if (this.world.doesNotCollide(this.getBoundingBox()) && !this.collided && !this.isTouchingWater()) {
			int var1 = MathHelper.floor(this.x);
			int var2 = MathHelper.floor(this.getBoundingBox().y1);
			int var3 = MathHelper.floor(this.z);
			
			if (var2 < 63) {
				return false;
			}
			
			BlockPos pos = new BlockPos(var1, var2, var3);
			BlockState blockstate = this.world.getBlockState(pos.down());
			
			Block[] validBlocks = {
					Blocks.GRASS, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES,
					Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES
			};
			
			for (Block block : validBlocks) {
				if (block.equals(blockstate.getBlock())) return true;
			}
		}
		return false;
	}
	
	@Override
	public void jump() {
		this.setJumping(true);
	}
	
	/**
	 * @return whether the entity is rideable
	 */
	public boolean rideableEntity() {
		return false;
	}
	
	/**
	 * fixes bug with entities following a player carrying wheat
	 */
	protected abstract Entity findPlayerToAttack();
	
	public void repelMobs(Entity entity1, Double dist, World world) {
		for (Entity entity : world.getEntities(entity1, entity1.getBoundingBox().expand(dist, 4D, dist))) {
			if (entity instanceof MobEntity) {
				MobEntity mobEntity = (MobEntity) entity;
				mobEntity.setTarget(null);
				mobEntity.getNavigation().stop(); // clearPath
			}
		}
	}
	
	public void faceItem(int i, int j, int k, float f) {
		double d = i - this.x;
		double d2 = j - this.y;
		double d1 = k - this.z;
		double d3 = MathHelper.sqrt((d * d) + (d1 * d1));
		float f1 = (float) ((Math.atan2(d1, d) * 180D) / Math.PI) - 90F;
		float f2 = (float) ((Math.atan2(d2, d3) * 180D) / Math.PI);
		this.pitch = -adjustRotation(this.pitch, f2, f);
		this.yaw = adjustRotation(this.yaw, f1, f);
	}
	
	public float adjustRotation(float f, float f1, float f2) {
		float f3 = f1 - f;
		while (f3 < -180F) {
			f3 += 360F;
		}
		while (f3 >= 180F) {
			f3 -= 360F;
		}
		if (f3 > f2) {
			f3 = f2;
		}
		if (f3 < -f2) {
			f3 = -f2;
		}
		return f + f3;
	}
	
	public boolean isFlyingAlone() {
		return false;
	}
	
	private void followPlayer() {
		PlayerEntity player = this.world.getClosestPlayer(this, 24D);
		if (player == null) {
			return;
		}
		
		ItemStack handStack = player.getActiveItem();
		if (handStack != null && isBreedingItem(handStack)) {
			this.getNavigation().startMovingTo(player, 1D);
		}
	}
	
	public boolean isOnAir() {
		return (this.world.isAir(new BlockPos(
				MathHelper.floor(this.x),
				MathHelper.floor(this.y - 0.2D),
				MathHelper.floor(this.z))) && this.world.isAir(new BlockPos(MathHelper.floor(this.x),
				MathHelper.floor(this.y - 1.2D),
				MathHelper.floor(this.z))));
	}
	
	@Override
	public float getScaleFactor() {
		return 1.0F;
	}
	
	@Override
	public double getHeightOffset() {
		return 0D;
	}
	
	public boolean shouldIgnore(Entity entity) {
		return true; // Ambient entities should not attack... Right?
	}
	
	@Override
	public PassiveEntity createChild(PassiveEntity passiveEntity) {
		return null;
	}
}
