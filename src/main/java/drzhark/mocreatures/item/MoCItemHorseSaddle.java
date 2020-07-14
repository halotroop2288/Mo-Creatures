package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MoCItemHorseSaddle extends MoCItem {

    public MoCItemHorseSaddle(String name) {
        super(name);
        this.maxStackSize = 32;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entityliving) {
        if (entityliving instanceof MoCEntityHorse) {
            MoCEntityHorse entityhorse = (MoCEntityHorse) entityliving;
            if (!entityhorse.getIsRideable() && entityhorse.getIsAdult()) {
                entityhorse.setRideable(true);
                itemstack.stackSize--;
                return true;
            }
        }
        return false;
    }
}
