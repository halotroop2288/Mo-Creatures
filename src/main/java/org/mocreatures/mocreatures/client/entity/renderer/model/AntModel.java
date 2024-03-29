package org.mocreatures.mocreatures.client.entity.renderer.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import org.mocreatures.mocreatures.common.entity.ambient.AntEntity;

public class AntModel extends EntityModel<AntEntity> {
	ModelPart head;
	ModelPart mouth;
	ModelPart rightAntenna;
	ModelPart leftAntenna;
	ModelPart thorax;
	ModelPart abdomen;
	ModelPart midLegs;
	ModelPart frontLegs;
	ModelPart rearLegs;
	
	public AntModel() {
		this.textureWidth = 32;
		this.textureHeight = 32;
		
		this.head = new ModelPart(this, 0, 11);
		this.head.addCuboid(-0.5F, 0F, 0F, 1, 1, 1);
		this.head.setPivot(0F, 21.9F, -1.3F);
		ModelTools.setPartRotation(this.head, -2.171231F, 0F, 0F);
		this.cuboidList.add(head);
		
		this.mouth = new ModelPart(this, 8, 10);
		this.mouth.addCuboid(0F, 0F, 0F, 2, 1, 0);
		this.mouth.setPivot(-1F, 22.3F, -1.9F);
		ModelTools.setPartRotation(this.mouth, -0.8286699F, 0F, 0F);
		this.cuboidList.add(mouth);
		
		this.rightAntenna = new ModelPart(this, 0, 6);
		this.rightAntenna.addCuboid(-0.5F, 0F, -1F, 1, 0, 1);
		this.rightAntenna.setPivot(-0.5F, 21.7F, -2.3F);
		ModelTools.setPartRotation(this.rightAntenna, -1.041001F, 0.7853982F, 0F);
		this.cuboidList.add(rightAntenna);
		
		this.leftAntenna = new ModelPart(this, 4, 6);
		this.leftAntenna.addCuboid(-0.5F, 0F, -1F, 1, 0, 1);
		this.leftAntenna.setPivot(0.5F, 21.7F, -2.3F);
		ModelTools.setPartRotation(this.leftAntenna, -1.041001F, -0.7853982F, 0F);
		this.cuboidList.add(leftAntenna);
		
		this.thorax = new ModelPart(this, 0, 0);
		this.thorax.addCuboid(-0.5F, 1.5F, -1F, 1, 1, 2);
		this.thorax.setPivot(0F, 20F, -0.5F);
		this.cuboidList.add(thorax);
		
		this.abdomen = new ModelPart(this, 8, 1);
		this.abdomen.addCuboid(-0.5F, -0.2F, -1F, 1, 2, 1);
		this.abdomen.setPivot(0F, 21.5F, 0.3F);
		ModelTools.setPartRotation(this.abdomen, 1.706911F, 0F, 0F);
		this.cuboidList.add(abdomen);
		
		this.midLegs = new ModelPart(this, 4, 8);
		this.midLegs.addCuboid(-1F, 0F, 0F, 2, 3, 0);
		this.midLegs.setPivot(0F, 22F, -0.7F);
		ModelTools.setPartRotation(this.midLegs, 0.5948578F, 0F, 0F);
		this.cuboidList.add(midLegs);
		
		this.frontLegs = new ModelPart(this, 0, 8);
		this.frontLegs.addCuboid(-1F, 0F, 0F, 2, 3, 0);
		this.frontLegs.setPivot(0F, 22F, -0.8F);
		ModelTools.setPartRotation(this.frontLegs, -0.6192304F, 0F, 0F);
		this.cuboidList.add(frontLegs);
		
		this.rearLegs = new ModelPart(this, 0, 8);
		this.rearLegs.addCuboid(-1F, 0F, 0F, 2, 3, 0);
		this.rearLegs.setPivot(0F, 22F, 0F);
		ModelTools.setPartRotation(this.rearLegs, 0.9136644F, 0F, 0F);
		this.cuboidList.add(rearLegs);
	}
	
	@Override
	public void render(AntEntity entity, float f, float g, float h, float i, float j, float k) {
		super.render(entity, f, g, h, i, j, k);
		setAngles(entity, f, g, h, i, j, k);
		this.cuboidList.forEach(part -> part.render(k));
	}
	
	@Override
	public void setAngles(AntEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		float legMov = MathHelper.cos((f) + (float) Math.PI) * f1;
		float legMovB = MathHelper.cos(f) * f1;
		this.frontLegs.roll = -0.6192304F + legMov;
		this.midLegs.roll = 0.5948578F + legMovB;
		this.rearLegs.roll = 0.9136644F + legMov;
	}
}
