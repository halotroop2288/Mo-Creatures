package com.halotroop.mocreatures.client.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public abstract class CreatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
	public CreatureRenderer(EntityRenderDispatcher entityRenderDispatcher, M entityModel, float f) {
		super(entityRenderDispatcher, entityModel, f);
	}
}
