package org.mocreatures.mocreatures.common.registry;

import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import org.mocreatures.mocreatures.common.MoCMain;

import java.util.Locale;

public class MoCTags {
	public enum ItemTag {
		HERBIVORE_FOOD, CARNIVORE_FOOD;
		
		public Tag<Item> tag;
		
		ItemTag() {
			this.tag = register(this.name().toLowerCase(Locale.ENGLISH));
		}
		
		private static Tag<Item> register(String name) {
			return new ItemTags.CachingTag(MoCMain.getID(name));
		}
	}
}
