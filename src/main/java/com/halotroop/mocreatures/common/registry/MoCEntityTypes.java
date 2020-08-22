package com.halotroop.mocreatures.common.registry;

import com.halotroop.mocreatures.client.entity.renderer.AntRenderer;
import com.halotroop.mocreatures.common.MoCMain;
import com.halotroop.mocreatures.common.entity.ambient.AntEntity;
import com.halotroop.mocreatures.common.entity.animal.GenericBirdEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class MoCEntityTypes {
	public static final EntityType<AntEntity> ANT;
	public static final EntityType<GenericBirdEntity> BIRD;
	
	static {
		ANT = FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, AntEntity::new)
				.dimensions(EntityDimensions.fixed(AntRenderer.SCALE, AntRenderer.SCALE)).build();
		BIRD = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, GenericBirdEntity::new)
				.dimensions(EntityDimensions.fixed(0.575f, 0.75f)).build();
	}
	
	public static void registerAll() {
		registerEntity("ant", ANT);
		registerEntity("bird", BIRD);
	}
	
	private static <T extends Entity> void registerEntity(String name, EntityType<T> entry) {
		Registry.register(Registry.ENTITY_TYPE, MoCMain.getID(name), entry);
	}
}
