package com.halotroop.mocreatures.common.entity.ai.goals;

import com.halotroop.mocreatures.common.entity.MoCEntity;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.passive.AnimalEntity;

public class MoCWanderGoal extends WanderAroundGoal {
	public AnimalEntity creature;
	
	public MoCWanderGoal(AnimalEntity creature, double speed) {
		super(creature, speed);
		this.creature = creature;
	}
	
	@Override
	public boolean canStart() {
		if (this.creature instanceof MoCEntity && ((MoCEntity) this.creature).isMovementCeased()) {
			return false;
		}
		return super.canStart();
	}
}
