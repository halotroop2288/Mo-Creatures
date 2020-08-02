package com.halotroop.mocreatures.common.registry;

import com.halotroop.mocreatures.common.MoCMain;
import com.halotroop.mocreatures.common.entity.ambient.AntEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class MoCEntityTypes {
	public static final EntityType<AntEntity> ANT;
	
	static {
		ANT = FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, AntEntity::new)
				.dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build();
	}
	
	public static void registerAll() {
		registerEntity("ant", ANT);
	}
	
	private static <T extends Entity> void registerEntity(String name, EntityType<T> entry) {
		Registry.register(Registry.ENTITY_TYPE, MoCMain.getID(name), entry);
	}
}
