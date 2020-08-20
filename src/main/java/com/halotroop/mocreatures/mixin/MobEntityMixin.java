package com.halotroop.mocreatures.mixin;

import com.halotroop.mocreatures.common.entity.HasHomePosition;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntityMixin implements HasHomePosition {
	private static final TrackedData<BlockPos> HOME_POSITION;
	
	static {
		HOME_POSITION = DataTracker.registerData(MobEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
	}
	
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void addToDataTracker(CallbackInfo ci) {
		((MobEntity) (Object) this).getDataTracker().startTracking(HOME_POSITION, BlockPos.ORIGIN);
	}
	
	@Override
	public BlockPos getHomePosition() {
		return ((MobEntity) (Object) this).getDataTracker().get(HOME_POSITION);
	}
	
	@Override
	public boolean withinMaxHomeDistance(BlockPos pos) {
		return ((MobEntity) (Object) this).getBlockPos().getSquaredDistance(pos.getX(), pos.getY(), pos.getZ(), false) < getMaxHomeDistance();
	}
}
