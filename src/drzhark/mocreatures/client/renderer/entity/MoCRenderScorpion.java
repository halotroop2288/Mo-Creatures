package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;

@SideOnly(Side.CLIENT)
public class MoCRenderScorpion extends MoCRenderAnimal {
    public MoCRenderScorpion(MoCModelScorpion modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityScorpion entityscorpion = (MoCEntityScorpion) entityliving;
        super.doRenderLiving(entityscorpion, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityScorpion entityscorpion = (MoCEntityScorpion) entityliving;

        if (entityscorpion.climbing())
        {
            rotateAnimal(entityscorpion);
        }

        if (!entityscorpion.getIsAdult())
        {
            stretch(entityscorpion);
            if (entityscorpion.getIsPicked())
            {
                upsideDown(entityscorpion);
            }
        }
        else
        {
            adjustHeight(entityscorpion);
        }
    }

    protected void upsideDown(EntityLiving entityliving)
    {
        GL11.glRotatef(-90F, -1F, 0.0F, 0.0F);

        if (entityliving.ridingEntity == MoCClientProxy.mc.thePlayer)
        {
            GL11.glTranslatef(-0.55F, -1.9F, -0.7F);

        }
        else
        {
            GL11.glTranslatef(-1.555F, -0.5F, -0.5F);

        }

    }

    protected void adjustHeight(EntityLiving entityliving)
    {
        GL11.glTranslatef(0.0F, -0.2F, 0.0F);
    }

    protected void rotateAnimal(EntityLiving entityliving)
    {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(MoCEntityScorpion entityscorpion)
    {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult())
        {
            f = entityscorpion.getEdad() * 0.01F;
        }
        GL11.glScalef(f, f, f);
    }

}
