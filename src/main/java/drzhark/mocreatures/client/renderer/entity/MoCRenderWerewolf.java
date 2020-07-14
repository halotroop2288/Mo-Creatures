package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderWerewolf extends RenderLiving<MoCEntityWerewolf> {

    private final MoCModelWere tempWerewolf;

    public MoCRenderWerewolf(MoCModelWereHuman modelwerehuman, ModelBase modelbase, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelbase, f);
        this.addLayer(new LayerMoCWereHuman(this));
        this.tempWerewolf = (MoCModelWere) modelbase;
    }

    @Override
    public void doRender(MoCEntityWerewolf entitywerewolf, double d, double d1, double d2, float f, float f1) {
        this.tempWerewolf.hunched = entitywerewolf.getIsHunched();
        super.doRender(entitywerewolf, d, d1, d2, f, f1);

    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityWerewolf entitywerewolf) {
        return entitywerewolf.getTexture();
    }

    private class LayerMoCWereHuman implements LayerRenderer<MoCEntityWerewolf> {

        private final MoCRenderWerewolf mocRenderer;
        private final MoCModelWereHuman mocModel = new MoCModelWereHuman();

        public LayerMoCWereHuman(MoCRenderWerewolf render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityWerewolf entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            int myType = entity.getType();

            if (!entity.getIsHumanForm()) {
                bindTexture(MoCreatures.proxy.getTexture("wereblank.png"));
            } else {
                switch (myType) {

                    case 1:
                        bindTexture(MoCreatures.proxy.getTexture("weredude.png"));
                        break;
                    case 2:
                        bindTexture(MoCreatures.proxy.getTexture("werehuman.png"));
                        break;
                    case 3:
                        bindTexture(MoCreatures.proxy.getTexture("wereoldie.png"));
                        break;
                    case 4:
                        bindTexture(MoCreatures.proxy.getTexture("werewoman.png"));
                        break;
                    default:
                        bindTexture(MoCreatures.proxy.getTexture("wereoldie.png"));
                }

            }

            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entity, f, f1, f2);
            this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
