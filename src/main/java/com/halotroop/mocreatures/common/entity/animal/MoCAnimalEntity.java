package com.halotroop.mocreatures.common.entity.animal;

import com.halotroop.mocreatures.common.config.MoCSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoCAnimalEntity extends AnimalEntity {
	private static final TrackedData<BlockPos> HOME_POS;
	private static final TrackedData<Boolean> ADULT;
	
	static {
		HOME_POS = DataTracker.registerData(MoCAnimalEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
		ADULT = DataTracker.registerData(MoCAnimalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
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
	
	public boolean isAdult() {
		return this.dataTracker.get(ADULT);
	}
	
	@Override
	public boolean shouldRenderName() {
		return (MoCSettings.globalSettings.displayPetName && this.hasCustomName()
				&& !this.hasPassengers() && !this.hasVehicle());
	}
	
	@Override
	public void jump() {
		this.addVelocity(0, getJumpVelocity(), 0);
	}
	
	public boolean isNotScared() {
		return false;
	}
}
