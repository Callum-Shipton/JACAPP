package components.control;

import org.joml.Vector2f;

import components.Message;
import components.attack.BaseAttack;
import components.graphical.PlayerGraphics;
import components.inventory.BaseInventory;
import components.movement.BaseMovement;
import entity.Entity;
import gui.buttons.UpgradeManaRegenButton;
import math.VectorMath;

public class TestingControl extends BaseControl {

	private BaseMovement BM;
	private PlayerGraphics PG;
	private BaseAttack BA;
	private Vector2f movement = new Vector2f(0.0f, 0.0f);
	private Vector2f dir = new Vector2f(0.0f, 0.0f);
	private boolean toggle = false;

	public TestingControl(PlayerGraphics PG, BaseAttack BA, BaseMovement BM, BaseInventory PI) {
		this.PG = PG;
		this.BA = BA;
		this.BM = BM;
		movement.add(0.0f, -1.0f);
		movement.add(-1.0f, 0.0f);
		movement.normalize();
		this.PG.setAnimating(true);
		// dir.add(0.0f, -1.0f);

	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Entity e) {

		this.BM.move(e, movement);

		new UpgradeManaRegenButton().click();
		// TODO Auto-generated method stub
		if (toggle)
			dir.set(1.0f, 0.0f);
		else
			dir.set(0.0f, 1.0f);
		dir.normalize();
		this.PG.setDirection((int) (Math.round(VectorMath.angle(dir)) / 45));
		if (this.BA.attack(e, this.PG.getDirection()))
			toggle = !toggle;
	}

}
