package entities;

import components.attack.PlayerAttack;
import components.attack.TypeAttack;
import components.collision.RigidCollision;
import components.control.PlayerControl;
import components.graphical.PlayerGraphics;
import components.inventory.PlayerInventory;
import components.movement.BasicMovement;
import components.spawn.PointSpawn;
import display.Art;
import math.Vector2;

public class Player extends Entity{

	public Player(TypeAttack type){
		PlayerGraphics g = new PlayerGraphics(this, Art.player, Art.base);
		PointSpawn s = new PointSpawn(g, new Vector2(480.0f, 480.0f), this);
		PlayerAttack a;
		a = new PlayerAttack(type);
		addComponent(g);
		RigidCollision c = new RigidCollision(this);
		addComponent(c);
		BasicMovement m = new BasicMovement(c, g, 5);
		PlayerInventory i = new PlayerInventory(a, m, 0, 1);
		addComponent(s);
		addComponent(a);
		addComponent(m);
		addComponent(i);
		addComponent(new PlayerControl(this, g, a, m, i));
	}
}
