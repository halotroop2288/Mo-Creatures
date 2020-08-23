package org.mocreatures.mocreatures.common.entity.passive.pet;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.mocreatures.mocreatures.common.entity.MoCreature;
import org.mocreatures.mocreatures.common.registry.MoCSounds;

import java.util.Locale;

public class GenericBirdEntity extends ParrotEntity implements MoCreature.Flying, MoCreature.Variable {
	public float wingB, wingC, wingD, wingE, wingH;
	private int hopTimer;
	
	public GenericBirdEntity(EntityType<? extends ParrotEntity> entityType, World world) {
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
	
	@Override
	public boolean interactMob(PlayerEntity playerEntity, Hand hand) {
		final ItemStack stack = playerEntity.getActiveItem();
		if (!stack.isEmpty() && stack.getItem().equals(Items.WHEAT_SEEDS)) {
			if (!playerEntity.abilities.creativeMode) {
				stack.decrement(1);
			}
			
			if (!this.isSilent()) {
				this.world.playSound(null, this.x, this.y, this.z, SoundEvents.ENTITY_PARROT_EAT,
						this.getSoundCategory(), 1.0F,
						1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			}
			
			if (!this.world.isClient) {
				if (this.random.nextInt(10) == 0) {
					this.setOwner(playerEntity);
					this.showEmoteParticle(true);
					this.world.sendEntityStatus(this, (byte)7);
				} else {
					this.showEmoteParticle(false);
					this.world.sendEntityStatus(this, (byte)6);
				}
			}
			return true;
		} else {
			if (this.isTamed()) {
				if (this.hasVehicle()) {
					this.stopRiding();
				} else {
					if (this.startRiding(playerEntity)) {
						this.yaw = playerEntity.yaw;
					}
				}
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void tick() {
		super.tick();
		this.updateWingAnimationProgress();
		
		if (this.getVehicle() != null) {
			this.yaw = this.getVehicle().yaw;
			
			if (this.getVehicle() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) this.getVehicle();
				// FIXME: Give player slow falling effect in later versions
				player.fallDistance = 0.0F;
				Vec3d vel = player.getVelocity();
				if (vel.getY() < -0.1D) {
					player.setVelocity(vel.x, 0, vel.z);
				}
			}
		}
		
		tryHop();
	}
	
	private void tryHop() {
		Vec3d vel = this.getVelocity();
		if (--this.hopTimer <= 0 && this.onGround
				&& (vel.getX() > 0.05D || vel.getZ() > 0.05D
				|| vel.getX() < -0.05D || vel.getZ() < -0.05D)) {
			this.setVelocity(0.25D, vel.y, vel.z);
			float velX = MathHelper.sin(this.yaw * (float) Math.PI / 180.0F);
			float velZ = MathHelper.cos(this.yaw * (float) Math.PI / 180.0F);
			
			this.setVelocity(vel.x + (-0.2F * velX), vel.y, vel.z + (0.2F * velZ));
			this.hopTimer = 15;
		}
	}
	
	private void updateWingAnimationProgress() {
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
	
	public float getWingAnimationProgress(float f) {
		float f1 = this.wingE + ((this.wingB - this.wingE) * f);
		float f2 = this.wingD + ((this.wingC - this.wingD) * f);
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}
	
	public enum BirdType {
		dove("white", MoCSounds.ENTITY_BIRD_AMBIENT_WHITE.event),
		crow("black", MoCSounds.ENTITY_BIRD_AMBIENT_BLACK.event),
//		parrot("green", MoCSoundEvents.ENTITY_BIRD_AMBIENT_GREEN.event), // TODO? make this a different bird, since parrots have been added to vanilla
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
}
