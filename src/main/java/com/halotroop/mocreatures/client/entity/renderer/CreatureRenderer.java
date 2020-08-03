package com.halotroop.mocreatures.client.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;

public abstract class CreatureRenderer<T extends MobEntity, M extends EntityModel<T>> extends MobEntityRenderer<T, M> {
	public CreatureRenderer(EntityRenderDispatcher entityRenderDispatcher, M entityModel, float scale) {
		super(entityRenderDispatcher, entityModel, scale);
	}
}
