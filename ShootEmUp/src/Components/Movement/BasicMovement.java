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
		
	}
	
	public boolean checkCollision(Entity e){
		Vector4 vec = new Vector4(0.0f, 0.0f, 0.0f, 0.0f);
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap.getEntites(gridPos);
		boolean collision = false;
		Character hit = null;
		Entity wall;
		for (Entity character : entities) {
			if (character instanceof Character) {
				if (((doesCollide(e, character) != null) && (character != e))) {
					collision = true;
					vec = doesCollide(e, character);
					hit = (Character) character;
					break;
				}
			}
		}

		if (collide == false) {
			e.setPosX(e.getPosX() + Math.round(moveVec.x()));
		} else {
			if (Math.abs(vec.x()) < speed) {
				e.setPosX(e.getPosX() + ((Math.round(moveVec.x())) - vec.x()
						- (moveVec.x() / Math.abs(moveVec.x()))));
			} else if(Math.abs(vec.x()) >= speed); 
			else if (Math.abs(vec.z()) < speed) {
				e.setPosX(e.getPosX() + ((Math.round(moveVec.x())) - vec.z()
						- (moveVec.x() / Math.abs(moveVec.x()))));
			}
			onCollide(hit);
		}
		collide = false;
		if(Math.abs(moveVec.y()) > 0){
		for (Entity character : entities) {
			if (character instanceof Character) {
				if ((character.doesCollide(e.getPosX(), e.getPosY() + (Math.round(moveVec.y())),
						e.getWidth(), e.getHeight()) != null) && (character != e)) {
					collide = true;
					hit = (Character) character;
					vec = character.doesCollide(e.getPosX(), e.getPosY()
							+ (Math.round(moveVec.y())), e.getWidth(), e.getHeight());
					break;
				}
			}
		}

		if (collide == false) {
			e.setPosY(e.getPosY() + Math.round(moveVec.y()));
		} else {
			onCollide(hit);
			if (Math.abs(vec.y()) < speed) {
				e.setPosY(e.getPosY() + ((Math.round(moveVec.y())) - vec.y()
						- (moveVec.y() / Math.abs(moveVec.y()))));
			} else if(Math.abs(vec.y()) >= speed); 
			else if (Math.abs(vec.w()) < speed) {
				e.setPosY(e.getPosY() + ((Math.round(moveVec.y())) - vec.w()
						- (moveVec.y() / Math.abs(moveVec.y()))));
			}
		}
		}
		if(!destroy){
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, this);
		gridPos = newGrid;
		}
		return collision;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
}
