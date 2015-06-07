package Components.Movement;

import java.util.HashSet;

import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;
import Object.Character;
import Object.Entity;

public class BasicMovement extends NoMovement{
	protected int speed;
	protected HashSet<Vector2> gridPos;
	
	public void move(Entity e, Vector2 moveVec) {
		e.setPosX(e.getPosX() + Math.round(moveVec.x()));
		e.setPosY(e.getPosY() + Math.round(moveVec.y()));
		checkCollision(e);
	}
	
	public void checkCollision(Entity e){
		Vector4 vec = new Vector4(0.0f, 0.0f, 0.0f, 0.0f);
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap.getEntites(gridPos);
		boolean collision = false;
		Entity hit = null;
		for (Entity character : entities) {
			if (character instanceof Character) {
				if (((doesCollide(e, character) != null) && (character != e))) {
					collision = true;
					vec = doesCollide(e, character);
					hit = character;
					break;
				}
			}
		}

		if (collision == true) {
			
			//code for sending the two entitys to the collision component;
		
		}
		
		
		//code for destroying the entity?
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
