package com.halotroop.mocreatures.common.entity.passive.pet;

import blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public interface MoCTamable {
	boolean isRiderDisconnecting();
	
	void setRiderDisconnecting(boolean flag);
	
	void playTameEffect(boolean par1);
	
	void setTamed(boolean par1);
	
	void setDead();
	
	void writeEntityToNBT(CompoundTag tag);
	
	void readEntityFromNBT(CompoundTag tag);
	
	void setOwnerId(@Nullable UUID uuid);
	
	float getPetHealth();
	
	void spawnHeart();
	
	boolean readytoBreed();
	
	String getOffspringClazz(MoCTamable mate);
	
	int getOffspringTypeInt(MoCTamable mate);
	
	boolean compatibleMate(Entity mate);
	
	void setHasEaten(boolean flag);
	
	boolean getHasEaten();
	
	void setGestationTime(int time);
	
	int getGestationTime();
}
