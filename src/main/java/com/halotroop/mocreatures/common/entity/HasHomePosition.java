package com.halotroop.mocreatures.common.entity;

import net.minecraft.util.math.BlockPos;

public interface HasHomePosition {
	BlockPos getHomePosition();
	
	default boolean hasHome() {
		return getHomePosition() != null;
	}
	
	boolean withinMaxHomeDistance(BlockPos pos);
	
	default double getMaxHomeDistance() {
		return 0;
	}
}
