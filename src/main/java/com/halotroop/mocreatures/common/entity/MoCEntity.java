package com.halotroop.mocreatures.common.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

public interface MoCEntity {
	String getPetName();
	
	void setPetName(String name);
	
	int getOwnerPetId();
	
	void setOwnerPetId(int petId);
	
	UUID getOwnerId();
	
	boolean isTamed();
	
	boolean getIsAdult();
	
	boolean getIsGhost();
	
	void setAdult(boolean flag);
	
	boolean checkSpawningBiome();
	
	boolean getCanSpawnHere();
	
	boolean renderName();
	
	int nameYOffset();
	
	void jump();
	
	void makeEntityDive();
	
	float getSizeFactor();
	
	float getAdjustedYOffset();
	
	void setArmorType(int i);
	
	float rollRotationOffset();
	
	float pitchRotationOffset();
	
	void setAgeTicks(int i);
	
	int getAgeTicks();
	
	float yawRotationOffset();
	
	float getAdjustedZOffset();
	
	float getAdjustedXOffset();
	
	Identifier getTexture();
	
	boolean canAttackTarget(LivingEntity entity);
	
	boolean getIsSitting(); // is the entity sitting, for animations and AI
	
	boolean isNotScared(); //relentless creature that attacks others used for AI
	
	boolean isMovementCeased(); //to deactivate path / wander behavior AI
	
	boolean shouldAttackPlayers();
	
	double getDivingDepth();
	
	boolean isDiving();
	
	void forceEntityJump();
	
	int maxFlyingHeight();
	
	int minFlyingHeight();
	
	boolean isFlyer();
	
	boolean getIsFlying();
	
	String getClazzString();
}
