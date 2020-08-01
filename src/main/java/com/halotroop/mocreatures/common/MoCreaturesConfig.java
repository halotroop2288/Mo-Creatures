package com.halotroop.mocreatures.common;

import blue.endless.jankson.Comment;
import io.github.cottonmc.cotton.config.ConfigManager;
import io.github.cottonmc.cotton.config.annotations.ConfigFile;

public enum MoCreaturesConfig {
	ANCHOVY(12, 6, 6, 1),
	ANGELFISH(12, 6, 6, 1),
	ANGLER(12, 6, 6, 1),
	ANT(7, 4, 4, 1),
	BASS(10, 4, 4, 1),
//	BEE(6,3,2,1),
	BIGGOLEM(3, 1, 1, 1),
	BIRD(15, 4, 3, 2),
	BLACKBEAR(6, 4, 3, 1),
	BOAR(8,3,2,2),
//	BUNNY(8,4,3,2),
	BUTTERFLY(8,3,3,1),
	CAVEOGRE(5,3,2,1),
	CLOWNFISH(12,6,6,1),
//	COD(10,4,4,1),
	CRAB(8,2,2,1),
	CRICKET(8,2,2,1),
	CROCODILE(6,2,2,1),
	DEER(8,2,2,1),
//	DOLPHIN(6,3,4,2),
	DRAGONFLY(6,2,2,1),
	DUCK(8,3,2,1),
	ELEPHANT(4,3,1,1),
	ENT(4,3,2,1),
	FIREFLY(8,3,2,1),
	FIREOGRE(6,3,2,1),
//	FISHY(12,6,6,1),
	FLY(8,2,2,1),
//	FOX(8,2,1,1),
	GOAT(8,2,3,1),
	GOLDFISH(12,6,6,1),
	GREENOGRE(8,3,2,1),
	GRIZZLYBEAR(6,4,2,1),
	HELLRAT(6,4,4,1),
	HIPPOTANG(12,6,6,1),
	HORSEMOB(8,3,3,1),
	JELLYFISH(8,4,4,1),
//	KITTY(8,3,2,1),
	KOMODODRAGON(8,2,2,1),
	LEOPARD(6,4,2,1),
	LION(6,4,2,1),
	MAGGOT(8,2,2,1),
	MANDERIN(12,6,6,1),
	MANTARAY(10,3,2,1),
	MANTICORE(8,3,3,1),
	MINIGOLEM(6,2,3,1),
	MOLE(7,3,2,1),
	MOUSE(7,2,2,1),
	OSTRICH(4,3,1,1),
	PANDABEAR(6,4,3,1),
	PANTHER(6,4,2,1),
	PIRANHA(4,4,3,1),
	RACCOON(8,2,2,1),
	RAT(7,2,2,1),
	ROACH(8,2,2,1),
	SALMON(10,4,4,1),
	SCORPION(6,3,3,1),
	SHARK(6,3,2,1),
	SILVERSKELETON(6,4,4,1),
	SNAIL(7,2,2,1),
	SNAKE(8,3,2,1),
	STINGRAY(10,3,2,1),
	TIGER(6,4,2,1),
	TURKEY(8,2,2,1),
//	TURTLE(6,3,2,1),
	WEREWOLF(8,3,4,1),
//	WILDHORSE(8,4,4,1),
//	WILDPOLARBEAR(6,4,2,1),
	WRAITH(6,3,4,1),
//	WILDWOLF(8,3,3,1),
	WYVERN(8,3,3,1);
	
	public CreatureConfig config;
	
	MoCreaturesConfig(int frequency, int maxChunk, int maxSpawn, int minSpawn) {
		this.config = ConfigManager.loadConfig(CreatureConfig.class,
				"MoCreatures/Creatures/" + this.name().toLowerCase() + ".cfg"); // fake defaults are "true,1,1,1,1"
		
		if (!config.canSpawn || config.maxChunk != maxChunk // Sets real defaults if not already defaulted
				|| config.maxSpawn != maxSpawn || config.minSpawn != minSpawn) {
			this.config.frequency= frequency;
			this.config.minSpawn = minSpawn;
			this.config.maxSpawn = maxSpawn;
			this.config.maxChunk = maxChunk;
			this.config.canSpawn = true;
			this.save();
		}
	}
	
	public void save() {
		ConfigManager.saveConfig(this.config, "MoCreatures/Creatures/" + this.name().toLowerCase() + ".cfg");
	}
	
	static void loadAll() {
		// TOUCH
	}
	
	public static void saveAll() {
		for (MoCreaturesConfig config : MoCreaturesConfig.values()) {
			config.save();
		}
	}
	
	@ConfigFile(name = "MoCreature", extension = ".cfg")
	public static class CreatureConfig {
		@Comment(value = "Whether this creature can spawn naturally or not.")
		public boolean canSpawn = true;
		@Comment(value = "How frequently this creature spawns")
		public int frequency = 1;
		@Comment(value = "The maximum amount of this creature to spawn per chunk")
		public int maxChunk  = 1;
		@Comment(value = "The maximum amount of this creature to spawn in the world at once")
		public int maxSpawn  = 1;
		@Comment(value = "The minimum amount of this creature to spawn in the world at once")
		public int minSpawn  = 1;
	}
}
