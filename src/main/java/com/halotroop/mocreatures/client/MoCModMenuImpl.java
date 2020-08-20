package com.halotroop.mocreatures.client;

import com.halotroop.mocreatures.common.config.MoCSettings;
import com.halotroop.mocreatures.common.config.MoCreaturesConfig;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;

import java.util.function.Function;

import static com.halotroop.mocreatures.common.MoCMain.MODID;
import static com.halotroop.mocreatures.common.config.MoCSettings.*;

public class MoCModMenuImpl implements ModMenuApi {
	private static final String OPT_STR = "option." + MODID + '.';
	
	@Override
	public String getModId() {
		return MODID;
	}
	
	@Override
	public Function<Screen, ? extends Screen> getConfigScreenFactory() {
		return (modListScreen) -> {
			ConfigBuilder builder = ConfigBuilder.create()
					.setTitle("title." + MODID + ".config") // title.mocreatures.config -> Mo' Creatures
					.setParentScreen(modListScreen) // ModMenu's ModList screen
					.setSavingRunnable(() -> { // Save with my handler of Cotton Config
						MoCSettings.saveAll();
						MoCreaturesConfig.saveAll();
					});
			
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
			
			// TODO: Add localization support for the rest of these tooltips?
			// Creature Settings
			ConfigCategory creatureGeneralSettingsCategory = builder.getOrCreateCategory(MODID + ".creature_general.config")
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "attack_horses",
							creatureGeneralSettings.attackHorses).requireRestart()
							.setTooltip("Allows creatures to attack horses.").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "attack_wolves",
							creatureGeneralSettings.attackWolves).requireRestart()
							.setTooltip("Allows creatures to attack wolves.").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "destroy_drops",
							creatureGeneralSettings.destroyDrops).build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "easy_breeding",
							creatureGeneralSettings.easyBreeding)
							.setTooltip("Makes horse breeding simpler.").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "elephant_bulldozer",
							creatureGeneralSettings.elephantBulldozer).build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "enable_hunters",
							creatureGeneralSettings.enableHunters)
							.setTooltip("Allows creatures to attack other creatures.",
									"Not recommended if despawning is off.").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "kill_all_villagers",
							creatureGeneralSettings.killAllVillagers).build())
					.addEntry(entryBuilder.startIntSlider(OPT_STR + "mother_wyvern_egg_drop_chance", // Handled by data now?
							creatureGeneralSettings.motherWyvernEggDropChance, 0, 100)
							.setTooltip("A value of 33 means mother wyverns", "have a 33% chance to drop an egg.")
							.build())
					.addEntry(entryBuilder.startIntSlider(OPT_STR + "ostrich_egg_drop_chance", // Handled by data now?
							creatureGeneralSettings.ostrichEggDropChance, 0, 100)
							.setTooltip("A value of 3 means ostriches have a 3% chance to drop an egg.")
							.build())
					.addEntry(entryBuilder.startIntSlider(OPT_STR + "rare_item_drop_chance", // Handled by data now?
							creatureGeneralSettings.rareItemDropChance, 0, 100)
							.setTooltip("A value of 25 means Horses/Ostriches/Scorpions/etc.",
									"have a 25% chance to drop a rare item such as",
									"a heart of darkness, unicorn, bone when killed.",
									"Raise the value if you want higher drop rates.").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "static_bed",
							creatureGeneralSettings.staticBed).requireRestart().build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "static_litter",
							creatureGeneralSettings.staticLitter).requireRestart().build())
					.addEntry(entryBuilder.startIntSlider(OPT_STR + "wyvern_egg_drop_chance", // Handled by data now?
							creatureGeneralSettings.wyvernEggDropChance, 0, 100)
							.setTooltip("A value of 10 means wyverns have a 10% chance to drop an egg.").build())
					.addEntry(entryBuilder.startIntSlider(OPT_STR + "zebra_chance", // Handled by data now?
							creatureGeneralSettings.zebraChance, 0, 100)
							.setTooltip("The percent for spawning a zebra.").build())
					.addEntry(entryBuilder.startIntSlider(OPT_STR + "despawn_light_level",
							creatureGeneralSettings.despawnLightLevel, 0, 15)
							.setTooltip("The maximum light level threshold used to determine",
									"whether or not to despawn a farm animal.").build());
			
			// Creature spawn settings
			for (MoCreaturesConfig config : MoCreaturesConfig.values()) {
				String optName = "option." + MODID + '.';
				String entityName = "entity." + MODID + '.' + config.name().toLowerCase() + ".name";
				
				SubCategoryBuilder subCategory = entryBuilder
						.startSubCategory(entityName)
						.setTooltip("Spawn settings for " + I18n.translate(entityName));
				subCategory.add(entryBuilder.startBooleanToggle(optName + "can_spawn",
						config.config.canSpawn).build());
				subCategory.add(entryBuilder.startIntField(optName + "frequency",
						config.config.frequency).build());
				subCategory.add(entryBuilder.startIntField(optName + "max_chunk",
						config.config.maxChunk).build());
				subCategory.add(entryBuilder.startIntField(optName + "max_spawn",
						config.config.maxSpawn).build());
				subCategory.add(entryBuilder.startIntField(optName + "min_spawn",
						config.config.minSpawn).build());
				
				builder.getOrCreateCategory(MODID + ".creature_spawns.config").addEntry(subCategory.build());
			}
			
			// Monster General Settings
			ConfigCategory monsterGeneralSettingsCategory = builder.getOrCreateCategory(MODID + ".monster.config");
			
			SubCategoryBuilder caveOgreSubCategory = entryBuilder
					.startSubCategory("entity." + MODID + '.' + "caveogre.name");
			SubCategoryBuilder fireOgreSubCategory = entryBuilder
					.startSubCategory("entity." + MODID + '.' + "fireogre.name");
			
			caveOgreSubCategory.add(entryBuilder.startIntSlider(OPT_STR + "ogre.chance",
					monsterGeneralSettings.caveOgreChance, 0, 100)
					.setTooltip("The chance percentage of spawning Cave ogres at depth of 50 in the Overworld").build());
			caveOgreSubCategory.add(entryBuilder.startDoubleField(OPT_STR + "ogre.strength",
					monsterGeneralSettings.caveOgreStrength)
					.setTooltip("The block destruction radius of Cave Ogres").build());
			fireOgreSubCategory.add(entryBuilder.startIntSlider(OPT_STR + "ogre.chance",
					monsterGeneralSettings.fireOgreChance, 0, 100)
					.setTooltip("The chance percentage of spawning Fire ogres in the Overworld").build());
			fireOgreSubCategory.add(entryBuilder.startDoubleField(OPT_STR + "ogre.strength",
					monsterGeneralSettings.fireOgreChance)
					.setTooltip("The block destruction radius of Fire Ogres").build());
			
			monsterGeneralSettingsCategory
					.addEntry(caveOgreSubCategory.build())
					.addEntry(fireOgreSubCategory.build())
					.addEntry(entryBuilder.startDoubleField(OPT_STR + "ogre_strength",
							monsterGeneralSettings.ogreStrength).setTooltip("The block destruction radius of green Ogres")
							.build())
					.addEntry(entryBuilder.startIntField(OPT_STR + "ogre_attack_range",
							monsterGeneralSettings.ogreAttackRange)
							.setTooltip("The block radius where ogres 'smell' players").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "golem_destroy_blocks",
							monsterGeneralSettings.golemDestroyBlocks)
							.setTooltip("Allows Big Golems to break blocks.").build())
			;
			// Ownership Settings
			ConfigCategory ownershipSettingsCategory = builder.getOrCreateCategory(MODID + ".ownership.config")
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "enable_ownership",
							ownershipSettings.enableOwnership)
							.setTooltip(
									"Assigns player as owner for each creature they tame.",
									"Only the owner can interact with the tamed creature.").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "enable_reset_owner_scroll",
							ownershipSettings.enableResetOwnerScroll)
							.setTooltip("Allows players to remove a tamed creatures owner essentially un-taming it.").build())
					.addEntry(entryBuilder.startIntField(OPT_STR + "max_tamed_per_op",
							ownershipSettings.maxTamedPerOP)
							.setTooltip("Max tamed creatures an op can have." +
									"Requires enableOwnership to be set to true.").build())
					.addEntry(entryBuilder.startIntField(OPT_STR + "max_tamed_per_player",
							ownershipSettings.maxTamedPerOP)
							.setTooltip("Max tamed creatures a player can have." +
									"Requires enableOwnership to be set to true.").build());
			
			// Global Settings
			ConfigCategory globalSettingsCategory = builder.getOrCreateCategory(MODID + ".global.config")
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "allow_insta_spawn",
							globalSettings.allowInstaSpawn)
							.setTooltip("Allows you to instantly spawn MoCreatures from GUI.").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "animate_textures",
							globalSettings.animateTextures).setTooltip("Animate Textures").build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "display_pet_health",
							globalSettings.displayPetHealth)
							.setTooltip(I18n.translate(OPT_STR + "display_pet_health")).build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "display_pet_emotes",
							globalSettings.displayPetEmotes)
							.setTooltip(I18n.translate(OPT_STR + "display_pet_emotes")).build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "display_pet_name",
							globalSettings.displayPetName)
							.setTooltip(I18n.translate(OPT_STR + "display_pet_name")).build())
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "force_despawns",
							globalSettings.forceDespawns)
							.setTooltip("If true, it will force despawns on all creatures including",
									"vanilla for a more dynamic experience while exploring world.",
									"If false, all passive mocreatures will not despawn to prevent",
									"other creatures from taking over.",
									"Note: if you experience issues with farm animals despawning,",
									"adjust despawnLightLevel.").build())
					.addEntry(entryBuilder.startIntField(OPT_STR + "particle_fx",
							globalSettings.particleFX).build());
			// Water Mob General Settings
			ConfigCategory waterMobGeneralSettingsCategory = builder.getOrCreateCategory(MODID + ".water_mob.config")
					.addEntry(entryBuilder.startBooleanToggle(OPT_STR + "attack_dolphins",
							waterMobGeneralSettings.attackDolphins)
							.setTooltip("Allows water creatures to attack dolphins.").build());
			
			builder.setFallbackCategory(creatureGeneralSettingsCategory);
			return builder.build();
		};
	}
}
