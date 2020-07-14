package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCItemRecord extends ItemRecord {

    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation("mocreatures", "shuffling");

    public MoCItemRecord(String name) {
        super(name);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setUnlocalizedName(name);
        GameRegistry.registerItem(this, name);
        if (!MoCreatures.isServer())
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                    .register(this, 0, new ModelResourceLocation("mocreatures:" + name, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    /**
     * Return the title for this record.
     */
    public String getRecordTitle() {
        return "MoC - " + this.recordName;
    }

    @Override
    public ResourceLocation getRecordResource(String name) {
        return RECORD_SHUFFLE_RESOURCE;
    }
}
