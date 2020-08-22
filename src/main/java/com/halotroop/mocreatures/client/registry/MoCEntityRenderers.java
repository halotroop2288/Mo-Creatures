package com.halotroop.mocreatures.client.registry;

import com.halotroop.mocreatures.client.entity.renderer.AntRenderer;
import com.halotroop.mocreatures.client.entity.renderer.GenericBirdRenderer;
import com.halotroop.mocreatures.common.entity.ambient.AntEntity;
import com.halotroop.mocreatures.common.entity.animal.GenericBirdEntity;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;

public class MoCEntityRenderers {
	public static void registerAll() {
		EntityRendererRegistry.INSTANCE.register(AntEntity.class, AntRenderer::new);
		EntityRendererRegistry.INSTANCE.register(GenericBirdEntity.class, GenericBirdRenderer::new);
	}
}
