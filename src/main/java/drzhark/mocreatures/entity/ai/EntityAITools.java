package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAITools {

    protected static boolean IsNearPlayer(EntityLiving entityliving, double d) {
        EntityPlayer entityplayer1 = entityliving.worldObj.getClosestPlayerToEntity(entityliving, d);
        if (entityplayer1 != null) {
            return true;
        }
        return false;
    }

    protected static EntityPlayer getIMoCTameableOwner(IMoCTameable pet) {
        if (pet.getOwnerName() == null || pet.getOwnerName().equals("")) {
            return null;
        }

        for (int i = 0; i < ((EntityLiving) pet).worldObj.playerEntities.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) ((EntityLiving) pet).worldObj.playerEntities.get(i);

            if (pet.getOwnerName().equals(entityplayer.getName())) {
                return entityplayer;
            }
        }
        return null;
    }
}
