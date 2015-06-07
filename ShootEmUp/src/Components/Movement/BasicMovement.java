package Components.Movement;

import java.util.HashSet;

import Components.Collision.BaseCollision;
import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;
import Object.Entity;

public class BasicMovement extends NoMovement{
	protected int speed;
	protected HashSet<Vector2> gridPos;
	protected BaseCollision BC;
	
	public void move(Entity e, Vector2 moveVec) {
		e.setPosX(e.getPosX() + Math.round(moveVec.x()));
		e.setPosY(e.getPosY() + Math.round(moveVec.y()));
		checkCollision(e);
	}
	
	public void checkCollision(Entity e){
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap.getEntites(gridPos);
		boolean collision = false;
		Entity hit = null;
		for (Entity character : entities) {
				if (((doesCollide(e, character) != null) && (character != e))) {
				collision = true;
				hit = character;
				break;
			}
		}

		if (collision == true) {
			BC.collision(e, hit);
		}
		
		//code for not destroying the entity?
		/*
		if(!destroy){
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, this);
		gridPos = newGrid;
		}
		*/
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
}
