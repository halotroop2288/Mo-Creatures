package org.mocreatures.mocreatures.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import org.mocreatures.mocreatures.client.entity.renderer.AntRenderer;
import org.mocreatures.mocreatures.common.MoCMain;
import org.mocreatures.mocreatures.common.entity.ambient.AntEntity;
import org.mocreatures.mocreatures.common.entity.ambient.ButterflyEntity;
import org.mocreatures.mocreatures.common.entity.passive.pet.GenericBirdEntity;

public class MoCEntities {
	public static final EntityType<AntEntity> ANT;
	public static final EntityType<GenericBirdEntity> BIRD;
	public static final EntityType<ButterflyEntity> BUTTERFLY;
	
	static {
		ANT = FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, AntEntity::new)
				.dimensions(EntityDimensions.fixed(AntRenderer.SCALE, AntRenderer.SCALE)).build();
		BIRD = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, GenericBirdEntity::new)
				.dimensions(EntityDimensions.fixed(0.575F, 0.75F)).build();
		BUTTERFLY = FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, ButterflyEntity::new)
				.dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build();
	}
	
	public static void registerAll() {
		registerEntity("ant", ANT);
		registerEntity("bird", BIRD);
		registerEntity("butterfly", BUTTERFLY);
	}
	
	private static <T extends Entity> void registerEntity(String name, EntityType<T> entry) {
		Registry.register(Registry.ENTITY_TYPE, MoCMain.getID(name), entry);
	}
}
