package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityLitterBox extends EntityLiving {

    public int littertime;

    public MoCEntityLitterBox(World world) {
        super(world);
        setSize(1.0F, 0.3F);
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("litterbox.png");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D); // setMaxHealth
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // pickedUp - 0 false 1 true
        this.dataWatcher.addObject(17, Byte.valueOf((byte) 0)); // usedLitter - 0 false 1 true
    }

    public boolean getPickedUp() {
        return (this.dataWatcher.getWatchableObjectByte(17) == 1);
    }

    public boolean getUsedLitter() {
        return (this.dataWatcher.getWatchableObjectByte(16) == 1);
    }

    public void setPickedUp(boolean flag) {
        if (this.worldObj.isRemote) {
            return;
        }
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(17, Byte.valueOf(input));
    }

    public void setUsedLitter(boolean flag) {
        if (this.worldObj.isRemote) {
            return;
        }
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(16, Byte.valueOf(input));
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected String getDeathSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return null;
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public double getYOffset() {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (((this.getRidingEntity() instanceof EntityPlayer) && !this.worldObj.isRemote) || this.getRidingEntity() == MoCreatures.proxy.getPlayer())//MoCProxy.mc().thePlayer)
        {
            setPickedUp(true);
            return ((EntityPlayer) this.getRidingEntity()).isSneaking() ? 0.25 : 0.5F;
        }
        return super.getYOffset();

    }

    @Override
    public void handleStatusUpdate(byte byte0) {
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null)
                && MoCreatures.isServer()
                && ((itemstack.getItem() == Items.stone_pickaxe) || (itemstack.getItem() == Items.wooden_pickaxe)
                        || (itemstack.getItem() == Items.iron_pickaxe) || (itemstack.getItem() == Items.golden_pickaxe) || (itemstack.getItem() == Items.diamond_pickaxe))) {
            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.litterbox));
            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            setDead();
            return true;
        }

        if ((itemstack != null) && MoCreatures.isServer() && (itemstack.getItem() == Item.getItemFromBlock(Blocks.sand))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setUsedLitter(false);
            this.littertime = 0;
            return true;
        } else {
            this.rotationYaw = entityplayer.rotationYaw;
            if (this.getRidingEntity() == null) {
                if (MoCreatures.isServer()) {
                    mountEntity(entityplayer);
                }
            } else {
                if (MoCreatures.isServer()) {
                    this.mountEntity(null);
                }
            }
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }
    }

    @Override
    public void moveEntity(double d, double d1, double d2) {
        if ((this.getRidingEntity() != null) || !this.onGround || !MoCreatures.proxy.staticLitter) {
            if (!this.worldObj.isRemote) {
                super.moveEntity(d, d1, d2);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.onGround) {
            setPickedUp(false);
        }
        if (getUsedLitter() && MoCreatures.isServer()) {
            this.littertime++;
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(12D, 4D, 12D));
            for (int i = 0; i < list.size(); i++) {
                Entity entity = list.get(i);
                if (!(entity instanceof EntityMob)) {
                    continue;
                }
                EntityMob entitymob = (EntityMob) entity;
                entitymob.setAttackTarget(this);
                if (entitymob instanceof EntityCreeper) {
                    ((EntityCreeper) entitymob).setCreeperState(-1);
                }
                if (entitymob instanceof MoCEntityOgre) {
                    ((MoCEntityOgre) entitymob).smashCounter = 0;
                }
            }

        }
        if (this.littertime > 5000 && MoCreatures.isServer()) {
            setUsedLitter(false);
            this.littertime = 0;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.setBoolean("UsedLitter", getUsedLitter());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        setUsedLitter(nbttagcompound.getBoolean("UsedLitter"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        return false;
    }
}
