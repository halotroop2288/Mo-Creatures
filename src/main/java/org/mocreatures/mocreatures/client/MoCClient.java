package org.mocreatures.mocreatures.client;

import net.fabricmc.api.ClientModInitializer;
import org.mocreatures.mocreatures.client.registry.MoCEntityRenderers;

public class MoCClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MoCEntityRenderers.registerAll();
	}
}
