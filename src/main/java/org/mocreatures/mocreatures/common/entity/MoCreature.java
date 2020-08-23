package org.mocreatures.mocreatures.common.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;

import java.util.UUID;

/**
 * Used to label all mobs in the Mo Creatures mod for reference in other classes.
 */
public interface MoCreature {
	/**
	 * @return whether or not the creature can fly.
	 */
	default boolean canFly() {
		return this instanceof MoCreature.Flying;
	}
	
	/**
	 * @return TODO: Fill this in.
	 */
	default boolean isMovementCeased() {
		return this.isSitting() || ((AnimalEntity)this).hasPassengers();
	}
	
	default boolean isSitting() { return false; }
	
	/**
	 * Used to label specifically flying mobs from the Mo Creatures mod.
	 */
	interface Flying extends MoCreature, Flutterer {
		/**
		 * @return the minimum height the creature should ascend to before flying around.
		 */
		int minFlyingHeight();
		
		/**
		 * @return the height the creature should descend to if it finds itself too high.
		 */
		int maxFlyingHeight();
	}
	
	/**
	 * Used to label creatures that have multiple aesthetic variants.
	 *
	 * Implementors should have TrackableData that tracks the variant, and a getter for their type.
	 */
	interface Variable extends MoCreature {
		/**
		 * Should get the variant from the dataTracker.
		 */
		int getVariant();
		
		/**
		 * Should set the variant to the dataTracker.
		 */
		void setVariant(int i);
		
		/**
		 * Should write variant to tag.
		 */
		void writeCustomDataToTag(CompoundTag compoundTag);
		
		/**
		 * Should read variant from tag.
		 */
		void readCustomDataFromTag(CompoundTag compoundTag);
		
		EntityData initialize(IWorld world, LocalDifficulty localDifficulty, SpawnType spawnType, EntityData entityData,
		                      CompoundTag tag);
	}
	
	/**
	 * Used to label creatures that can be tamed by a player.
	 */
	interface Tamable extends MoCreature {
		UUID ownerUuid = null;
	}
}
