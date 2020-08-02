package com.halotroop.mocreatures.common;

import com.halotroop.mocreatures.common.config.MoCSettings;
import com.halotroop.mocreatures.common.config.MoCreaturesConfig;
import com.halotroop.mocreatures.common.registry.MoCEntityTypes;
import io.github.cottonmc.cotton.logging.ModLogger;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class MoCMain implements ModInitializer {
	public static final String MODID = "mocreatures";
	public static ModLogger logger = new ModLogger(MODID);
	
	public static Identifier getID(String name) {
		return new Identifier(MODID, name);
	}
	
	public static Identifier getTexture(String path) {
		return getID("textures/entity/");
	}
	
	@Override
	public void onInitialize() {
		logger.info("Loading settings...");
		MoCSettings.loadAll();
		MoCreaturesConfig.loadAll();
		logger.info("Loading settings complete!");
		
		logger.info("Registering objects");
		registerCommon();
	}
	
	private static void registerCommon() {
		MoCEntityTypes.registerAll();
	}
}
