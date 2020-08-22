package org.mocreatures.mocreatures.client.entity.renderer;

import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.Identifier;
import org.mocreatures.mocreatures.client.entity.renderer.model.AntModel;
import org.mocreatures.mocreatures.common.MoCMain;
import org.mocreatures.mocreatures.common.entity.ambient.AntEntity;

public class AntRenderer extends InsectRenderer<AntEntity, AntModel> {
	public static final float SCALE = 0.25F;
	
	public AntRenderer(EntityRenderDispatcher dispatcher, EntityRendererRegistry.Context context) {
		super(dispatcher, new AntModel(), SCALE);
	}
	
	@Override
	protected Identifier getTexture(AntEntity entity) {
		return MoCMain.getTexture("entity/ant");
	}
}
