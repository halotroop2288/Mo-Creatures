package org.mocreatures.mocreatures.client.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.Identifier;
import org.mocreatures.mocreatures.client.entity.renderer.model.GenericBirdModel;
import org.mocreatures.mocreatures.common.MoCMain;
import org.mocreatures.mocreatures.common.entity.passive.pet.GenericBirdEntity;

@Environment(EnvType.CLIENT)
public class GenericBirdRenderer extends CreatureRenderer<GenericBirdEntity, EntityModel<GenericBirdEntity>> {
	public GenericBirdRenderer(EntityRenderDispatcher entityRenderDispatcher, EntityRendererRegistry.Context context) {
		super(entityRenderDispatcher, new GenericBirdModel(), 1);
	}
	
	@Override
	protected Identifier getTexture(GenericBirdEntity bird) {
		return MoCMain.getTexture("entity/bird" + bird.getColor());
	}
	
	@Override
	protected float getAnimationProgress(GenericBirdEntity bird, float f) {
		return bird.getWingAnimationProgress(f);
	}
}
