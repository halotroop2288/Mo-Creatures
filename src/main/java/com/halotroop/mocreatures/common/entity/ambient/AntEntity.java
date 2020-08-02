package com.halotroop.mocreatures.common.entity.ambient;

import com.halotroop.mocreatures.common.MoCTools;
import com.halotroop.mocreatures.common.entity.ai.goals.MoCWanderGoal2;
import com.halotroop.mocreatures.common.registry.MoCEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AntEntity extends InsectEntity {
	private static final TrackedData<Boolean> FOUND_FOOD;
	
	static {
		FOUND_FOOD = DataTracker.registerData(AntEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}
	
	public AntEntity(EntityType<? extends InsectEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new MoCWanderGoal2(this, 1.2D));
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FOUND_FOOD, false);
	}
	
	@Override
	protected void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
	}
	
	public boolean hasFood() {
		return this.dataTracker.get(FOUND_FOOD);
	}
	
	public void setHasFood(boolean flag) {
		this.dataTracker.set(FOUND_FOOD, flag);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (!this.world.isClient()) {
			if (!this.hasFood()) {
				ItemEntity itemEntity = MoCTools.getClosestFood(this, 8D);
				if (itemEntity == null || itemEntity.removed) {
					return;
				}
				if (!itemEntity.hasVehicle()) {
					float f = itemEntity.distanceTo(this);
					if (f > 1.0F) {
						int i = MathHelper.floor(itemEntity.x);
						int j = MathHelper.floor(itemEntity.y);
						int k = MathHelper.floor(itemEntity.z);
						faceLocation(i, j, k, 30F);
						
						setMyOwnPath(itemEntity, f);
						return;
					}
					if (f < 1.0F) {
						exchangeItem(itemEntity);
						setHasFood(true);
						return;
					}
				}
			}
		}
		
		if (hasFood()) {
			if (!this.hasPassengers()) {
				ItemEntity itemEntity = MoCTools.getClosestFood(this, 2D);
				if (itemEntity != null && !itemEntity.hasVehicle()) {
					itemEntity.startRiding(this);
					return;
				}
				
				if (!this.hasPassengers()) {
					setHasFood(false);
				}
			}
		}
	}
	
	private void exchangeItem(ItemEntity itemEntity) {
		ItemEntity cargo = new ItemEntity(this.world, this.x, this.y + 0.2D, this.z, itemEntity.getStack());
		itemEntity.kill();
		if (!this.world.isClient) {
			this.world.spawnEntity(cargo);
		}
	}
	
	@Override
	public PassiveEntity createChild(PassiveEntity passiveEntity) {
		PassiveEntity child = MoCEntityTypes.ANT.create(this.world);
		if (child != null) {
			world.spawnEntity(child);
		}
		return child;
	}
	
	@Override
	public float getMovementSpeed() {
		if (hasFood()) {
			return 0.05F;
		} else {
			return 0.15F;
		}
	}
}
