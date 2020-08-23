package org.mocreatures.mocreatures.client.entity.renderer.model;

import net.minecraft.client.model.ModelPart;

public class ModelTools {
	public static void setPartRotation(ModelPart part, float pitch, float yaw, float roll) {
		part.pitch = pitch;
		part.yaw = yaw;
		part.roll = roll;
	}
}
