package components.control;

import org.joml.Vector2f;

import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.PlayerGraphics;
import components.inventory.BaseInventory;
import components.inventory.TypePotion;
import components.movement.BaseMovement;
import entity.Entity;
import input.Keyboard;
import loop.Loop;
import math.VectorMath;

public class PlayerControl extends BaseControl {

	private BaseMovement BM;
	private PlayerGraphics PG;
	private BaseAttack BA;
	private BaseInventory PI;

	public PlayerControl() {
	

		this.PG = getEntity().getComponent(TypeComponent.GRAPHICS);
		this.BA = getEntity().getComponent(TypeComponent.ATTACK);
		this.BM = getEntity().getComponent(TypeComponent.MOVEMENT);
		this.PI = getEntity().getComponent(TypeComponent.INVENTORY);
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	private void checkMovement(Entity e) {
		Vector2f movement = new Vector2f(0.0f, 0.0f);
		if ((Keyboard.getKey(Loop.getKeys().moveUp) == 1) || (Keyboard.getKey(Loop.getKeys().moveUp) == 2)) {
			movement.add(0.0f, -1.0f);
		}
		if ((Keyboard.getKey(Loop.getKeys().moveLeft) == 1) || (Keyboard.getKey(Loop.getKeys().moveLeft) == 2)) {
			movement.add(-1.0f, 0.0f);
		}
		if ((Keyboard.getKey(Loop.getKeys().moveDown) == 1) || (Keyboard.getKey(Loop.getKeys().moveDown) == 2)) {
			movement.add(0.0f, 1.0f);
		}
		if ((Keyboard.getKey(Loop.getKeys().moveRight) == 1) || (Keyboard.getKey(Loop.getKeys().moveRight) == 2)) {
			movement.add(1.0f, 0.0f);
		}

		if (movement.length() > 0) {
			if (movement.length() > 1) {
				movement.normalize();
			}
			this.PG.setAnimating(true);
			this.BM.move(e, movement);

		} else {
			this.PG.setAnimating(false);
		}
	}

	private void checkDirection() {
		Vector2f dir = new Vector2f(0.0f, 0.0f);
		if ((Keyboard.getKey(Loop.getKeys().lookUp) == 1) || (Keyboard.getKey(Loop.getKeys().lookUp) == 2)) {
			dir.add(0.0f, -1.0f);
		}
		if ((Keyboard.getKey(Loop.getKeys().lookLeft) == 1) || (Keyboard.getKey(Loop.getKeys().lookLeft) == 2)) {
			dir.add(-1.0f, 0.0f);
		}
		if ((Keyboard.getKey(Loop.getKeys().lookDown) == 1) || (Keyboard.getKey(Loop.getKeys().lookDown) == 2)) {
			dir.add(0.0f, 1.0f);
		}
		if ((Keyboard.getKey(Loop.getKeys().lookRight) == 1) || (Keyboard.getKey(Loop.getKeys().lookRight) == 2)) {
			dir.add(1.0f, 0.0f);
		}
		if (dir.length() > 0) {
			if (dir.length() > 1) {
				dir.normalize();
			}
			this.PG.setDirection((int) (Math.round(VectorMath.angle(dir)) / 45));
		}
	}

	private void checkAttack(Entity e) {
		if ((Keyboard.getKey(Loop.getKeys().shoot) == 1) || (Keyboard.getKey(Loop.getKeys().shoot) == 2)) {
			this.BA.attack(e, this.PG.getDirection());
		}
	}

	private void checkPotions() {
		if ((Keyboard.getKey(Loop.getKeys().potion1) == 1) || (Keyboard.getKey(Loop.getKeys().potion1) == 2)) {
			this.PI.usePotion(TypePotion.HEALTH);
			Keyboard.setKey(Loop.getKeys().potion1);
		}

		if ((Keyboard.getKey(Loop.getKeys().potion2) == 1) || (Keyboard.getKey(Loop.getKeys().potion2) == 2)) {
			this.PI.usePotion(TypePotion.MANA);
			Keyboard.setKey(Loop.getKeys().potion2);
		}

		if ((Keyboard.getKey(Loop.getKeys().potion3) == 1) || (Keyboard.getKey(Loop.getKeys().potion3) == 2)) {
			this.PI.usePotion(TypePotion.SPEED);
			Keyboard.setKey(Loop.getKeys().potion3);
		}

		if ((Keyboard.getKey(Loop.getKeys().potion4) == 1) || (Keyboard.getKey(Loop.getKeys().potion4) == 2)) {
			this.PI.usePotion(TypePotion.KNOCKBACK);
			Keyboard.setKey(Loop.getKeys().potion4);
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
