package org.mocreatures.mocreatures.client.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.mocreatures.mocreatures.client.entity.renderer.model.ButterflyModel;
import org.mocreatures.mocreatures.common.MoCMain;
import org.mocreatures.mocreatures.common.entity.ambient.ButterflyEntity;

@Environment(EnvType.CLIENT)
public class ButterflyRenderer extends MobEntityRenderer<ButterflyEntity, ButterflyModel> {
	public ButterflyRenderer(EntityRenderDispatcher dispatcher, EntityRendererRegistry.Context context) {
		super(dispatcher, new ButterflyModel(), 1);
	}
	
	@Override
	protected Identifier getTexture(ButterflyEntity entity) {
		return MoCMain.getTexture("entity/" + entity.getButterflyType().name());
	}
}
