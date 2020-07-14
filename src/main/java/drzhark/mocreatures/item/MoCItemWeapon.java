package drzhark.mocreatures.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCItemWeapon extends MoCItem {

    private float attackDamage;
    private final Item.ToolMaterial material;
    private int specialWeaponType = 0;
    private boolean breakable = false;

    public MoCItemWeapon(String name, Item.ToolMaterial par2ToolMaterial) {
        super(name);
        this.material = par2ToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2ToolMaterial.getMaxUses());
        this.attackDamage = 4F + par2ToolMaterial.getDamageVsEntity();
    }

    /**
     *
     * @param par1
     * @param par2ToolMaterial
     * @param damageType 0 = default, 1 = poison, 2 = slow down, 3 = fire, 4 =
     *        confusion, 5 = blindness
     */
    public MoCItemWeapon(String name, ToolMaterial par2ToolMaterial, int damageType, boolean fragile) {
        this(name, par2ToolMaterial);
        this.specialWeaponType = damageType;
        this.breakable = fragile;
    }

    /**
     * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
     */
    public float getDamageVsEntity() {
        return this.material.getDamageVsEntity();
    }

    public float getStrVsBlock(ItemStack stack, Block block) {
        if (block == Blocks.web) {
            return 15.0F;
        } else {
            Material material = block.getMaterial();
            return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves
                    && material != Material.gourd ? 1.0F : 1.5F;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     *  
     * @param target The Entity being hit
     * @param attacker the attacking entity
     */
    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase target, EntityLivingBase attacker) {
        int i = 1;
        if (this.breakable) {
            i = 10;
        }
        par1ItemStack.damageItem(i, attacker);
        int potionTime = 100;
        switch (this.specialWeaponType) {
            case 1: //poison
                target.addPotionEffect(new PotionEffect(Potion.poison.id, potionTime, 0));
                break;
            case 2: //frost slowdown
                target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, potionTime, 0));
                break;
            case 3: //fire
                target.setFire(10);
                break;
            case 4: //confusion
                target.addPotionEffect(new PotionEffect(Potion.confusion.id, potionTime, 0));
                break;
            case 5: //blindness
                target.addPotionEffect(new PotionEffect(Potion.blindness.id, potionTime, 0));
                break;
            default:
                break;
        }

        return true;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving) {
        par1ItemStack.damageItem(2, par6EntityLiving);
        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    @Override
    public boolean canHarvestBlock(Block par1Block) {
        return par1Block == Blocks.web;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based
     * on material.
     */
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
        if ((double) blockIn.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, playerIn);
        }

        return true;
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName() {
        return this.material.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair The ItemStack to be repaired
     * @param repair The ItemStack that should repair this Item (leather for leather armor, etc.)
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        ItemStack mat = this.material.getRepairItemStack();
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false))
            return true;
        return super.getIsRepairable(toRepair, repair);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @SuppressWarnings("deprecation")
    public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier",
                (double) this.attackDamage, 0));
        return multimap;
    }
}
