package com.halotroop.mocreatures.client.entity.renderer.model;

import net.minecraft.client.model.ModelPart;

public interface ModelHelper {
	default void setRotation(ModelPart part, float pitch, float yaw, float roll) {
		part.pitch = pitch;
		part.yaw = yaw;
		part.roll = roll;
	}
}
