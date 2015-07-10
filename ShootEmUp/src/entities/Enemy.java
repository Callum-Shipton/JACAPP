package entities;

import components.ComponentType;
import components.attack.BaseAttack;
import components.attack.EnemyAttack;
import components.collision.BaseCollision;
import components.collision.RigidCollision;
import components.control.AIControl;
import components.control.BaseControl;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.inventory.BaseInventory;
import components.inventory.EnemyInventory;
import components.movement.BaseMovement;
import components.movement.BasicMovement;
import display.Art;
import main.ShootEmUp;

public class Enemy extends Entity{
	
	public Enemy(EnemyType type, float x, float y){	
		
		addComponent(type.getEnemyGraphics());
		addComponent(type.getEnemyAttack());
		addComponent(type.getEnemyCollision());
		addComponent(type.getEnemyControl());
		addComponent(type.getEnemyMovement());
		addComponent(type.getEnemyInventory());
		
		((RigidCollision)getComponent(ComponentType.COLLISION)).setUp(this);
		
		ShootEmUp.currentLevel.entities.add(this);
	}
}
