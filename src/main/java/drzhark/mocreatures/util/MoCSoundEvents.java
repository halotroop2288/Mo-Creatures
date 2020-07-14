package drzhark.mocreatures.util;

import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MoCSoundEvents {

    public static final SoundEvent ENTITY_BEAR_AMBIENT;
    public static final SoundEvent ENTITY_BEAR_DEATH;
    public static final SoundEvent ENTITY_BEAR_HURT;
    public static final SoundEvent ENTITY_BEE_AMBIENT;
    public static final SoundEvent ENTITY_BEE_UPSET;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_BLACK;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_BLUE;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_GREEN;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_RED;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_WHITE;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_YELLOW;
    public static final SoundEvent ENTITY_BIRD_DEATH;
    public static final SoundEvent ENTITY_BIRD_HURT;
    public static final SoundEvent ENTITY_CRICKET_AMBIENT;
    public static final SoundEvent ENTITY_CRICKET_FLY;
    public static final SoundEvent ENTITY_CROCODILE_AMBIENT;
    public static final SoundEvent ENTITY_CROCODILE_DEATH;
    public static final SoundEvent ENTITY_CROCODILE_HURT;
    public static final SoundEvent ENTITY_CROCODILE_JAWSNAP;
    public static final SoundEvent ENTITY_CROCODILE_RESTING;
    public static final SoundEvent ENTITY_CROCODILE_ROLL;
    public static final SoundEvent ENTITY_DEER_AMBIENT_BABY;
    public static final SoundEvent ENTITY_DEER_AMBIENT;
    public static final SoundEvent ENTITY_DEER_DEATH;
    public static final SoundEvent ENTITY_DEER_HURT;
    public static final SoundEvent ENTITY_DOLPHIN_DEATH;
    public static final SoundEvent ENTITY_DOLPHIN_HURT;
    public static final SoundEvent ENTITY_DOLPHIN_AMBIENT;
    public static final SoundEvent ENTITY_DOLPHIN_UPSET;
    public static final SoundEvent ENTITY_DUCK_AMBIENT;
    public static final SoundEvent ENTITY_DUCK_DEATH;
    public static final SoundEvent ENTITY_DUCK_HURT;
    public static final SoundEvent ENTITY_DRAGONFLY_AMBIENT;
    public static final SoundEvent ENTITY_ELEPHANT_AMBIENT_BABY;
    public static final SoundEvent ENTITY_ELEPHANT_AMBIENT;
    public static final SoundEvent ENTITY_ELEPHANT_DEATH;
    public static final SoundEvent ENTITY_ELEPHANT_HURT;
    public static final SoundEvent ENTITY_ENT_AMBIENT;
    public static final SoundEvent ENTITY_ENT_DEATH;
    public static final SoundEvent ENTITY_ENT_HURT;
    public static final SoundEvent ENTITY_FLY_AMBIENT;
    public static final SoundEvent ENTITY_FOX_AMBIENT;
    public static final SoundEvent ENTITY_FOX_DEATH;
    public static final SoundEvent ENTITY_FOX_HURT;
    public static final SoundEvent ENTITY_GENERIC_ARMOR_ON;
    public static final SoundEvent ENTITY_GENERIC_ARMOR_OFF;
    public static final SoundEvent ENTITY_GENERIC_DESTROY;
    public static final SoundEvent ENTITY_GENERIC_DRINKING;
    public static final SoundEvent ENTITY_GENERIC_EATING;
    public static final SoundEvent ENTITY_GENERIC_MAGIC_APPEAR;
    public static final SoundEvent ENTITY_GENERIC_ROPING;
    public static final SoundEvent ENTITY_GENERIC_TRANSFORM;
    public static final SoundEvent ENTITY_GENERIC_TUD;
    public static final SoundEvent ENTITY_GENERIC_VANISH;
    public static final SoundEvent ENTITY_GENERIC_WHIP;
    public static final SoundEvent ENTITY_GENERIC_WINGFLAP;
    public static final SoundEvent ENTITY_GOAT_AMBIENT;
    public static final SoundEvent ENTITY_GOAT_AMBIENT_BABY;
    public static final SoundEvent ENTITY_GOAT_AMBIENT_FEMALE;
    public static final SoundEvent ENTITY_GOAT_DEATH;
    public static final SoundEvent ENTITY_GOAT_DIGG;
    public static final SoundEvent ENTITY_GOAT_EATING;
    public static final SoundEvent ENTITY_GOAT_HURT;
    public static final SoundEvent ENTITY_GOAT_SMACK;
    public static final SoundEvent ENTITY_GOLEM_AMBIENT;
    public static final SoundEvent ENTITY_GOLEM_ATTACH;
    public static final SoundEvent ENTITY_GOLEM_DYING;
    public static final SoundEvent ENTITY_GOLEM_EXPLODE;
    public static final SoundEvent ENTITY_GOLEM_HURT;
    public static final SoundEvent ENTITY_GOLEM_SHOOT;
    public static final SoundEvent ENTITY_GOLEM_WALK;
    public static final SoundEvent ENTITY_HORSE_MAD;
    public static final SoundEvent ENTITY_HORSE_AMBIENT;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_DONKEY;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_GHOST;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_ZEBRA;
    public static final SoundEvent ENTITY_HORSE_ANGRY;
    public static final SoundEvent ENTITY_HORSE_ANGRY_DONKEY;
    public static final SoundEvent ENTITY_HORSE_ANGRY_GHOST;
    public static final SoundEvent ENTITY_HORSE_ANGRY_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_ANGRY_ZEBRA;
    public static final SoundEvent ENTITY_HORSE_DEATH;
    public static final SoundEvent ENTITY_HORSE_DEATH_DONKEY;
    public static final SoundEvent ENTITY_HORSE_DEATH_GHOST;
    public static final SoundEvent ENTITY_HORSE_DEATH_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_HURT;
    public static final SoundEvent ENTITY_HORSE_HURT_DONKEY;
    public static final SoundEvent ENTITY_HORSE_HURT_GHOST;
    public static final SoundEvent ENTITY_HORSE_HURT_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_HURT_ZEBRA;
    public static final SoundEvent ENTITY_KITTY_AMBIENT;
    public static final SoundEvent ENTITY_KITTY_AMBIENT_BABY;
    public static final SoundEvent ENTITY_KITTY_ANGRY;
    public static final SoundEvent ENTITY_KITTY_DEATH;
    public static final SoundEvent ENTITY_KITTY_DEATH_BABY;
    public static final SoundEvent ENTITY_KITTY_DRINKING;
    public static final SoundEvent ENTITY_KITTY_EATING;
    public static final SoundEvent ENTITY_KITTY_HUNGRY;
    public static final SoundEvent ENTITY_KITTY_HURT;
    public static final SoundEvent ENTITY_KITTY_HURT_BABY;
    public static final SoundEvent ENTITY_KITTY_LITTER;
    public static final SoundEvent ENTITY_KITTY_PURR;
    public static final SoundEvent ENTITY_KITTY_TRAPPED;
    public static final SoundEvent ENTITY_KITTYBED_POURINGMILK;
    public static final SoundEvent ENTITY_KITTYBED_POURINGFOOD;
    public static final SoundEvent ENTITY_LION_AMBIENT;
    public static final SoundEvent ENTITY_LION_AMBIENT_BABY;
    public static final SoundEvent ENTITY_LION_DEATH;
    public static final SoundEvent ENTITY_LION_DEATH_BABY;
    public static final SoundEvent ENTITY_LION_HURT;
    public static final SoundEvent ENTITY_LION_HURT_BABY;
    public static final SoundEvent ENTITY_MOUSE_AMBIENT;
    public static final SoundEvent ENTITY_MOUSE_DEATH;
    public static final SoundEvent ENTITY_MOUSE_HURT;
    public static final SoundEvent ENTITY_OGRE_AMBIENT;
    public static final SoundEvent ENTITY_OGRE_DEATH;
    public static final SoundEvent ENTITY_OGRE_HURT;
    public static final SoundEvent ENTITY_OSTRICH_AMBIENT;
    public static final SoundEvent ENTITY_OSTRICH_AMBIENT_BABY;
    public static final SoundEvent ENTITY_OSTRICH_DEATH;
    public static final SoundEvent ENTITY_OSTRICH_HURT;
    public static final SoundEvent ENTITY_RABBIT_DEATH;
    public static final SoundEvent ENTITY_RABBIT_HURT;
    public static final SoundEvent ENTITY_RABBIT_LIFT;
    public static final SoundEvent ENTITY_RACCOON_AMBIENT;
    public static final SoundEvent ENTITY_RACCOON_DEATH;
    public static final SoundEvent ENTITY_RACCOON_HURT;
    public static final SoundEvent ENTITY_RAT_AMBIENT;
    public static final SoundEvent ENTITY_RAT_DEATH;
    public static final SoundEvent ENTITY_RAT_HURT;
    public static final SoundEvent ENTITY_SCORPION_AMBIENT;
    public static final SoundEvent ENTITY_SCORPION_CLAW;
    public static final SoundEvent ENTITY_SCORPION_DEATH;
    public static final SoundEvent ENTITY_SCORPION_HURT;
    public static final SoundEvent ENTITY_SCORPION_STING;
    public static final SoundEvent ENTITY_SNAKE_AMBIENT;
    public static final SoundEvent ENTITY_SNAKE_ANGRY;
    public static final SoundEvent ENTITY_SNAKE_DEATH;
    public static final SoundEvent ENTITY_SNAKE_HURT;
    public static final SoundEvent ENTITY_SNAKE_RATTLE;
    public static final SoundEvent ENTITY_SNAKE_SNAP;
    public static final SoundEvent ENTITY_SNAKE_SWIM;
    public static final SoundEvent ENTITY_TURKEY_AMBIENT;
    public static final SoundEvent ENTITY_TURKEY_DEATH;
    public static final SoundEvent ENTITY_TURKEY_HURT;
    public static final SoundEvent ENTITY_TURTLE_AMBIENT;
    public static final SoundEvent ENTITY_TURTLE_ANGRY;
    public static final SoundEvent ENTITY_TURTLE_DEATH;
    public static final SoundEvent ENTITY_TURTLE_EATING;
    public static final SoundEvent ENTITY_TURTLE_HURT;
    public static final SoundEvent ENTITY_WEREWOLF_AMBIENT;
    public static final SoundEvent ENTITY_WEREWOLF_AMBIENT_HUMAN;
    public static final SoundEvent ENTITY_WEREWOLF_DEATH;
    public static final SoundEvent ENTITY_WEREWOLF_DEATH_HUMAN;
    public static final SoundEvent ENTITY_WEREWOLF_HURT_HUMAN;
    public static final SoundEvent ENTITY_WEREWOLF_HURT;
    public static final SoundEvent ENTITY_WEREWOLF_TRANSFORM;
    public static final SoundEvent ENTITY_WRAITH_AMBIENT;
    public static final SoundEvent ENTITY_WOLF_AMBIENT;
    public static final SoundEvent ENTITY_WOLF_DEATH;
    public static final SoundEvent ENTITY_WOLF_HURT;
    public static final SoundEvent ENTITY_WRAITH_DEATH;
    public static final SoundEvent ENTITY_WRAITH_HURT;
    public static final SoundEvent ENTITY_WYVERN_AMBIENT;
    public static final SoundEvent ENTITY_WYVERN_DEATH;
    public static final SoundEvent ENTITY_WYVERN_HURT;
    public static final SoundEvent ENTITY_WYVERN_WINGFLAP;
    public static final SoundEvent ITEM_RECORD_SHUFFLING;

    private static SoundEvent getRegisteredSoundEvent(String id) {
        final ResourceLocation resourceLocation = new ResourceLocation("mocreatures", id);
        SoundEvent soundevent = (SoundEvent)SoundEvent.REGISTRY.getObject(resourceLocation);
        if (soundevent == null) {
            return GameRegistry.register(new SoundEvent(resourceLocation).setRegistryName(resourceLocation));
        }

        return soundevent;
    }

    static {
        if (!Bootstrap.isRegistered()) {
            throw new RuntimeException("Accessed Sounds before Bootstrap!");
        } else {
            ENTITY_BEAR_AMBIENT = getRegisteredSoundEvent("beargrunt");
            ENTITY_BEAR_DEATH = getRegisteredSoundEvent("beardeath");
            ENTITY_BEAR_HURT = getRegisteredSoundEvent("bearhurt");
            ENTITY_BEE_AMBIENT = getRegisteredSoundEvent("bee");
            ENTITY_BEE_UPSET = getRegisteredSoundEvent("beeupset");
            ENTITY_BIRD_AMBIENT_BLACK = getRegisteredSoundEvent("birdblack");
            ENTITY_BIRD_AMBIENT_BLUE = getRegisteredSoundEvent("birdblue");
            ENTITY_BIRD_AMBIENT_GREEN = getRegisteredSoundEvent("birdgreen");
            ENTITY_BIRD_AMBIENT_RED = getRegisteredSoundEvent("birdred");
            ENTITY_BIRD_AMBIENT_YELLOW = getRegisteredSoundEvent("birdyellow");
            ENTITY_BIRD_AMBIENT_WHITE = getRegisteredSoundEvent("birdwhite");
            ENTITY_BIRD_DEATH = getRegisteredSoundEvent("birddying"); // TODO
            ENTITY_BIRD_HURT = getRegisteredSoundEvent("birdhurt"); // TODO
            ENTITY_CRICKET_AMBIENT = getRegisteredSoundEvent("cricket");
            ENTITY_CRICKET_FLY = getRegisteredSoundEvent("cricketfly");
            ENTITY_CROCODILE_AMBIENT = getRegisteredSoundEvent("crocgrunt");
            ENTITY_CROCODILE_DEATH = getRegisteredSoundEvent("crocdying");
            ENTITY_CROCODILE_HURT = getRegisteredSoundEvent("crochurt"); // TODO
            ENTITY_CROCODILE_JAWSNAP = getRegisteredSoundEvent("crocjawsnap");
            ENTITY_CROCODILE_RESTING = getRegisteredSoundEvent("crocresting");
            ENTITY_CROCODILE_ROLL = getRegisteredSoundEvent("crocroll");
            ENTITY_DEER_AMBIENT_BABY = getRegisteredSoundEvent("deerbgrunt");
            ENTITY_DEER_AMBIENT = getRegisteredSoundEvent("deerfgrunt");
            ENTITY_DEER_DEATH = getRegisteredSoundEvent("deerdying");
            ENTITY_DEER_HURT = getRegisteredSoundEvent("deerhurt"); // TODO
            ENTITY_DOLPHIN_AMBIENT = getRegisteredSoundEvent("dolphin");
            ENTITY_DOLPHIN_DEATH = getRegisteredSoundEvent("dolphindying");
            ENTITY_DOLPHIN_HURT = getRegisteredSoundEvent("dolphinhurt");
            ENTITY_DOLPHIN_UPSET = getRegisteredSoundEvent("dolphinupset");
            ENTITY_DUCK_AMBIENT = getRegisteredSoundEvent("duck");
            ENTITY_DUCK_DEATH = getRegisteredSoundEvent("duckdying"); // TODO
            ENTITY_DUCK_HURT = getRegisteredSoundEvent("duckhurt");
            ENTITY_DRAGONFLY_AMBIENT = getRegisteredSoundEvent("dragonfly");
            ENTITY_ENT_AMBIENT = getRegisteredSoundEvent("entgrunt"); // TODO
            ENTITY_ENT_DEATH = getRegisteredSoundEvent("entdying"); // TODO
            ENTITY_ENT_HURT = getRegisteredSoundEvent("enthurt"); // TODO
            ENTITY_ELEPHANT_AMBIENT_BABY = getRegisteredSoundEvent("elephantcalf");
            ENTITY_ELEPHANT_AMBIENT = getRegisteredSoundEvent("elephantgrunt");
            ENTITY_ELEPHANT_DEATH = getRegisteredSoundEvent("elephantdying"); // TODO
            ENTITY_ELEPHANT_HURT = getRegisteredSoundEvent("elephanthurt");
            ENTITY_FLY_AMBIENT = getRegisteredSoundEvent("fly");
            ENTITY_FOX_AMBIENT = getRegisteredSoundEvent("foxcall");
            ENTITY_FOX_DEATH = getRegisteredSoundEvent("foxdying");
            ENTITY_FOX_HURT = getRegisteredSoundEvent("foxhurt");
            ENTITY_GENERIC_ARMOR_ON = getRegisteredSoundEvent("armorput");
            ENTITY_GENERIC_ARMOR_OFF = getRegisteredSoundEvent("armoroff");
            ENTITY_GENERIC_DESTROY = getRegisteredSoundEvent("destroy");
            ENTITY_GENERIC_DRINKING = getRegisteredSoundEvent("drinking");
            ENTITY_GENERIC_EATING = getRegisteredSoundEvent("eating");
            ENTITY_GENERIC_MAGIC_APPEAR = getRegisteredSoundEvent("appearmagic");
            ENTITY_GENERIC_ROPING = getRegisteredSoundEvent("roping");
            ENTITY_GENERIC_TRANSFORM = getRegisteredSoundEvent("transform");
            ENTITY_GENERIC_TUD = getRegisteredSoundEvent("tud"); // TODO
            ENTITY_GENERIC_VANISH = getRegisteredSoundEvent("vanish");
            ENTITY_GENERIC_WHIP = getRegisteredSoundEvent("whip");
            ENTITY_GENERIC_WINGFLAP = getRegisteredSoundEvent("wingflap");
            ENTITY_GOAT_AMBIENT = getRegisteredSoundEvent("goatgrunt");
            ENTITY_GOAT_AMBIENT_BABY = getRegisteredSoundEvent("goatkid");
            ENTITY_GOAT_AMBIENT_FEMALE = getRegisteredSoundEvent("goatfemale");
            ENTITY_GOAT_DEATH = getRegisteredSoundEvent("goatdying");
            ENTITY_GOAT_HURT = getRegisteredSoundEvent("goathurt");
            ENTITY_GOAT_DIGG = getRegisteredSoundEvent("goatdigg");
            ENTITY_GOAT_EATING = getRegisteredSoundEvent("goateating");
            ENTITY_GOAT_SMACK = getRegisteredSoundEvent("goatsmack");
            ENTITY_GOLEM_AMBIENT = getRegisteredSoundEvent("golemgrunt");
            ENTITY_GOLEM_ATTACH = getRegisteredSoundEvent("golemattach");
            ENTITY_GOLEM_DYING = getRegisteredSoundEvent("golemdying");
            ENTITY_GOLEM_EXPLODE = getRegisteredSoundEvent("golemexplode");
            ENTITY_GOLEM_HURT = getRegisteredSoundEvent("golemhurt");
            ENTITY_GOLEM_SHOOT = getRegisteredSoundEvent("golemshoot");
            ENTITY_GOLEM_WALK = getRegisteredSoundEvent("golemwalk");
            ENTITY_HORSE_MAD = getRegisteredSoundEvent("horsemad");
            ENTITY_HORSE_AMBIENT = getRegisteredSoundEvent("horsegrunt");
            ENTITY_HORSE_AMBIENT_DONKEY = getRegisteredSoundEvent("horsegrunt"); // TODO
            ENTITY_HORSE_AMBIENT_GHOST = getRegisteredSoundEvent("horsegruntghost");
            ENTITY_HORSE_AMBIENT_UNDEAD = getRegisteredSoundEvent("horsegruntundead");
            ENTITY_HORSE_AMBIENT_ZEBRA = getRegisteredSoundEvent("zebragrunt");
            ENTITY_HORSE_ANGRY = getRegisteredSoundEvent("horsemad");
            ENTITY_HORSE_ANGRY_DONKEY = getRegisteredSoundEvent("donkeyhurt");
            ENTITY_HORSE_ANGRY_GHOST = getRegisteredSoundEvent("horsemadghost");
            ENTITY_HORSE_ANGRY_UNDEAD = getRegisteredSoundEvent("horsemadundead");
            ENTITY_HORSE_ANGRY_ZEBRA = getRegisteredSoundEvent("zebrahurt");
            ENTITY_HORSE_DEATH = getRegisteredSoundEvent("horsedying");
            ENTITY_HORSE_DEATH_DONKEY = getRegisteredSoundEvent("donkeydying");
            ENTITY_HORSE_DEATH_GHOST = getRegisteredSoundEvent("horsedyingghost");
            ENTITY_HORSE_DEATH_UNDEAD = getRegisteredSoundEvent("horsedyingundead");
            ENTITY_HORSE_HURT = getRegisteredSoundEvent("horsehurt");
            ENTITY_HORSE_HURT_DONKEY = getRegisteredSoundEvent("donkeyhurt");
            ENTITY_HORSE_HURT_GHOST = getRegisteredSoundEvent("horsehurtghost");
            ENTITY_HORSE_HURT_UNDEAD = getRegisteredSoundEvent("horsehurtundead");
            ENTITY_HORSE_HURT_ZEBRA = getRegisteredSoundEvent("zebrahurt");
            ENTITY_KITTY_AMBIENT = getRegisteredSoundEvent("kittygrunt");
            ENTITY_KITTY_AMBIENT_BABY = getRegisteredSoundEvent("kittengrunt");
            ENTITY_KITTY_ANGRY = getRegisteredSoundEvent("kittyupset");
            ENTITY_KITTY_DEATH = getRegisteredSoundEvent("kittydying");
            ENTITY_KITTY_DEATH_BABY = getRegisteredSoundEvent("kittendying");
            ENTITY_KITTY_DRINKING = getRegisteredSoundEvent("kittyeatingm");
            ENTITY_KITTY_EATING = getRegisteredSoundEvent("kittyfood");
            ENTITY_KITTY_HUNGRY = getRegisteredSoundEvent("kittyeatingf");
            ENTITY_KITTY_HURT = getRegisteredSoundEvent("kittyhurt");
            ENTITY_KITTY_HURT_BABY = getRegisteredSoundEvent("kittenhurt");
            ENTITY_KITTY_LITTER = getRegisteredSoundEvent("kittylitter"); // TODO
            ENTITY_KITTY_PURR = getRegisteredSoundEvent("kittypurr");
            ENTITY_KITTY_TRAPPED = getRegisteredSoundEvent("kittytrapped");
            ENTITY_KITTYBED_POURINGFOOD = getRegisteredSoundEvent("pouringfood");
            ENTITY_KITTYBED_POURINGMILK = getRegisteredSoundEvent("pouringmilk");
            ENTITY_LION_AMBIENT = getRegisteredSoundEvent("liongrunt");
            ENTITY_LION_AMBIENT_BABY = getRegisteredSoundEvent("cubgrunt");
            ENTITY_LION_DEATH = getRegisteredSoundEvent("liondeath");
            ENTITY_LION_DEATH_BABY = getRegisteredSoundEvent("cubdying");
            ENTITY_LION_HURT = getRegisteredSoundEvent("lionhurt");
            ENTITY_LION_HURT_BABY = getRegisteredSoundEvent("cubhurt");
            ENTITY_MOUSE_AMBIENT = getRegisteredSoundEvent("micegrunt");
            ENTITY_MOUSE_DEATH = getRegisteredSoundEvent("micedying");
            ENTITY_MOUSE_HURT = getRegisteredSoundEvent("micehurt"); // TODO
            ENTITY_OGRE_AMBIENT = getRegisteredSoundEvent("ogre");
            ENTITY_OGRE_DEATH = getRegisteredSoundEvent("ogredying");
            ENTITY_OGRE_HURT = getRegisteredSoundEvent("ogrehurt");
            ENTITY_OSTRICH_AMBIENT = getRegisteredSoundEvent("ostrichgrunt");
            ENTITY_OSTRICH_AMBIENT_BABY = getRegisteredSoundEvent("ostrichchick");
            ENTITY_OSTRICH_DEATH = getRegisteredSoundEvent("ostrichdying");
            ENTITY_OSTRICH_HURT = getRegisteredSoundEvent("ostrichhurt");
            ENTITY_RABBIT_DEATH = getRegisteredSoundEvent("rabbitdeath");
            ENTITY_RABBIT_HURT = getRegisteredSoundEvent("rabbithurt");
            ENTITY_RABBIT_LIFT = getRegisteredSoundEvent("rabbitlift");
            ENTITY_RACCOON_AMBIENT = getRegisteredSoundEvent("raccoongrunt");
            ENTITY_RACCOON_DEATH = getRegisteredSoundEvent("raccoondying");
            ENTITY_RACCOON_HURT = getRegisteredSoundEvent("raccoonhurt");
            ENTITY_RAT_AMBIENT = getRegisteredSoundEvent("ratgrunt");
            ENTITY_RAT_DEATH = getRegisteredSoundEvent("ratdying");
            ENTITY_RAT_HURT = getRegisteredSoundEvent("rathurt");
            ENTITY_SCORPION_AMBIENT = getRegisteredSoundEvent("scorpiongrunt");
            ENTITY_SCORPION_CLAW = getRegisteredSoundEvent("scorpionclaw");
            ENTITY_SCORPION_DEATH = getRegisteredSoundEvent("scorpiondying");
            ENTITY_SCORPION_HURT = getRegisteredSoundEvent("scorpionhurt");
            ENTITY_SCORPION_STING = getRegisteredSoundEvent("scorpionsting");
            ENTITY_SNAKE_AMBIENT = getRegisteredSoundEvent("snakehiss");
            ENTITY_SNAKE_ANGRY = getRegisteredSoundEvent("snakeupset");
            ENTITY_SNAKE_DEATH = getRegisteredSoundEvent("snakedying");
            ENTITY_SNAKE_HURT = getRegisteredSoundEvent("snakehurt");
            ENTITY_SNAKE_RATTLE = getRegisteredSoundEvent("snakerattle");
            ENTITY_SNAKE_SNAP = getRegisteredSoundEvent("snakesnap");
            ENTITY_SNAKE_SWIM = getRegisteredSoundEvent("snakeswim");
            ENTITY_TURKEY_AMBIENT = getRegisteredSoundEvent("turkey");
            ENTITY_TURKEY_DEATH = getRegisteredSoundEvent("turkeyhurt");
            ENTITY_TURKEY_HURT = getRegisteredSoundEvent("turkeyhurt");
            ENTITY_TURTLE_AMBIENT = getRegisteredSoundEvent("turtlegrunt"); // TODO
            ENTITY_TURTLE_ANGRY = getRegisteredSoundEvent("turtlehissing");
            ENTITY_TURTLE_DEATH = getRegisteredSoundEvent("turtledying");
            ENTITY_TURTLE_EATING = getRegisteredSoundEvent("turtleeating");
            ENTITY_TURTLE_HURT = getRegisteredSoundEvent("turtlehurt");
            ENTITY_WEREWOLF_AMBIENT_HUMAN = getRegisteredSoundEvent("werehumangrunt"); // TODO
            ENTITY_WEREWOLF_DEATH_HUMAN = getRegisteredSoundEvent("werehumandying");
            ENTITY_WEREWOLF_HURT_HUMAN = getRegisteredSoundEvent("werehumanhurt");
            ENTITY_WEREWOLF_AMBIENT = getRegisteredSoundEvent("werewolfgrunt");
            ENTITY_WEREWOLF_DEATH = getRegisteredSoundEvent("werewolfdying");
            ENTITY_WEREWOLF_HURT = getRegisteredSoundEvent("werewolfhurt");
            ENTITY_WEREWOLF_TRANSFORM = getRegisteredSoundEvent("weretransform");
            ENTITY_WOLF_AMBIENT = getRegisteredSoundEvent("wolfgrunt");
            ENTITY_WOLF_DEATH = getRegisteredSoundEvent("wolfdeath");
            ENTITY_WOLF_HURT = getRegisteredSoundEvent("wolfhurt");
            ENTITY_WRAITH_AMBIENT = getRegisteredSoundEvent("wraith");
            ENTITY_WRAITH_DEATH = getRegisteredSoundEvent("wraithdying");
            ENTITY_WRAITH_HURT = getRegisteredSoundEvent("wraithhurt");
            ENTITY_WYVERN_AMBIENT = getRegisteredSoundEvent("wyverngrunt");
            ENTITY_WYVERN_DEATH = getRegisteredSoundEvent("wyverndying");
            ENTITY_WYVERN_HURT = getRegisteredSoundEvent("wyvernhurt");
            ENTITY_WYVERN_WINGFLAP = getRegisteredSoundEvent("wyvernwingflap");
            ITEM_RECORD_SHUFFLING = getRegisteredSoundEvent("shuffling");
        }
    }
}