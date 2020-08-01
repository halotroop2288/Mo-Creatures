package com.halotroop.mocreatures.common;

import blue.endless.jankson.Comment;
import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;

public class MoCSettings {
	public static CreatureGeneralSettings creatureGeneralSettings;
	public static GlobalSettings globalSettings;
	public static MonsterGeneralSettings monsterGeneralSettings;
	public static OwnershipSettings ownershipSettings;
	public static WaterMobGeneralSettings waterMobGeneralSettings;
	
	static void loadAll() {
		creatureGeneralSettings = loadConfig(CreatureGeneralSettings.class, "creature-general");
		globalSettings = loadConfig(GlobalSettings.class, "global");
		monsterGeneralSettings = loadConfig(MonsterGeneralSettings.class, "monster-general");
		ownershipSettings = loadConfig(OwnershipSettings.class, "ownership");
		waterMobGeneralSettings = loadConfig(WaterMobGeneralSettings.class, "water-mob-general");
	}
	
	public static void saveAll() {
		saveConfig(creatureGeneralSettings, "creature-general");
		saveConfig(globalSettings, "global");
		saveConfig(monsterGeneralSettings, "monster-general");
		saveConfig(ownershipSettings, "ownership");
		saveConfig(waterMobGeneralSettings, "water-mob-general");
	}
	
	public static <T> T loadConfig(Class<T> clazz, String name) {
		return ConfigManager.loadConfig(clazz, "MoCreatures/" + name + ".cfg");
	}
	
	public static void saveConfig(Object object, String name) {
		ConfigManager.saveConfig(object, "MoCreatures/" + name + ".cfg");
	}
	
	@ConfigFile(name = "MoC-Creature-General-Settings", extension = ".cfg")
	public static class CreatureGeneralSettings {
		@Comment(value = "Allows creatures to attack horses.")
		public boolean attackHorses = false;
		
		@Comment(value = "Allows creatures to attack wolves.")
		public boolean attackWolves = false;
		public boolean destroyDrops = false;
		
		@Comment(value = "Makes horse breeding simpler.")
		public boolean easyBreeding = false;
		public boolean elephantBulldozer = true;
		
		@Comment(value = "Allows creatures to attack other creatures. Not recommended if despawning is off.")
		public boolean enableHunters = true;
		public boolean killAllVillagers = false;
		
		@Comment(value = "A value of 33 means mother wyverns have a 33% chance to drop an egg.")
		public int motherWyvernEggDropChance = 33;
		
		@Comment(value = "A value of 3 means ostriches have a 3% chance to drop an egg.")
		public int ostrichEggDropChance = 3;
		
		@Comment(value = "A value of 25 means Horses/Ostriches/Scorpions/etc." +
				"have a 25% chance to drop a rare item such as a heart of darkness, unicorn, bone when killed." +
				"Raise the value if you want higher drop rates.")
		public int rareItemDropChance = 25;
		public boolean staticBed = true;
		public boolean staticLitter = true;
		
		@Comment(value = "A value of 10 means wyverns have a 10% chance to drop an egg.")
		public int wyvernEggDropChance = 10;
		
		@Comment(value = "The percent for spawning a Zebra")
		public int zebraChance = 10;
		
		@Comment(value = "The maximum light level threshold used to determine whether or not to despawn a farm animal.")
		public int despawnLightLevel = 2;
	}
	
	@ConfigFile(name = "MoC-Global-Settings", extension = ".cfg")
	public static class GlobalSettings {
		@Comment(value = "Allows you to instantly spawn MoCreatures from GUI.")
		public boolean allowInstaSpawn = false;
		
		@Comment(value = "Animate Textures")
		public boolean animateTextures = false;
		
		@Comment(value = "Turns on verbose logging")
		public boolean debug = false;
		
		@Comment(value = "Shows Pet Health")
		public boolean displayPetHealth = true;
		
		@Comment(value = "Shows Pet Emotes")
		public boolean displayPetEmotes = true;
		
		@Comment(value = "Shows Pet Name")
		public boolean displayPetName = true;
		
		@Comment(value = "If true, it will force despawns on all creatures including vanilla for a more dynamic" +
				"experience while exploring world." +
				"If false, all passive mocreatures will not despawn to prevent other creatures from taking over." +
				"Note: if you experience issues with farm animals despawning, adjust despawnLightLevel.")
		public boolean forceDespawns = false;
		public int particleFX = 3;
	}
	
	@ConfigFile(name = "MoC-Monster-General-Settings", extension = ".cfg")
	public static class MonsterGeneralSettings {
		@Comment(value = "The chance percentage of spawning Cave Ogres at depth of 50 in the Overworld")
		public int caveOgreChance = 75;
		
		@Comment(value = "The block destruction radius of Cave Ogres")
		public double caveOgreStrength = 3.0;
		
		@Comment(value = "The chance percentage of spawning Fire ogres in the Overworld")
		public int fireOgreChance = 25;
		
		@Comment(value = "The block destruction radius of Fire Ogres")
		public double fireOgreStrength = 2.0;
		
		@Comment(value = "The block radius where ogres 'smell' players")
		public int ogreAttackRange = 12;
		
		@Comment(value = "The block destruction radius of green Ogres")
		public double ogreStrength = 2.5;
		
		@Comment(value = "Allows Big Golems to break blocks.")
		public boolean golemDestroyBlocks = true;
	}
	
	@ConfigFile(name = "MoC-Water-Mob-General-Settings", extension = ".cfg")
	public static class OwnershipSettings {
		@Comment(value = "Assigns player as owner for each creature they tame." +
				"Only the owner can interact with the tamed creature.")
		public boolean enableOwnership = false;
		
		@Comment(value = "Allows players to remove a tamed creatures owner essentially un-taming it.")
		public boolean enableResetOwnerScroll = false;
		
		@Comment(value = "Max tamed creatures an op can have. Requires enableOwnership to be set to true.")
		public int maxTamedPerOP = 20;
		
		@Comment(value = "Max tamed creatures a player can have. Requires enableOwnership to be set to true.")
		public int getMaxTamedPerPlayer = 10;
	}
	
	public static class WaterMobGeneralSettings {
		@Comment(value = "Allows water creatures to attack dolphins.")
		public boolean attackDolphins = false;
	}
}
