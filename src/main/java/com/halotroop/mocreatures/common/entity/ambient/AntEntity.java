package com.halotroop.mocreatures.common.entity.ambient;

import com.halotroop.mocreatures.common.registry.MoCEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;

public class AntEntity extends InsectEntity {
	public AntEntity(EntityType<? extends InsectEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	protected void initGoals() {
		this.goalSelector.add(4, new SeekFoodGoal(this, true));
		this.goalSelector.add(5, new WanderAroundGoal(this, 1.2D));
	}
	
	@Override
	public PassiveEntity createChild(PassiveEntity mate) {
		PassiveEntity child = MoCEntityTypes.ANT.create(world);
		world.spawnEntity(child);
		return child;
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
	}
	
	@Override
	public int getMaxAir() {
		return 60; // Die in water after 3 seconds
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
	}
	
	@Override
	public float getMovementSpeed() {
		return hasPassengers() ? 0.05F : 0.15F;
	}
	
}
