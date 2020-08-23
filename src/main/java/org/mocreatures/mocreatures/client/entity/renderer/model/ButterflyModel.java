package org.mocreatures.mocreatures.client.entity.renderer.model;

import com.google.common.collect.Lists;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import org.mocreatures.mocreatures.common.entity.ambient.ButterflyEntity;

import java.util.List;

public class ButterflyModel extends EntityModel<ButterflyEntity> {
	public final List<ModelPart> wings = Lists.newArrayList();
	ModelPart
			head, mouth,
			rightAntenna, leftAntenna,
			frontLegs, rearLegs, midLegs,
			abdomen, thorax,
			rightMidWing, leftMidWing,
			leftFrontWing, rightFrontWing,
			rightBackWing, leftBackWing;
	
	public ButterflyModel() {
		this.textureWidth = this.textureHeight = 32;
		
		this.head = new ModelPart(this, 0, 11);
		this.head.addCuboid(-0.5F, 0F, 0F, 1, 1, 1);
		this.head.setPivot(0F, 21.9F, -1.3F);
		ModelTools.setPartRotation(this.head, -2.171231F, 0F, 0F);
		this.cuboidList.add(head);
		
		this.mouth = new ModelPart(this, 0, 8);
		this.mouth.addCuboid(0F, 0F, 0F, 1, 2, 0);
		this.mouth.setPivot(-0.2F, 22F, -2.5F);
		ModelTools.setPartRotation(this.mouth, 0.6548599F, 0F, 0F);
		this.cuboidList.add(mouth);
		
		this.rightAntenna = new ModelPart(this, 0, 7);
		this.rightAntenna.addCuboid(-0.5F, 0F, -1F, 1, 0, 1);
		this.rightAntenna.setPivot(-0.5F, 21.7F, -2.3F);
		ModelTools.setPartRotation(this.rightAntenna, -1.041001F, 0.7853982F, 0F);
		this.cuboidList.add(mouth);
		
		this.leftAntenna = new ModelPart(this, 4, 7);
		this.leftAntenna.addCuboid(-0.5F, 0F, -1F, 1, 0, 1);
		this.leftAntenna.setPivot(0.5F, 21.7F, -2.3F);
		ModelTools.setPartRotation(this.leftAntenna, -1.041001F, -0.7853982F, 0F);
		this.cuboidList.add(mouth);
		
		this.thorax = new ModelPart(this, 0, 0);
		this.thorax.addCuboid(-0.5F, 1.5F, -1F, 1, 1, 2);
		this.thorax.setPivot(0F, 20F, -1F);
		this.cuboidList.add(thorax);
		
		this.abdomen = new ModelPart(this, 8, 1);
		this.abdomen.addCuboid(-0.5F, 0F, -1F, 1, 3, 1);
		this.abdomen.setPivot(0F, 21.5F, 0F);
		ModelTools.setPartRotation(this.abdomen, 1.427659F, 0F, 0F);
		this.cuboidList.add(abdomen);
		
		this.frontLegs = new ModelPart(this, 0, 8);
		this.frontLegs.addCuboid(-1F, 0F, 0F, 2, 3, 0);
		this.frontLegs.setPivot(0F, 21.5F, -1.8F);
		ModelTools.setPartRotation(this.frontLegs, 0.1487144F, 0F, 0F);
		this.cuboidList.add(frontLegs);
		
		this.midLegs = new ModelPart(this, 4, 8);
		this.midLegs.addCuboid(-1F, 0F, 0F, 2, 3, 0);
		this.midLegs.setPivot(0F, 22F, -1.2F);
		ModelTools.setPartRotation(this.midLegs, 0.5948578F, 0F, 0F);
		this.cuboidList.add(midLegs);
		
		this.rearLegs = new ModelPart(this, 0, 8);
		this.rearLegs.addCuboid(-1F, 0F, 0F, 2, 3, 0);
		this.rearLegs.setPivot(0F, 22.5F, -0.4F);
		ModelTools.setPartRotation(this.rearLegs, 1.070744F, 0F, 0F);
		this.cuboidList.add(rearLegs);
		
		this.leftFrontWing = new ModelPart(this, 4, 20);
		this.leftFrontWing.addCuboid(0F, 0F, -4F, 8, 0, 6);
		this.leftFrontWing.setPivot(0.3F, 21.4F, -1F);
		this.wings.add(leftFrontWing);
		
		this.leftMidWing = new ModelPart(this, 4, 26);
		this.leftMidWing.addCuboid(0F, 0F, -1F, 8, 0, 6);
		this.leftMidWing.setPivot(0.3F, 21.5F, -0.5F);
		this.wings.add(leftMidWing);
		
		this.leftBackWing = new ModelPart(this, 4, 0);
		this.leftBackWing.addCuboid(0F, 0F, -1F, 5, 0, 8);
		this.leftBackWing.setPivot(0.3F, 21.2F, -1F);
		ModelTools.setPartRotation(this.leftBackWing, 0F, 0F, 0.5934119F);
		this.wings.add(leftBackWing);
		
		this.rightFrontWing = new ModelPart(this, 4, 8);
		this.rightFrontWing.addCuboid(-8F, 0F, -4F, 8, 0, 6);
		this.rightFrontWing.setPivot(-0.3F, 21.4F, -1F);
		this.wings.add(leftFrontWing);
		
		this.rightMidWing = new ModelPart(this, 4, 14);
		this.rightMidWing.addCuboid(-8F, 0F, -1F, 8, 0, 6);
		this.rightMidWing.setPivot(-0.3F, 21.5F, -0.5F);
		this.wings.add(rightMidWing);
		
		this.rightBackWing = new ModelPart(this, 14, 0);
		this.rightBackWing.addCuboid(-5F, 0F, -1F, 5, 0, 8);
		this.rightBackWing.setPivot(0.3F, 21.2F, -1F);
		ModelTools.setPartRotation(this.rightBackWing, 0F, 0F, -0.5934119F);
		this.wings.add(rightBackWing);
	}
	
	@Override
	public void render(ButterflyEntity butterfly, float f, float g, float h, float i, float j, float k) {
		setAngles(f, g, h, butterfly.onGround);
		this.cuboidList.forEach((part) -> part.render(k));
	}
	
	@Override
	public void setAngles(ButterflyEntity entity, float f, float g, float h, float i, float j, float k) {
		this.setAngles(f, g, h, entity.onGround);
	}
	
	/**
	 * butterfly to have two / three moves: 1 slow movement when idle on ground
	 * has to be random from closing up to horizontal 2 fast wing flapping
	 * flying movement, short range close to 0 degree
	 * RLegXRot = cos((distanceWalked * 0.6662F) + Pi) * 0.8F * speed;
	 */
	private void setAngles(float distanceWalked, float speed, float timer, boolean onGround) {
		float f2a = timer % 100F;
		float wingRot = 0;
		float legMovA, legMovB;
		
		if (!onGround) { // flying
			wingRot = MathHelper.cos((timer * 0.9F)) * 0.9F;
			
			/*
			 * WingRot = MathHelper.cos((timer * 0.6662F)) * 0.5F; if (f2a > 40 &
			 * f2a < 60) { WingRot = MathHelper.cos((timer * 0.9F)) * 0.9F; }
			 */
			legMovA = (speed * 1.5F);
			legMovB = legMovA;
		} else {
			legMovA = MathHelper.cos((distanceWalked * 1.5F) + (float) Math.PI) * 2.0F * speed;
			legMovB = MathHelper.cos(distanceWalked * 1.5F) * 2.0F * speed;
			if (f2a > 40 & f2a < 60) { // random movement
				wingRot = MathHelper.cos((timer * 0.15F)) * 0.9F;
			}
			
		}
		
		float baseAngle = 0.52359F;
		
		this.leftMidWing.roll = -baseAngle + wingRot;
		this.rightMidWing.roll = baseAngle - wingRot;
		this.leftFrontWing.roll = -baseAngle + wingRot;
		
		this.leftBackWing.roll = 0.5934119F + -baseAngle + wingRot;
		this.rightFrontWing.roll = baseAngle - wingRot;
		this.rightBackWing.roll = -0.5934119F + baseAngle - wingRot;
		
		this.frontLegs.pitch = 0.1487144F + legMovA;
		this.midLegs.pitch = 0.5948578F + legMovB;
		this.rearLegs.pitch = 1.070744F + legMovA;
	}
}
