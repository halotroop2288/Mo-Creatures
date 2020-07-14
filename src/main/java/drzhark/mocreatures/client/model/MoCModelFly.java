package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.ambient.MoCEntityFly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCModelFly extends ModelBase {

    ModelRenderer FrontLegs;
    ModelRenderer RearLegs;
    ModelRenderer MidLegs;
    ModelRenderer FoldedWings;
    ModelRenderer Head;
    ModelRenderer Tail;
    ModelRenderer Abdomen;
    ModelRenderer RightWing;
    ModelRenderer Thorax;
    ModelRenderer LeftWing;

    public MoCModelFly() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        this.Head.setRotationPoint(0F, 21.5F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setRotationPoint(0F, 20.5F, -1F);
        setRotation(this.Thorax, 0F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Abdomen.setRotationPoint(0F, 21.5F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.Tail = new ModelRenderer(this, 10, 2);
        this.Tail.addBox(-1F, 0F, -1F, 1, 1, 1);
        this.Tail.setRotationPoint(0.5F, 21.2F, 1.5F);
        setRotation(this.Tail, 1.427659F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 7);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.FrontLegs.setRotationPoint(0F, 22.5F, -1.8F);
        setRotation(this.FrontLegs, 0.1487144F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 0, 11);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.RearLegs.setRotationPoint(0F, 22.5F, -0.4F);
        setRotation(this.RearLegs, 1.070744F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 0, 9);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.MidLegs.setRotationPoint(0F, 22.5F, -1.2F);
        setRotation(this.MidLegs, 0.5948578F, 0F, 0F);

        /*
         * RightWing = new ModelRenderer(this, 10, 8); RightWing.addBox(-4F,
         * -2F, 0F, 3, 4, 0); RightWing.setRotationPoint(0F, 14.5F, -1F);
         * LeftWing = new ModelRenderer(this, 4, 8); LeftWing.addBox(1F, -2F,
         * 0F, 3, 4, 0); LeftWing.setRotationPoint(0F, 14.5F, -1F);
         */

        this.LeftWing = new ModelRenderer(this, 4, 4);
        this.LeftWing.addBox(-1F, 0F, 0.5F, 2, 0, 4);
        this.LeftWing.setRotationPoint(0F, 20.4F, -1F);
        setRotation(this.LeftWing, 0F, 1.047198F, 0F);

        this.RightWing = new ModelRenderer(this, 4, 4);
        this.RightWing.addBox(-1F, 0F, 0.5F, 2, 0, 4);
        this.RightWing.setRotationPoint(0F, 20.4F, -1F);
        setRotation(this.RightWing, 0F, -1.047198F, 0F);

        this.FoldedWings = new ModelRenderer(this, 4, 4);
        this.FoldedWings.addBox(-1F, 0F, 0F, 2, 0, 4);
        this.FoldedWings.setRotationPoint(0F, 20.5F, -2F);
        setRotation(this.FoldedWings, 0.0872665F, 0F, 0F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityFly fly = (MoCEntityFly) entity;
        boolean isFlying = (fly.getIsFlying() || fly.motionY < -0.1D);
        setRotationAngles(f, f1, f2, f3, f4, f5, !isFlying);
        this.FrontLegs.render(f5);
        this.RearLegs.render(f5);
        this.MidLegs.render(f5);
        this.Head.render(f5);
        this.Tail.render(f5);
        this.Abdomen.render(f5);
        this.Thorax.render(f5);

        if (!isFlying) {
            this.FoldedWings.render(f5);
        } else {
            GL11.glPushMatrix();
            GL11.glEnable(3042 /* GL_BLEND */);
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            this.LeftWing.render(f5);
            this.RightWing.render(f5);
            GL11.glDisable(3042/* GL_BLEND */);
            GL11.glPopMatrix();
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    /*
     * public void setRotationAngles(float f, float f1, float f2, float f3,
     * float f4, float f5) { super.setRotationAngles(f, f1, f2, f3, f4, f5);
     * float WingRot = MathHelper.cos((f2 * 3.0F)) * 0.7F;
     * RightWing.rotateAngleZ = WingRot; LeftWing.rotateAngleZ = -WingRot; float
     * legMov = (f1*1.5F); FrontLegs.rotateAngleX = 0.1487144F + legMov;
     * MidLegs.rotateAngleX = 0.5948578F + legMov; RearLegs.rotateAngleX =
     * 1.070744F + legMov; }
     */

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean onGround) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        float WingRot = MathHelper.cos((f2 * 3.0F)) * 0.7F;
        this.RightWing.rotateAngleZ = WingRot;
        this.LeftWing.rotateAngleZ = -WingRot;
        float legMov = 0F;
        float legMovB = 0F;

        if (!onGround) {
            legMov = (f1 * 1.5F);
            legMovB = legMov;
        } else {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }

        this.FrontLegs.rotateAngleX = 0.1487144F + legMov;
        this.MidLegs.rotateAngleX = 0.5948578F + legMovB;
        this.RearLegs.rotateAngleX = 1.070744F + legMov;
    }

}
