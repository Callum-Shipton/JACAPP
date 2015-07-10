package components.control;

import static org.lwjgl.glfw.GLFW.*;

import math.Vector2;
import input.Keyboard;
import components.Message;
import components.attack.BaseAttack;
import components.graphical.PlayerGraphics;
import components.inventory.PlayerInventory;
import components.inventory.TypePotion;
import components.movement.BaseMovement;
import entities.Entity;

public class PlayerControl extends BaseControl{
	
	BaseMovement BM;
	PlayerGraphics PG;
	BaseAttack BA;
	PlayerInventory PI;
	
	public PlayerControl(Entity e, PlayerGraphics PG, BaseAttack BA, BaseMovement BM, PlayerInventory PI){
		PG.scrollScreen(e);
		this.PG = PG;
		this.BA = BA;
		this.BM = BM;
		this.PI = PI;
	}

	@Override
	public void update(Entity e) {
		Vector2 movement = new Vector2(0.0f, 0.0f);
		if (Keyboard.getKey(GLFW_KEY_W) == 1
				|| Keyboard.getKey(GLFW_KEY_W) == 2) {
			movement.add(0.0f, -1.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_A) == 1
				|| Keyboard.getKey(GLFW_KEY_A) == 2) {
			movement.add(-1.0f, 0.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_S) == 1
				|| Keyboard.getKey(GLFW_KEY_S) == 2) {
			movement.add(0.0f, 1.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_D) == 1
				|| Keyboard.getKey(GLFW_KEY_D) == 2) {
			movement.add(1.0f, 0.0f);
		}

		if (movement.length() > 0) {
			if (movement.length() > 1)
				movement.normalize();
			PG.setAnimating(true);
			BM.move(e, movement);
			PG.scrollScreen(e);

		} else
			PG.setAnimating(false);
		Vector2 dir = new Vector2(0.0f, 0.0f);
		if (Keyboard.getKey(GLFW_KEY_UP) == 1
				|| Keyboard.getKey(GLFW_KEY_UP) == 2) {
			dir.add(0.0f, -1.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_LEFT) == 1
				|| Keyboard.getKey(GLFW_KEY_LEFT) == 2) {
			dir.add(-1.0f, 0.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_DOWN) == 1
				|| Keyboard.getKey(GLFW_KEY_DOWN) == 2) {
			dir.add(0.0f, 1.0f);
		}
		if (Keyboard.getKey(GLFW_KEY_RIGHT) == 1
				|| Keyboard.getKey(GLFW_KEY_RIGHT) == 2) {
			dir.add(1.0f, 0.0f);
		}
		if (dir.length() > 0) {
			if (dir.length() > 1)
				dir.normalize();
			PG.setDirection((int) (Math.round(dir.Angle()) / 45));
		}

		if (Keyboard.getKey(GLFW_KEY_SPACE) == 1
				|| Keyboard.getKey(GLFW_KEY_SPACE) == 2) {
			BA.attack(e, PG.getDirection());
		}
		
		if (Keyboard.getKey(GLFW_KEY_1) == 1
				|| Keyboard.getKey(GLFW_KEY_1) == 2) {
			PI.usePotion(TypePotion.HEALTH);
		}
		
		if (Keyboard.getKey(GLFW_KEY_2) == 1
				|| Keyboard.getKey(GLFW_KEY_2) == 2) {
			PI.usePotion(TypePotion.MANA);
		}
		
		if (Keyboard.getKey(GLFW_KEY_3) == 1
				|| Keyboard.getKey(GLFW_KEY_3) == 2) {
			PI.usePotion(TypePotion.SPEED);
		}
		
		if (Keyboard.getKey(GLFW_KEY_4) == 1
				|| Keyboard.getKey(GLFW_KEY_4) == 2) {
			PI.usePotion(TypePotion.KNOCKBACK);
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
}
