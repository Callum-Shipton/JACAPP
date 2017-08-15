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
import input.Keys;
import math.VectorMath;

public class PlayerControl extends BaseControl {

	public PlayerControl() {

	}

	public PlayerControl(PlayerControl playerControl) {

	}

	@Override
	public void receive(Message m) {

	}

	private void checkMovement(Entity e) {
		BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);
		PlayerGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);

		Vector2f movement = new Vector2f(0.0f, 0.0f);
		if ((Keyboard.getKey(Keys.getKey("moveUp")) == 1) || (Keyboard.getKey(Keys.getKey("moveUp")) == 2)) {
			movement.add(0.0f, -1.0f);
		}
		if ((Keyboard.getKey(Keys.getKey("moveLeft")) == 1) || (Keyboard.getKey(Keys.getKey("moveLeft")) == 2)) {
			movement.add(-1.0f, 0.0f);
		}
		if ((Keyboard.getKey(Keys.getKey("moveDown")) == 1) || (Keyboard.getKey(Keys.getKey("moveDown")) == 2)) {
			movement.add(0.0f, 1.0f);
		}
		if ((Keyboard.getKey(Keys.getKey("moveRight")) == 1) || (Keyboard.getKey(Keys.getKey("moveRight")) == 2)) {
			movement.add(1.0f, 0.0f);
		}

		if (movement.length() > 0) {
			if (movement.length() > 1) {
				movement.normalize();
			}
			graphicsComponent.setAnimating(true);
			movementComponent.move(e, movement);

		} else {
			graphicsComponent.setAnimating(false);
		}
	}

	private void checkDirection() {
		PlayerGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);
		Vector2f dir = new Vector2f(0.0f, 0.0f);
		if ((Keyboard.getKey(Keys.getKey("lookUp")) == 1) || (Keyboard.getKey(Keys.getKey("lookUp")) == 2)) {
			dir.add(0.0f, -1.0f);
		}
		if ((Keyboard.getKey(Keys.getKey("lookLeft")) == 1) || (Keyboard.getKey(Keys.getKey("lookLeft")) == 2)) {
			dir.add(-1.0f, 0.0f);
		}
		if ((Keyboard.getKey(Keys.getKey("lookDown")) == 1) || (Keyboard.getKey(Keys.getKey("lookDown")) == 2)) {
			dir.add(0.0f, 1.0f);
		}
		if ((Keyboard.getKey(Keys.getKey("lookRight")) == 1) || (Keyboard.getKey(Keys.getKey("lookRight")) == 2)) {
			dir.add(1.0f, 0.0f);
		}
		if (dir.length() > 0) {
			if (dir.length() > 1) {
				dir.normalize();
			}
			graphicsComponent.setDirection((int) (Math.round(VectorMath.angle(dir)) / 45));
		}
	}

	private void checkAttack(Entity e) {
		PlayerGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);
		BaseAttack attackComponent = getEntity().getComponent(TypeComponent.ATTACK);
		if ((Keyboard.getKey(Keys.getKey("shoot")) == 1) || (Keyboard.getKey(Keys.getKey("shoot")) == 2)) {
			attackComponent.attack(e, graphicsComponent.getDirection());
		}
	}

	private void checkPotions() {
		BaseInventory baseInventory = getEntity().getComponent(TypeComponent.INVENTORY);
		if ((Keyboard.getKey(Keys.getKey("potion1")) == 1) || (Keyboard.getKey(Keys.getKey("potion1")) == 2)) {
			baseInventory.usePotion(TypePotion.HEALTH);
			Keyboard.setKey(Keys.getKey("potion1"));
		}

		if ((Keyboard.getKey(Keys.getKey("potion2")) == 1) || (Keyboard.getKey(Keys.getKey("potion2")) == 2)) {
			baseInventory.usePotion(TypePotion.MANA);
			Keyboard.setKey(Keys.getKey("potion2"));
		}

		if ((Keyboard.getKey(Keys.getKey("potion3")) == 1) || (Keyboard.getKey(Keys.getKey("potion3")) == 2)) {
			baseInventory.usePotion(TypePotion.SPEED);
			Keyboard.setKey(Keys.getKey("potion3"));
		}

		if ((Keyboard.getKey(Keys.getKey("potion4")) == 1) || (Keyboard.getKey(Keys.getKey("potion4")) == 2)) {
			baseInventory.usePotion(TypePotion.KNOCKBACK);
			Keyboard.setKey(Keys.getKey("potion4"));
		}
	}

	@Override
	public void update() {
		checkMovement(getEntity());

		checkDirection();

		checkAttack(getEntity());

		checkPotions();
	}
}
