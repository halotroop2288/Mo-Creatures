package org.mocreatures.mocreatures.client.registry;

import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import org.mocreatures.mocreatures.client.entity.renderer.AntRenderer;
import org.mocreatures.mocreatures.client.entity.renderer.ButterflyRenderer;
import org.mocreatures.mocreatures.client.entity.renderer.GenericBirdRenderer;
import org.mocreatures.mocreatures.common.entity.ambient.AntEntity;
import org.mocreatures.mocreatures.common.entity.ambient.ButterflyEntity;
import org.mocreatures.mocreatures.common.entity.passive.pet.GenericBirdEntity;

public class MoCEntityRenderers {
	public static void registerAll() {
		EntityRendererRegistry.INSTANCE.register(AntEntity.class, AntRenderer::new);
		EntityRendererRegistry.INSTANCE.register(GenericBirdEntity.class, GenericBirdRenderer::new);
		EntityRendererRegistry.INSTANCE.register(ButterflyEntity.class, ButterflyRenderer::new);
	}
}
