package com.halotroop.mocreatures.client.registry;

import com.halotroop.mocreatures.client.entity.renderer.AntRenderer;
import com.halotroop.mocreatures.common.entity.ambient.AntEntity;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;

public class MoCEntityRenderers {
	public static void registerAll() {
		EntityRendererRegistry.INSTANCE.register(AntEntity.class, AntRenderer::new);
	}
}
