package com.halotroop.mocreatures.client.entity.renderer;

import com.halotroop.mocreatures.client.entity.renderer.model.AntModel;
import com.halotroop.mocreatures.common.MoCMain;
import com.halotroop.mocreatures.common.entity.ambient.AntEntity;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.Identifier;

public class AntRenderer extends InsectRenderer<AntEntity, AntModel> {
	public AntRenderer(EntityRenderDispatcher dispatcher, EntityRendererRegistry.Context context) {
		super(dispatcher, new AntModel(), 0.25f);
	}
	
	@Override
	protected Identifier getTexture(AntEntity entity) {
		return MoCMain.getTexture("entity/ant.png");
	}
}
