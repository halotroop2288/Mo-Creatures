package com.halotroop.mocreatures.common.entity.ambient;

import com.halotroop.mocreatures.common.entity.ai.goals.MoCWanderGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.world.World;

public abstract class InsectEntity extends MoCAmbientEntity {
	private int climbCounter;
	protected MoCWanderGoal wander;
	
	private static final TrackedData<Boolean> IS_FLYING;
	
	static {
		IS_FLYING = DataTracker.registerData(InsectEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}
	
	protected InsectEntity(EntityType<? extends MoCAmbientEntity> entityType, World world) {
		super(entityType, world);
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
		this.dataTracker.startTracking(IS_FLYING, Boolean.FALSE);
	}
	
	public boolean isFlying() {
		return this.dataTracker.get(IS_FLYING);
	}
	
	public void setFlying(boolean flag) {
		this.dataTracker.set(IS_FLYING, flag);
	}
	
	@Override
	public void tick() {
		super.tick();
	}
}
