package com.halotroop.mocreatures.common;

import com.halotroop.mocreatures.common.config.MoCSettings;
import com.halotroop.mocreatures.common.config.MoCreaturesConfig;
import com.halotroop.mocreatures.common.registry.MoCEntityTypes;
import com.halotroop.mocreatures.common.registry.MoCSoundEvents;
import io.github.cottonmc.cotton.logging.ModLogger;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

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
		return getID("textures/" + path);
	}
	
	private static void registerCommon() {
		MoCSoundEvents.registerAll();
		MoCEntityTypes.registerAll();
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
}
