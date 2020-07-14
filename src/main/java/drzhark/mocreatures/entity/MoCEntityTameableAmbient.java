package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class MoCEntityTameableAmbient extends MoCEntityAmbient implements IMoCTameable {

    public MoCEntityTameableAmbient(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(30, -1); // PetId
    }

    @Override
    public int getOwnerPetId() {
        return this.dataWatcher.getWatchableObjectInt(30);
    }

    @Override
    public void setOwnerPetId(int i) {
        this.dataWatcher.updateObject(30, i);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        //before ownership check
        if ((itemstack != null) && getIsTamed() && ((itemstack.getItem() == MoCreatures.scrollOfOwner)) && MoCreatures.proxy.enableResetOwnership
                && MoCTools.isThisPlayerAnOP(entityplayer)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwner("");

            }
            return true;
        }
        //if the player interacting is not the owner, do nothing!
        if (MoCreatures.proxy.enableOwnership && getOwnerName() != null && !getOwnerName().equals("")
                && !entityplayer.getName().equals(getOwnerName()) && !MoCTools.isThisPlayerAnOP(entityplayer)) {
            return true;
        }

        //changes name
        if (MoCreatures.isServer() && itemstack != null && getIsTamed()
                && (itemstack.getItem() == MoCreatures.medallion || itemstack.getItem() == Items.book || itemstack.getItem() == Items.name_tag)) {
            if (MoCTools.tameWithName(entityplayer, this)) {
                return true;
            }
            return false;
        }

        //sets it free, untamed
        if ((itemstack != null) && getIsTamed() && ((itemstack.getItem() == MoCreatures.scrollFreedom))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwner("");
                this.setPetName("");
                this.dropMyStuff();
                this.setTamed(false);
            }

            return true;
        }

        //removes owner, any other player can claim it by renaming it
        if ((itemstack != null) && getIsTamed() && ((itemstack.getItem() == MoCreatures.scrollOfSale))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwner("");
            }
            return true;
        }

        if ((itemstack != null) && getIsTamed() && isMyHealFood(itemstack)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            this.worldObj.playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            if (MoCreatures.isServer()) {
                this.setHealth(getMaxHealth());
            }
            return true;
        }

        //stores in fishnet
        if (itemstack != null && itemstack.getItem() == MoCreatures.fishnet && itemstack.getItemDamage() == 0 && this.canBeTrappedInNet()) {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            if (MoCreatures.isServer()) {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerName());
                if (petData != null) {
                    petData.setInAmulet(this.getOwnerPetId(), true);
                }
                MoCTools.dropAmulet(this, 1);
                this.isDead = true;
            }

            return true;
        }

        //heals
        if ((itemstack != null) && getIsTamed() && isMyHealFood(itemstack)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            this.worldObj.playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            if (MoCreatures.isServer()) {
                this.setHealth(getMaxHealth());
            }
            return true;
        }

        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == Items.shears)) {
            if (MoCreatures.isServer()) {
                dropMyStuff();
            }

            return true;
        }

        return super.interact(entityplayer);
    }

    // Fixes despawn issue when chunks unload and duplicated mounts when disconnecting on servers
    @Override
    public void setDead() {
        if (MoCreatures.isServer() && getIsTamed() && getHealth() > 0 && !this.riderIsDisconnecting) {
            return;
        }
        super.setDead();
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on
     * status
     */
    @Override
    public void playTameEffect(boolean par1) {
        EnumParticleTypes particleType = EnumParticleTypes.HEART;

        if (!par1) {
            particleType = EnumParticleTypes.SMOKE_NORMAL;
        }

        for (int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(particleType, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D
                    + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        if (getOwnerPetId() != -1) {
            nbttagcompound.setInteger("PetId", this.getOwnerPetId());
        }
        if (this instanceof IMoCTameable && getIsTamed() && MoCreatures.instance.mapData != null) {
            MoCreatures.instance.mapData.updateOwnerPet(this);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.hasKey("PetId")) {
            setOwnerPetId(nbttagcompound.getInteger("PetId"));
        }
        if (this.getIsTamed() && nbttagcompound.hasKey("PetId")) {
            MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerName());
            if (petData != null) {
                NBTTagList tag = petData.getOwnerRootNBT().getTagList("TamedList", 10);
                for (int i = 0; i < tag.tagCount(); i++) {
                    NBTTagCompound nbt = tag.getCompoundTagAt(i);
                    if (nbt.getInteger("PetId") == nbttagcompound.getInteger("PetId")) {
                        // update amulet flag
                        nbt.setBoolean("InAmulet", false);
                        // check if cloned and if so kill
                        if (nbt.hasKey("Cloned")) {
                            // entity was cloned
                            nbt.removeTag("Cloned"); // clear flag
                            this.setTamed(false);
                            this.setDead();
                        }
                    }
                }
            } else // no pet data was found, mocreatures.dat could of been deleted so reset petId to -1
            {
                this.setOwnerPetId(-1);
            }
        }
    }

    @Override
    public float getPetHealth() {
        return this.getHealth();
    }

    @Override
    public boolean isRiderDisconnecting() {
        return this.riderIsDisconnecting;
    }

    @Override
    public void setRiderDisconnecting(boolean flag) {
        this.riderIsDisconnecting = flag;
    }

    @Override
    public boolean allowLeashing() {
        return this.getIsTamed();
    }

    /**
     * Used to spawn hearts at this location
     */
    @Override
    public void spawnHeart() {
        double var2 = this.rand.nextGaussian() * 0.02D;
        double var4 = this.rand.nextGaussian() * 0.02D;
        double var6 = this.rand.nextGaussian() * 0.02D;

        this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D
                + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var2, var4, var6);
    }

	@Override
	public boolean readytoBreed() {
		return false;
	}
}
