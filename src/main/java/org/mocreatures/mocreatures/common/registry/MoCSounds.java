package org.mocreatures.mocreatures.common.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.mocreatures.mocreatures.common.MoCMain;

import java.util.Locale;

public enum MoCSounds {
	// Put inconsistent names on separate lines
	// TODO: Fix inconsistent names, place appropriate underscores back in || WILL BREAK OLD RESOURCE PACKS!
	ENTITY_BEAR_AMBIENT("bear_grunt"),
	ENTITY_BEAR_DEATH("bear_death"), ENTITY_BEAR_HURT,
	//	ENTITY_BEE_AMBIENT, ENTITY_BEE_UPSET,
	ENTITY_BIRD_AMBIENT_BLACK,
	ENTITY_BIRD_AMBIENT_BLUE,
	ENTITY_BIRD_AMBIENT_GREEN,
	ENTITY_BIRD_AMBIENT_RED,
	ENTITY_BIRD_AMBIENT_WHITE,
	ENTITY_BIRD_AMBIENT_YELLOW,
	//	ENTITY_BIRD_DEATH, BIRD_HURT, // TODO - DrZ team
	ENTITY_CRICKET_AMBIENT, CRICKET_FLY,
	ENTITY_CROCODILE_AMBIENT("croc_grunt"),
	ENTITY_CROCODILE_DEATH("croc_dying"),
	//	ENTITY_CROCODILE_HURT("croc_hurt"), // TODO - DrZ team
	ENTITY_CROCODILE_JAW_SNAP("croc_jaw_snap"),
	ENTITY_CROCODILE_RESTING("croc_resting"),
	ENTITY_CROCODILE_ROLL("croc_roll"),
	ENTITY_DEER_AMBIENT_BABY("deer_b_grunt"),
	ENTITY_DEER_AMBIENT("deer_f_grunt"),
	ENTITY_DEER_DEATH,
	ENTITY_DEER_HURT,
	ENTITY_DOLPHIN_UPSET,
	ENTITY_DUCK_AMBIENT,
	//	ENTITY_DUCK_DEATH, // TODO - DrZ team
	ENTITY_DUCK_HURT,
	ENTITY_DRAGONFLY_AMBIENT,
	ENTITY_ELEPHANT_AMBIENT_BABY("elephant_calf"),
	ENTITY_ELEPHANT_AMBIENT("elephant_grunt"),
	//	ENTITY_ELEPHANT_DEATH, // TODO - DrZ team
	ENTITY_ELEPHANT_HURT,
	//	ENTITY_ENT_AMBIENT, ENTITY_ENT_DEATH, ENTITY_ENT_HURT, // TODO - DrZ team
	ENTITY_FLY_AMBIENT,
	ENTITY_GENERIC_ARMOR_ON("armor_put"),
	GENERIC_ARMOR_OFF,
	ENTITY_GENERIC_DESTROY,
	ENTITY_GENERIC_DRINKING,
	ENTITY_GENERIC_EATING,
	ENTITY_GENERIC_MAGIC_APPEAR("appear_magic"),
	ENTITY_GENERIC_ROPING,
	ENTITY_GENERIC_TRANSFORM,
	//	ENTITY_GENERIC_THUD("tud"), // TODO - DrZ team
	ENTITY_GENERIC_VANISH,
	ENTITY_GENERIC_WHIP,
	ENTITY_GENERIC_WING_FLAP,
	ENTITY_GOAT_AMBIENT("goat_grunt"),
	GOAT_AMBIENT_BABY("goat_kid"),
	GOAT_AMBIENT_FEMALE("goat_female"),
	GOAT_DEATH, GOAT_DIGG, GOAT_EATING, GOAT_HURT, GOAT_SMACK,
	ENTITY_GOLEM_AMBIENT("golem_grunt"),
	GOLEM_ATTACH, GOLEM_DYING, GOLEM_EXPLODE, GOLEM_HURT, GOLEM_SHOOT, GOLEM_WALK,
	//  ENTITY_KITTY_AMBIENT, ENTITY_KITTY_AMBIENT_BABY, ENTITY_KITTY_ANGRY, ENTITY_KITTY_DEATH, ENTITY_KITTY_DEATH_BABY, ENTITY_KITTY_DRINKING, ENTITY_KITTY_EATING,
//	ENTITY_KITTY_HUNGRY, ENTITY_KITTY_HURT, ENTITY_KITTY_HURT_BABY, ENTITY_KITTY_PURR, ENTITY_KITTY_TRAPPED,
//	ENTITY_KITTY_LITTER, // TODO - DrZ team
	ENTITY_KITTY_BED_POURING_MILK("pouring_milk"),
	ENTITY_KITTY_BED_POURING_FOOD("pouring_food"),
	ENTITY_LION_AMBIENT("lion_grunt"),
	ENTITY_LION_AMBIENT_BABY("cub_grunt"),
	ENTITY_LION_DEATH("lion_death"),
	ENTITY_LION_DEATH_BABY("cub_dying"),
	ENTITY_LION_HURT,
	ENTITY_LION_HURT_BABY("cub_hurt"),
	ENTITY_MOUSE_AMBIENT("mice_grunt"),
	ENTITY_MOUSE_DEATH("mice_dying"),
	//	ENTITY_MOUSE_HURT("mice_hurt"), // TODO - DrZ team
	ENTITY_OGRE_AMBIENT, ENTITY_OGRE_DEATH, ENTITY_OGRE_HURT,
	ENTITY_OSTRICH_AMBIENT("ostrich_grunt"),
	ENTITY_OSTRICH_AMBIENT_BABY("ostrich_chick"),
	ENTITY_OSTRICH_DEATH, ENTITY_OSTRICH_HURT,
	ENTITY_RABBIT_LIFT,
	ENTITY_RACCOON_AMBIENT("raccoon_grunt"),
	ENTITY_RACCOON_DEATH, ENTITY_RACCOON_HURT,
	ENTITY_RAT_AMBIENT("rat_grunt"),
	ENTITY_RAT_DEATH, ENTITY_RAT_HURT,
	ENTITY_SCORPION_AMBIENT("scorpion_grunt"),
	ENTITY_SCORPION_CLAW, ENTITY_SCORPION_DEATH, ENTITY_SCORPION_HURT, ENTITY_SCORPION_STING,
	ENTITY_SNAKE_AMBIENT("snake_hiss"),
	ENTITY_SNAKE_ANGRY("snake_upset"),
	ENTITY_SNAKE_DEATH, ENTITY_SNAKE_HURT, ENTITY_SNAKE_RATTLE, ENTITY_SNAKE_SNAP, ENTITY_SNAKE_SWIM,
	ENTITY_TURKEY_AMBIENT, ENTITY_TURKEY_HURT,
	ENTITY_TURTLE_ANGRY("turtle_hissing"),
	//	ENTITY_TURTLE_EATING, // TODO - DrZ team
	ENTITY_WEREWOLF_AMBIENT("werewolf_grunt"),
	ENTITY_WEREWOLF_DEATH, ENTITY_WEREWOLF_HURT,
	ENTITY_WEREWOLF_TRANSFORM("were_transform"),
	//	ENTITY_WEREWOLF_AMBIENT_HUMAN("were_human_grunt"), // TODO - DrZ team
	ENTITY_WEREWOLF_DEATH_HUMAN("were_human_dying"),
	ENTITY_WEREWOLF_HURT_HUMAN("were_human_hurt"),
	ENTITY_WRAITH_AMBIENT, WRAITH_DEATH, WRAITH_HURT,
	ENTITY_WYVERN_AMBIENT("wyvern_grunt"),
	WYVERN_DEATH, WYVERN_HURT, WYVERN_WING_FLAP,
	
	ITEM_RECORD_SHUFFLING("shuffling");
	
	public SoundEvent event;
	public String name;
	
	MoCSounds() {
		this.name = this.name()
				.replaceAll("_", "")
				.replaceAll("ENTITY", "")
				.replaceAll("AMBIENT", "")
				.replaceAll("GENERIC", "")
				.replaceAll("DEATH", "DYING")
				.toLowerCase(Locale.ENGLISH);
		this.event = register(this.name);
	}
	
	MoCSounds(String name) {
		this.name = name.replaceAll("_", "").toLowerCase();
		this.event = register(this.name);
	}
	
	// Touch this class to statically register everything.
	public static void registerAll() {
	}
	
	private static SoundEvent register(String name) {
		Identifier id = MoCMain.getID(name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}
}