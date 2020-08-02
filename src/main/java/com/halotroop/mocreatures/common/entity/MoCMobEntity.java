package com.halotroop.mocreatures.common.entity;

import blue.endless.jankson.annotation.Nullable;
import com.halotroop.mocreatures.common.config.MoCSettings;
import com.halotroop.mocreatures.common.config.MoCreaturesConfig;
import com.halotroop.mocreatures.common.entity.ai.goals.MoCWanderGoal2;
import com.halotroop.mocreatures.common.entity.animal.MoCAnimalEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.CollisionView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class MoCMobEntity extends MobEntity implements MoCEntity {
	protected boolean divePending;
	protected int maxHealth;
	protected float moveSpeed;
	protected String texture;
	protected EntityNavigation navigationWater;
	protected EntityNavigation navigationFlyer;
	protected MoCWanderGoal2 wander;
	
	protected static final TrackedData<Boolean> ADULT;
	protected static final TrackedData<Integer> AGE;
	protected static final TrackedData<String> NAME_STR;
	
	static {
		ADULT = DataTracker.registerData(AnimalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		NAME_STR = DataTracker.registerData(AnimalEntity.class, TrackedDataHandlerRegistry.STRING);
		AGE = DataTracker.registerData(AnimalEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
	
	private final MoCreaturesConfig.CreatureConfig config;
	
	protected MoCMobEntity(EntityType<? extends MobEntity> entityType, World world,
	                       MoCreaturesConfig.CreatureConfig config) {
		super(entityType, world);
		this.navigationWater = new SwimNavigation(this, world);
		this.navigationFlyer = new BirdNavigation(this, world);
		this.config = config;
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(getMovementSpeed());
		this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(getAttackStrength());
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(20.0D);
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ADULT, false);
		this.dataTracker.startTracking(AGE, 45);
		this.dataTracker.startTracking(NAME_STR, "");
	}
	
	protected double getAttackStrength() {
		return 2.0D;
	}
	
	@Override
	public boolean getIsAdult() {
		return this.dataTracker.get(ADULT);
	}
	
	@Override
	public void setAdult(boolean flag) {
		this.dataTracker.set(ADULT, flag);
	}
	
	@Override
	public boolean isTamed() {
		return false;
	}
	
	@Override
	public String getPetName() {
		return this.dataTracker.get(NAME_STR);
	}
	
	@Override
	public int getAgeTicks() {
		return this.dataTracker.get(AGE);
	}
	
	@Nullable
	public UUID getOwnerId() {
		return null;
	}
	
	public void setOwnerUniqueId(@Nullable UUID uniqueId) {
	}
	
	@Override
	public int getOwnerPetId() {
		return 0;
	}
	
	@Override
	public void setOwnerPetId(int petId) {
	}
	
	@Override
	public void setAgeTicks(int value) {
		this.dataTracker.set(AGE, value);
	}
	
	@Override
	public void setPetName(String name) {
		this.dataTracker.set(NAME_STR, name);
	}
	
	@Override
	public boolean canSpawn(CollisionView collisionView) {
		return collisionView.doesNotCollide(this.getBoundingBox())
				&& !collisionView.intersectsEntities(this)
				&& !collisionView.isWaterAt(this.getBlockPos());
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return (this.config.frequency > 0 && super.canSpawn(this.world));
	}
	
	public boolean getCanSpawnHereMob() {
		int i = MathHelper.floor(this.x);
		int j = MathHelper.floor(getBoundingBox().y1);
		int k = MathHelper.floor(this.z);
		BlockPos pos = new BlockPos(i, j, k);
		if (this.world.getLightLevel(LightType.SKY, pos) > this.random.nextInt(32)) {
			return false;
		}
		int l = this.world.getLightLevel(pos);
//		if (this.world.isThundering()) {
//			int i1 = this.world.getSkylightSubtracted();
//			this.world.setSkylightSubtracted(10);
//			l = this.world.getLightFromNeighbors(pos);
//			this.world.setSkylightSubtracted(i1);
//		}
		return l <= this.random.nextInt(8);
	}
	
	// TODO move this to a class accessible by MocEntityMob and MoCentityAnimals
	protected LivingEntity getClosestEntityLiving(Entity entity, double d) {
		double d1 = -1D;
		LivingEntity livingEntity = null;
		for (Entity entity1 : this.world.getEntities(this, getBoundingBox().expand(d, d, d))) {
			if (shouldIgnore(entity1)) {
				continue;
			}
			double d2 = entity1.distanceTo(entity);
			if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canSee(entity)) {
				d1 = d2;
				livingEntity = (LivingEntity) entity1;
			}
		}
		
		return livingEntity;
	}
	
	// TODO: REMOVE - DrZhark team
	public boolean shouldIgnore(Entity entity) {
		return ((!(entity instanceof LivingEntity)) // Ignore non-living
				|| (entity instanceof MobEntity) // Ignore
				|| (entity instanceof PlayerEntity && this.isTamed())
//				|| (entity instanceof MoCEntityEgg) // TODO: Uncomment when MoCEntityEgg is added
//				|| (entity instanceof MoCEntityKittyBed) // TODO: Uncomment when MoCEntityEgg is added
//				|| (entity instanceof EntityLitterBox) // TODO: Uncomment when MoCEntityEgg is added
				|| (this.isTamed() && (entity instanceof MoCAnimalEntity && ((MoCAnimalEntity) entity).isTamed()))
				|| ((entity instanceof WolfEntity) && !(MoCSettings.creatureGeneralSettings.attackWolves))
				|| ((entity instanceof HorseEntity) && !(MoCSettings.creatureGeneralSettings.attackHorses))
		);
	}
	
	@Override
	public boolean checkSpawningBiome() {
		return true;
	}
	
	@Override
	public void tick() {
		if (!this.world.isClient()) {
			if (this.isHarmedByDaylight()) {
				if (this.world.isDay()) {
					float var1 = this.getBrightnessAtEyes();
					if (var1 > 0.5F
							&& this.world.isSkyVisible(new BlockPos(MathHelper.floor(this.x), MathHelper.floor(this.y),
							MathHelper.floor(this.z))) && this.random.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
						this.setOnFireFor(8);
					}
				}
			}
			if (getAgeTicks() == 0) setAgeTicks(getMaxAgeTicks() - 10); // fixes tiny creatures spawned by error
			if (!getIsAdult() && (this.random.nextInt(300) == 0)) {
				setAgeTicks(getAgeTicks() + 1);
				if (getAgeTicks() >= getMaxAgeTicks()) {
					setAdult(true);
				}
			}
			
			if (getIsFlying() && this.getNavigation().isIdle() && !isMovementCeased()
					&& this.getTarget() == null && this.random.nextInt(20) == 0) {
				this.wander.tick();
			}
		}
		
		this.getNavigation().tick();
		super.tick();
	}
	
	protected int getMaxAgeTicks() {
		return 100;
	}
	
	protected boolean isHarmedByDaylight() {
		return false;
	}
	
	/**
	 * Boolean used to select pathfinding behavior
	 *
	 * @return whether this is a flying entity
	 */
	public boolean isFlyer() {
		return false;
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);
		//nbttagcompound = MoCTools.getEntityData(this);
		tag.putBoolean("Adult", getIsAdult());
		tag.putInt("AgeTicks", getAgeTicks());
		tag.putString("Name", getPetName());
		
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		//tag = MoCTools.getEntityData(this);
		setAdult(tag.getBoolean("Adult"));
		setAgeTicks(tag.getInt("AgeTicks"));
		setPetName(tag.getString("Name"));
		
	}
	
	@Override
	protected void fall(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
		if (!isFlyer()) {
			super.fall(d, bl, blockState, blockPos);
		}
	}
	
	@Override
	public boolean isClimbing() {
		return !isFlyer() && super.isClimbing();
	}
	
	@Override
	public void travel(Vec3d vec3d) {
		if (!isFlyer()) {
			super.travel(vec3d);
		} else {
			this.moveEntityWithHeadingFlyer(vec3d);
		}
	}
	
	public void moveEntityWithHeadingFlyer(Vec3d vec3d) {
		if (!this.world.isClient()) {
			this.move(MovementType.SELF, vec3d);
			this.setVelocity(
					this.getVelocity().x * 0.8999999761581421D,
					this.getVelocity().y * 0.8999999761581421D,
					this.getVelocity().z * 0.8999999761581421D);
		} else {
			super.travel(vec3d);
		}
	}
	
	public float getMoveSpeed() {
		return 0.7F;
	}
	
	@Override
	public int nameYOffset() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void jump() {
	
	}
	
	@Override
	public boolean renderName() {
		return MoCSettings.globalSettings.displayPetName
				&& (getPetName() != null && !getPetName().equals("") && !this.hasPassengers() && this.hasVehicle());
	}
	
	@Override
	public void makeEntityDive() {
		this.divePending = true;
	}
	
	@Override
	public boolean cannotDespawn() {
		return isTamed();
	}
	
	@Override
	public void kill() {
		// Server check required to prevent tamed entities from being duplicated on client-side
		if (!this.world.isClient && (isTamed()) && (getHealth() > 0)) {
			return;
		}
		super.kill();
	}
	
	@Override
	public float getSizeFactor() {
		return 1.0F;
	}
	
	@Override
	public float getAdjustedYOffset() {
		return 0F;
	}
	
	@Override
	public void setArmorType(int i) {
	}
	
	public int getArmorType() {
		return 0;
	}
	
	@Override
	public float pitchRotationOffset() {
		return 0F;
	}
	
	@Override
	public float rollRotationOffset() {
		return 0F;
	}
	
	@Override
	public float yawRotationOffset() {
		return 0F;
	}
	
	@Override
	public float getAdjustedZOffset() {
		return 0F;
	}
	
	@Override
	public float getAdjustedXOffset() {
		return 0F;
	}
	
	@Override
	public Identifier getTexture() {
		return null;
	}
	
	@Override
	public boolean canAttackTarget(LivingEntity entity) {
		return false;
	}
	
	@Override
	public boolean getIsSitting() {
		return false;
	}
	
	@Override
	public boolean isNotScared() {
		return true;
	}
	
	@Override
	public boolean isMovementCeased() {
		return false;
	}
	
	@Override
	public boolean shouldAttackPlayers() {
		return !(this.world.getDifficulty().equals(Difficulty.PEACEFUL));
	}
	
	@Override
	public double getDivingDepth() {
		return 0;
	}
	
	@Override
	public boolean isDiving() {
		return false;
	}
	
	@Override
	public void forceEntityJump() {
	}
	
	// Removed override for tryAttack (attackEntityAsMob)
	
	@Override
	public int maxFlyingHeight() {
		return 5;
	}
	
	@Override
	public int minFlyingHeight() {
		return 1;
	}
	
	@Override
	public EntityNavigation getNavigation() {
		if (this.isWet() && this.isAmphibian()) {
			return this.navigationWater;
		}
		if (this.isFlyer()) {
			return this.navigationFlyer;
		}
		return this.navigation;
	}
	
	public boolean isAmphibian() {
		return false;
	}
	
	@Override
	public boolean getIsFlying() {
		return isFlyer();
	}
	
	@Override
	public String getClazzString() {
		return getClass().toString(); // FIXME: This is WRONG!
//		return EntityList.getEntityString(this);
	}
	
	@Override
	public boolean getIsGhost() {
		return false;
	}
}
