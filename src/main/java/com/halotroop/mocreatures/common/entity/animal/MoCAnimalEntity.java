package com.halotroop.mocreatures.common.entity.animal;

import com.halotroop.mocreatures.common.entity.MoCEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class MoCAnimalEntity extends AnimalEntity implements MoCEntity {
	private static final TrackedData<BlockPos> HOME_POS;
	
	static {
		HOME_POS = DataTracker.registerData(TurtleEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
	}
	
	protected MoCAnimalEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	public PassiveEntity createChild(PassiveEntity passiveEntity) {
		return null;
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(HOME_POS, BlockPos.ORIGIN);
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag compoundTag) {
		super.writeCustomDataToTag(compoundTag);
		compoundTag.putInt("HomePosX", this.getHomePosition().getX());
		compoundTag.putInt("HomePosY", this.getHomePosition().getY());
		compoundTag.putInt("HomePosZ", this.getHomePosition().getZ());
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag compoundTag) {
		super.readCustomDataFromTag(compoundTag);
		int i = compoundTag.getInt("HomePosX");
		int j = compoundTag.getInt("HomePosY");
		int k = compoundTag.getInt("HomePosZ");
		this.setHomePosition(new BlockPos(i, j, k));
	}
	
	public boolean hasHome() {
		return this.dataTracker.get(HOME_POS) != null;
	}
	
	public BlockPos getHomePosition() {
		return this.dataTracker.get(HOME_POS);
	}
	
	public void setHomePosition(BlockPos position) {
		this.dataTracker.set(HOME_POS, position);
	}
	
	public abstract int getMaximumHomeDistance();
	
	public boolean positionIsWithinHomeDistance(BlockPos position) {
		return getHomePosition().isWithinDistance(position, getMaximumHomeDistance());
	}
	
	@Override
	public String getPetName() {
		return "";
	}
	
	@Override
	public void setPetName(String name) {
	
	}
	
	@Override
	public int getOwnerPetId() {
		return 0;
	}
	
	@Override
	public void setOwnerPetId(int petId) {
	
	}
	
	@Override
	public UUID getOwnerId() {
		return null;
	}
	
	@Override
	public boolean isTamed() {
		return false;
	}
	
	@Override
	public boolean getIsAdult() {
		return false;
	}
	
	@Override
	public boolean getIsGhost() {
		return false;
	}
	
	@Override
	public void setAdult(boolean flag) {
	
	}
	
	@Override
	public boolean checkSpawningBiome() {
		return false;
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return false;
	}
	
	@Override
	public boolean renderName() {
		return false;
	}
	
	@Override
	public int nameYOffset() {
		return 0;
	}
	
	@Override
	public void jump() {
	
	}
	
	@Override
	public void makeEntityDive() {
	
	}
	
	@Override
	public float getSizeFactor() {
		return 0;
	}
	
	@Override
	public float getAdjustedYOffset() {
		return 0;
	}
	
	@Override
	public void setArmorType(int i) {
	
	}
	
	@Override
	public float rollRotationOffset() {
		return 0;
	}
	
	@Override
	public float pitchRotationOffset() {
		return 0;
	}
	
	@Override
	public void setAgeTicks(int i) {
	
	}
	
	@Override
	public int getAgeTicks() {
		return 0;
	}
	
	@Override
	public float yawRotationOffset() {
		return 0;
	}
	
	@Override
	public float getAdjustedZOffset() {
		return 0;
	}
	
	@Override
	public float getAdjustedXOffset() {
		return 0;
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
		return false;
	}
	
	@Override
	public boolean isMovementCeased() {
		return false;
	}
	
	@Override
	public boolean shouldAttackPlayers() {
		return false;
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
	
	@Override
	public int maxFlyingHeight() {
		return 0;
	}
	
	@Override
	public int minFlyingHeight() {
		return 0;
	}
	
	@Override
	public boolean isFlyer() {
		return false;
	}
	
	@Override
	public boolean getIsFlying() {
		return false;
	}
	
	@Override
	public String getClazzString() {
		return null;
	}
}
