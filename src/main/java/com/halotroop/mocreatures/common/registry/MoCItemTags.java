package com.halotroop.mocreatures.common.registry;

import com.halotroop.mocreatures.common.MoCMain;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;

public class MoCItemTags {
	public static final Tag<Item> HERBIVORE_FOOD = register("herbivore_food");
	public static final Tag<Item> CARNIVORE_FOOD = register("carnivore_food");
	
	private static Tag<Item> register(String name) {
		return new ItemTags.CachingTag(MoCMain.getID(name));
	}
}
