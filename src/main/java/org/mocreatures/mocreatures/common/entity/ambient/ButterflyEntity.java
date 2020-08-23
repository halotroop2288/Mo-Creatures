package org.mocreatures.mocreatures.common.entity.ambient;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.mocreatures.mocreatures.common.entity.MoCreature;

public class ButterflyEntity extends InsectEntity.Flying implements MoCreature.Variable {
	private static final TrackedData<Integer> VARIANT;
	
	static {
		VARIANT = DataTracker.registerData(ParrotEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
	
	public ButterflyEntity(EntityType<? extends InsectEntity.Flying> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	public EntityData initialize(IWorld world, LocalDifficulty localDifficulty, SpawnType spawnType,
	                             EntityData entityData, CompoundTag tag) {
		this.setVariant(this.random.nextInt(ButterflyType.values().length));
		return super.initialize(world, localDifficulty, spawnType, entityData, tag);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (!this.world.isClient && isFlying() && this.random.nextInt(200) == 0) {
			setFlying(false);
		}
	}
	
	@Override
	public boolean isAttractedToLight() {
		return getButterflyType().isMoth();
	}
	
	public ButterflyType getButterflyType() {
		return ButterflyType.values()[MathHelper.clamp(this.getVariant(), 0, ButterflyType.values().length)];
	}
	
	@Override
	public int minFlyingHeight() {
		return 2;
	}
	
	@Override
	public int maxFlyingHeight() {
		return 50;
	}
	
	@Override
	public int getVariant() {
		return MathHelper.clamp(this.dataTracker.get(VARIANT), 0, ButterflyType.values().length);
	}
	
	@Override
	public void setVariant(int i) {
		this.dataTracker.set(VARIANT, i);
	}
	
	@Override
	public void writeCustomDataToTag(CompoundTag compoundTag) {
		super.writeCustomDataToTag(compoundTag);
		compoundTag.putInt("Variant", this.getVariant());
	}
	
	@Override
	public void readCustomDataFromTag(CompoundTag compoundTag) {
		super.readCustomDataFromTag(compoundTag);
		this.setVariant(compoundTag.getInt("Variant"));
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(VARIANT, random.nextInt(ButterflyType.values().length));
	}
	
	public enum ButterflyType {
		// Butterflies
		bfagalaisurticae(false), // Small tortoiseshell
		bfargyreushyperbius(false), // Indian Fritillary
		bfathymanefte(false), // Colour Sergeant
		bfcatopsiliapomona(false), // Lemon Emigrant
		bfmorphopeleides(false), // The Emperor
		bfvanessaatalanta(false), // Red Admiral
		bfpierisrapae(false), // Cabbage White
		// Moths
		mothcamptogrammabilineata(true), // Yellow Shell
		mothidiaaemula(true), // Powdered Snout
		moththyatirabatis(true); // Peach Blossom
		
		private final boolean moth;
		
		ButterflyType(boolean moth) {
			this.moth = moth;
		}
		
		public boolean isMoth() {
			return moth;
		}
	}
}
