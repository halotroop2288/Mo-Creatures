package org.mocreatures.mocreatures.client.entity.renderer.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import org.mocreatures.mocreatures.common.entity.passive.pet.GenericBirdEntity;

public class GenericBirdModel extends EntityModel<GenericBirdEntity> {
	protected final ModelPart head, body, leftLeg, rightLeg, rightWing, leftWing, beak, tail;
	
	public GenericBirdModel() {
		final byte byte0 = 16;
		final float hOff = 0.75F; // Makes the birds correctly stand on the ground
		
		this.head = new ModelPart(this, 0, 0);
		this.head.addCuboid(-1.5F, -3F + hOff, -2F, 3, 3, 3, 0.0F);
		this.head.setPivot(0.0F, -1 + byte0, -4F);
		this.cuboidList.add(head);
		
		this.beak = new ModelPart(this, 14, 0);
		this.beak.addCuboid(-0.5F, -1.5F + hOff, -3F, 1, 1, 2, 0.0F);
		this.beak.setPivot(0.0F, -1 + byte0, -4F);
		this.cuboidList.add(beak);
		
		this.body = new ModelPart(this, 0, 9);
		this.body.addCuboid(-2F, -4F, -3F + hOff, 4, 8, 4, 0.0F);
		this.body.setPivot(0.0F, byte0, 0.0F);
		this.body.pitch = 1.047198F;
		this.cuboidList.add(body);
		
		this.leftLeg = new ModelPart(this, 26, 0);
		this.leftLeg.addCuboid(-1F, 0.0F + hOff, -4F, 3, 4, 3);
		this.leftLeg.setPivot(-2F, 3 + byte0, 1.0F);
		this.cuboidList.add(leftLeg);
		
		this.rightLeg = new ModelPart(this, 26, 0);
		this.rightLeg.addCuboid(-1F, 0.0F + hOff, -4F, 3, 4, 3);
		this.rightLeg.setPivot(1.0F, 3 + byte0, 1.0F);
		this.cuboidList.add(rightLeg);
		
		float wingOff = 0.0F;
		float wingPivOff = 0.5F;
		
		this.rightWing = new ModelPart(this, 24, 13);
		this.rightWing.addCuboid(-1F + wingOff, 0.0F + hOff, -3F, 1, 5, 5);
		this.rightWing.setPivot(-2F + wingPivOff, -2 + byte0, 0.0F);
		this.cuboidList.add(rightWing);
		
		this.leftWing = new ModelPart(this, 24, 13);
		this.leftWing.addCuboid(0.0F - wingOff, 0.0F + hOff, -3F, 1, 5, 5);
		this.leftWing.setPivot(2.0F - wingPivOff, -2 + byte0, 0.0F);
		this.cuboidList.add(leftWing);
		
		this.tail = new ModelPart(this, 0, 23);
		this.tail.addCuboid(-6F, 5F + hOff, 2.0F, 4, 1, 4, 0.0F);
		this.tail.setPivot(4F, -3 + byte0, 0.0F);
		this.tail.pitch = 0.261799F;
		this.cuboidList.add(tail);
	}
	
	@Override
	public void render(GenericBirdEntity entity, float f, float g, float h, float i, float j, float k) {
		this.cuboidList.forEach((part) -> part.render(k));
	}
	
	@Override
	public void setAngles(GenericBirdEntity entity, float f, float g, float h, float i, float j, float k) {
		this.head.pitch = -(j / 2.0F / 57.29578F);
		this.head.yaw = i / 57.29578F;
		this.beak.yaw = this.head.yaw;
		
		if (!entity.onGround) {
			this.leftLeg.pitch = 1.4F;
			this.rightLeg.pitch = 1.4F;
		} else {
			this.leftLeg.pitch = MathHelper.cos(f * 0.6662F) * g;
			this.rightLeg.pitch = MathHelper.cos((f * 0.6662F) + (float) Math.PI) * g;
		}
		this.rightWing.roll = h;
		this.leftWing.roll = -h;
	}
}
