package com.halotroop.mocreatures.client.entity.renderer;

import com.halotroop.mocreatures.common.entity.ambient.InsectEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModel;
import org.lwjgl.opengl.GL11;

public abstract class InsectRenderer<T extends InsectEntity, M extends EntityModel<T>> extends CreatureRenderer<T, M> {
	public InsectRenderer(EntityRenderDispatcher entityRenderDispatcher, M entityModel, float f) {
		super(entityRenderDispatcher, entityModel, f);
	}
	
	@Override
	public void render(T insect, double d, double e, double f, float g, float h) {
		if (insect.isClimbing()) {
			// rotateAnimal
			GL11.glRotatef(90F, -1, 0.0F, 0.0F);
		}
		
		// stretch
		float sizeFactor = insect.getScaleFactor();
		GL11.glScalef(sizeFactor, sizeFactor, sizeFactor);
		
		super.render(insect, d, e, f, g, h);
	}
}
