package org.mocreatures.mocreatures.common;

import io.github.cottonmc.cotton.logging.ModLogger;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.mocreatures.mocreatures.common.config.MoCSettings;
import org.mocreatures.mocreatures.common.config.MoCreaturesConfig;
import org.mocreatures.mocreatures.common.registry.MoCEntities;
import org.mocreatures.mocreatures.common.registry.MoCSounds;

public class MoCMain implements ModInitializer {
	public static final String MODID = "mocreatures";
	public static ModLogger logger = getLogger("main");
	
	public static ModLogger getLogger(String subtitle) {
		return new ModLogger(MODID + ":" + subtitle);
	}
	
	public static Identifier getID(String name) {
		return new Identifier(MODID, name);
	}
	
	public static Identifier getTexture(String path) {
		return getID("textures/" + path + ".png");
	}
	
	private static void registerCommon() {
		MoCSounds.registerAll();
		MoCEntities.registerAll();
	}
	
	@Override
	public void onInitialize() {
		logger.info("Loading settings...");
		MoCSettings.loadAll();
		MoCreaturesConfig.loadAll();
		logger.info("Loading settings complete!");
		
		logger.devInfo("Registering objects");
		registerCommon();
	}
}
