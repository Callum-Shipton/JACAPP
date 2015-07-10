package entities;

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

	private BaseGraphics enemyGraphics;
	private BaseAttack enemyAttack;
	private BaseControl enemyControl;
	private BaseCollision enemyCollision;
	private BaseMovement enemyMovement;
	private BaseInventory enemyInventory;
	
	public Enemy(EnemyType type, float x, float y){	
		
		enemyGraphics = new AnimatedGraphics(type.getImage(), Art.base, false, x, y); 
		enemyAttack = new EnemyAttack(type.getAttack(), type.getHealth());
		this.addComponent(enemyGraphics);
		enemyCollision = new RigidCollision(this);
		enemyMovement = new BasicMovement(this, enemyCollision, enemyGraphics, type.getSpeed());
		enemyControl = new AIControl(enemyGraphics,enemyAttack, enemyMovement);
		enemyInventory = new EnemyInventory(enemyGraphics, 1);
		
		addComponent(enemyAttack);
		addComponent(enemyCollision);
		addComponent(enemyControl);
		addComponent(enemyMovement);
		addComponent(enemyInventory);
		
		ShootEmUp.currentLevel.entities.add(this);
	}
}
