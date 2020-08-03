package com.halotroop.mocreatures.client;

import com.halotroop.mocreatures.client.registry.MoCEntityRenderers;
import net.fabricmc.api.ClientModInitializer;

public class MoCClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MoCEntityRenderers.registerAll();
	}
}
