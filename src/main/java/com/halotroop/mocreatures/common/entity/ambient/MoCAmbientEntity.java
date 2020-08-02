package com.halotroop.mocreatures.common.entity.ambient;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class MoCAmbientEntity extends AnimalEntity {
	protected MoCAmbientEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public void faceLocation(int i, int j, int k, float f) {
		double l = i + 0.5D - this.x;
		double m = j + 0.5D - this.y;
		double n = k + 0.5D - this.z;
		double o = MathHelper.sqrt(l * l + n * n);
		float p = (float) (Math.atan2(n, l) * 180.0D / Math.PI) - 90.0F;
		float q = (float) (-(Math.atan2(m, o) * 180.0D / Math.PI));
		this.pitch = -this.updateRotation(this.pitch, q, f);
		this.yaw = this.updateRotation(this.yaw, p, f);
	}
	
	private float updateRotation(float currentRotation, float intendedRotation, float maxIncrement) {
		float var4 = intendedRotation - currentRotation;
		
		while (var4 < -180.0F) {
			var4 += 360.0F;
		}
		
		while (var4 >= 180.0F) {
			var4 -= 360.0F;
		}
		
		if (var4 > maxIncrement) {
			var4 = maxIncrement;
		}
		
		if (var4 < -maxIncrement) {
			var4 = -maxIncrement;
		}
		
		return currentRotation + var4;
	}
	
	public void setMyOwnPath(Entity entity, float f) {
		this.getNavigation().startMovingTo(entity, f);
	}
}
