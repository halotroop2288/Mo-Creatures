package com.halotroop.mocreatures.common;

import io.github.cottonmc.cotton.logging.ModLogger;
import net.fabricmc.api.ModInitializer;

public class MoCMain implements ModInitializer {
	public static final String MODID = "mocreatures";
	public static ModLogger logger = new ModLogger(MODID);
	
	@Override
	public void onInitialize() {
		logger.info("Loading settings...");
		MoCSettings.loadAll();
		MoCreaturesConfig.loadAll();
		logger.info("Loading settings complete!");
	}
}
