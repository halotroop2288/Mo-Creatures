package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelKittyBed;
import drzhark.mocreatures.client.model.MoCModelKittyBed2;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;

@SideOnly(Side.CLIENT)
public class MoCRenderKittyBed extends RenderLiving {

    public MoCModelKittyBed kittybed;

    private int mycolor;

    public static float fleeceColorTable[][] = { { 1.0F, 1.0F, 1.0F }, { 0.95F, 0.7F, 0.2F }, { 0.9F, 0.5F, 0.85F }, { 0.6F, 0.7F, 0.95F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.7F, 0.8F }, { 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.6F, 0.7F }, { 0.7F, 0.4F, 0.9F }, { 0.2F, 0.4F, 0.8F }, { 0.5F, 0.4F, 0.3F }, { 0.4F, 0.5F, 0.2F }, { 0.8F, 0.3F, 0.3F }, { 0.1F, 0.1F, 0.1F } };

    public MoCRenderKittyBed(MoCModelKittyBed modelkittybed, MoCModelKittyBed2 modelkittybed2, float f)
    {
        super(modelkittybed, f);
        kittybed = modelkittybed;
        setRenderPassModel(modelkittybed2);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) entityliving;
        mycolor = entitykittybed.getSheetColor();
        kittybed.hasMilk = entitykittybed.getHasMilk();
        kittybed.hasFood = entitykittybed.getHasFood();
        kittybed.pickedUp = entitykittybed.getPickedUp();
        kittybed.milklevel = entitykittybed.milklevel;
        //if(MoCClientProxy.mc.isMultiplayerWorld() && (entityliving.ridingEntity == MoCClientProxy.mc.thePlayer))
        /*if (entityliving.ridingEntity == MoCClientProxy.mc.thePlayer)
        {

            GL11.glTranslatef(0.0F, 1.1F, 0.0F);

        }*/
    }

    protected int setWoolColorAndRender(EntityLiving entityliving, int i, float f)
    {
        loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "kittybed.png");
        float f1 = 0.35F;
        int j = MoCTools.colorize(mycolor);
        GL11.glColor3f(f1 * fleeceColorTable[j][0], f1 * fleeceColorTable[j][1], f1 * fleeceColorTable[j][2]);
        return 1;//true;
    }

    @Override
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return setWoolColorAndRender(entityliving, i, f);
    }

}
