package com.halotroop.mocreatures.common.entity.animal;

import com.halotroop.mocreatures.common.entity.MoCreature;
import com.halotroop.mocreatures.common.registry.MoCSounds;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

import java.util.Locale;

// TODO: Add feather-falling to the owner when a bird sits on their head.
public class GenericBirdEntity extends ParrotEntity implements MoCreature.Flying {
	public float getWingAnimationProgress(float f) {
		float f1 = this.wingE + ((this.wingB - this.wingE) * f);
		float f2 = this.wingD + ((this.wingC - this.wingD) * f);
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}
	
	public enum BirdType {
		dove("white", MoCSounds.ENTITY_BIRD_AMBIENT_WHITE.event),
		crow("black", MoCSounds.ENTITY_BIRD_AMBIENT_BLACK.event),
		//		parrot("green", MoCSoundEvents.ENTITY_BIRD_AMBIENT_GREEN.event), // TODO make this a different bird, since parrots have been added to vanilla?
		blue("blue", MoCSounds.ENTITY_BIRD_AMBIENT_BLUE.event),
		canary("yellow", MoCSounds.ENTITY_BIRD_AMBIENT_YELLOW.event),
		cardinal("red", MoCSounds.ENTITY_BIRD_AMBIENT_RED.event);
		
		protected final String color;
		protected final SoundEvent ambientSound;
		
		BirdType(String color, SoundEvent ambientSound) {
			this.color = color.toLowerCase(Locale.ENGLISH);
			this.ambientSound = ambientSound;
		}
	}
	
	public GenericBirdEntity(EntityType<GenericBirdEntity> entityType, World world) {
		super(entityType, world);
		this.wingB = 0.0F;
		this.wingC = 0.0F;
		this.wingH = 1.0F;
	}
	
	@Override
	public EntityData initialize(IWorld world, LocalDifficulty dif, SpawnType type, EntityData data, CompoundTag tag) {
		EntityData entityData1 = super.initialize(world, dif, type, data, tag);
		this.setVariant(this.random.nextInt(BirdType.values().length));
		return entityData1;
	}
	
	private BirdType getBirdType() {
		return BirdType.values()[this.getVariant()];
	}
	
	
	public String getColor() {
		return getBirdType().color;
	}
	
	@Override
	public int maxFlyingHeight() {
		return this.isTamed() ? 4 : 6;
	}
	
	@Override
	public int minFlyingHeight() {
		return 2;
	}
	
	@Override
	public SoundEvent getAmbientSound() {
		if (this.getBirdType() != null && this.getBirdType().ambientSound != null) {
			return this.getBirdType().ambientSound;
		} else {
			return SoundEvents.ENTITY_PARROT_AMBIENT;
		}
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_GENERIC_DEATH;
	}
	
	@Override
	protected SoundEvent getDrinkSound(ItemStack itemStack) {
		return MoCSounds.ENTITY_GENERIC_DRINKING.event;
	}
	
	public float wingB, wingC, wingD, wingE, wingH;
	
	@Override
	public void tick() {
		super.tick();
		this.updateWingAnimationProgress();
	}
	
	public void updateWingAnimationProgress() {
		this.wingE = this.wingB;
		this.wingD = this.wingC;
		this.wingC = (float) (this.wingC + ((this.onGround ? -1 : 4) * 0.3D));
		this.wingC = MathHelper.clamp(this.wingC, 0, 1);
		if (!this.onGround && (this.wingH < 1.0F)) {
			this.wingH = 1.0F;
		}
		this.wingH *= 0.9F;
		Vec3d vel = this.getVelocity();
		if (!this.onGround && (vel.y < 0.0D)) {
			this.setVelocity(vel.x, vel.y * 0.8D, vel.z);
		}
		this.wingB += this.wingH * 2.0F;
	}
}
