package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.item.MoCEntityEgg;

@SideOnly(Side.CLIENT)
public class MoCRenderEgg extends RenderLiving {

    public MoCRenderEgg(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityEgg entityegg = (MoCEntityEgg) entityliving;
        stretch(entityegg);

        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityEgg entityegg)
    {

        float f = entityegg.getSize() * 0.01F;
        GL11.glScalef(f, f, f);
    }
}
