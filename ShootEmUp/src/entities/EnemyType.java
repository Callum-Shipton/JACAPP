package entities;

import components.attack.BaseAttack;
import components.collision.BaseCollision;
import components.control.BaseControl;
import components.graphical.BaseGraphics;
import components.inventory.BaseInventory;
import components.movement.BaseMovement;

public class EnemyType {

	private BaseGraphics enemyGraphics;
	private BaseAttack enemyAttack;
	private BaseControl enemyControl;
	private BaseCollision enemyCollision;
	private BaseMovement enemyMovement;
	private BaseInventory enemyInventory;
	
	public EnemyType(BaseGraphics enemyGraphics, BaseAttack enemyAttack, BaseControl enemyControl, BaseCollision enemyCollision, BaseMovement enemyMovement, BaseInventory enemyInventory){
		this.enemyGraphics = enemyGraphics;
		this.enemyAttack = enemyAttack;
		this.enemyControl = enemyControl;
		this.enemyCollision = enemyCollision;
		this.enemyMovement = enemyMovement;
		this.enemyInventory = enemyInventory;
	}

	public BaseGraphics getEnemyGraphics() {
		return enemyGraphics;
	}

	public BaseAttack getEnemyAttack() {
		return enemyAttack;
	}

	public BaseControl getEnemyControl() {
		return enemyControl;
	}

	public BaseCollision getEnemyCollision() {
		return enemyCollision;
	}

	public BaseMovement getEnemyMovement() {
		return enemyMovement;
	}

	public BaseInventory getEnemyInventory() {
		return enemyInventory;
	}	
	
}
