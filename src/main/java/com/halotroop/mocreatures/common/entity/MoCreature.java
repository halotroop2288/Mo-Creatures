package com.halotroop.mocreatures.common.entity;

/**
 * Used to label all mobs in the Mo Creatures mod
 * For reference in other classes
 */
public interface MoCreature {
	/**
	 * Used to label specifically flying mobs from the Mo Creatures mod
	 */
	interface Flying extends MoCreature {
		int minFlyingHeight();
		
		int maxFlyingHeight();
	}
}
