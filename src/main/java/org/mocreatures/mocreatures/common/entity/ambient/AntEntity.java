package org.mocreatures.mocreatures.common.entity.ambient;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.world.World;
import org.mocreatures.mocreatures.common.entity.MoCreature;

public class AntEntity extends InsectEntity implements MoCreature {
	public AntEntity(EntityType<? extends InsectEntity> type, World world) {
		super(type, world);
	}
	
	@Override
	protected void initGoals() {
		this.goalSelector.add(4, new SeekFoodGoal(this, true));
		this.goalSelector.add(5, new WanderAroundGoal(this, 1.2D));
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
