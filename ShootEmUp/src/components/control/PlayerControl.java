package components.control;

import components.Message;
import components.attack.BaseAttack;
import components.graphical.PlayerGraphics;
import components.inventory.BaseInventory;
import components.inventory.TypePotion;
import components.movement.BaseMovement;
import input.Keyboard;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class PlayerControl extends BaseControl {

	private BaseMovement BM;
	private PlayerGraphics PG;
	private BaseAttack BA;
	private BaseInventory PI;

	public PlayerControl(PlayerGraphics PG, BaseAttack BA, BaseMovement BM, BaseInventory PI) {
		PG.scrollScreen();
		this.PG = PG;
		this.BA = BA;
		this.BM = BM;
		this.PI = PI;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	private void checkMovement(Entity e) {
		Vector2 movement = new Vector2(0.0f, 0.0f);
		if ((Keyboard.getKey(ShootEmUp.getKeys().moveUp) == 1) || (Keyboard.getKey(ShootEmUp.getKeys().moveUp) == 2)) {
			movement.add(0.0f, -1.0f);
		}
		if ((Keyboard.getKey(ShootEmUp.getKeys().moveLeft) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().moveLeft) == 2)) {
			movement.add(-1.0f, 0.0f);
		}
		if ((Keyboard.getKey(ShootEmUp.getKeys().moveDown) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().moveDown) == 2)) {
			movement.add(0.0f, 1.0f);
		}
		if ((Keyboard.getKey(ShootEmUp.getKeys().moveRight) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().moveRight) == 2)) {
			movement.add(1.0f, 0.0f);
		}

		if (movement.length() > 0) {
			if (movement.length() > 1) {
				movement.normalize();
			}
			this.PG.setAnimating(true);
			this.BM.move(e, movement);
			this.PG.scrollScreen();

		} else {
			this.PG.setAnimating(false);
		}
	}

	private void checkDirection() {
		Vector2 dir = new Vector2(0.0f, 0.0f);
		if ((Keyboard.getKey(ShootEmUp.getKeys().lookUp) == 1) || (Keyboard.getKey(ShootEmUp.getKeys().lookUp) == 2)) {
			dir.add(0.0f, -1.0f);
		}
		if ((Keyboard.getKey(ShootEmUp.getKeys().lookLeft) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().lookLeft) == 2)) {
			dir.add(-1.0f, 0.0f);
		}
		if ((Keyboard.getKey(ShootEmUp.getKeys().lookDown) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().lookDown) == 2)) {
			dir.add(0.0f, 1.0f);
		}
		if ((Keyboard.getKey(ShootEmUp.getKeys().lookRight) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().lookRight) == 2)) {
			dir.add(1.0f, 0.0f);
		}
		if (dir.length() > 0) {
			if (dir.length() > 1) {
				dir.normalize();
			}
			this.PG.setDirection((int) (Math.round(dir.Angle()) / 45));
		}
	}

	private void checkAttack(Entity e) {
		if ((Keyboard.getKey(ShootEmUp.getKeys().shoot) == 1) || (Keyboard.getKey(ShootEmUp.getKeys().shoot) == 2)) {
			this.BA.attack(e, this.PG.getDirection());
		}
	}

	private void checkPotions() {
		if ((Keyboard.getKey(ShootEmUp.getKeys().potion1) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().potion1) == 2)) {
			this.PI.usePotion(TypePotion.HEALTH);
			Keyboard.setKey(ShootEmUp.getKeys().potion1);
		}

		if ((Keyboard.getKey(ShootEmUp.getKeys().potion2) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().potion2) == 2)) {
			this.PI.usePotion(TypePotion.MANA);
			Keyboard.setKey(ShootEmUp.getKeys().potion2);
		}

		if ((Keyboard.getKey(ShootEmUp.getKeys().potion3) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().potion3) == 2)) {
			this.PI.usePotion(TypePotion.SPEED);
			Keyboard.setKey(ShootEmUp.getKeys().potion3);
		}

		if ((Keyboard.getKey(ShootEmUp.getKeys().potion4) == 1)
				|| (Keyboard.getKey(ShootEmUp.getKeys().potion4) == 2)) {
			this.PI.usePotion(TypePotion.KNOCKBACK);
			Keyboard.setKey(ShootEmUp.getKeys().potion4);
		}
	}

	@Override
	public void update(Entity e) {
		checkMovement(e);

		checkDirection();

		checkAttack(e);

		checkPotions();
	}
}
