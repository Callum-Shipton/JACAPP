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

		if (collision == true) {
			//code for moving back away from enemy;
			/*
			 * if (Math.abs(vec.x()) < speed) {
				e.setPosX(e.getPosX() + ((Math.round(moveVec.x())) - vec.x()
						- (moveVec.x() / Math.abs(moveVec.x()))));
			} else if(Math.abs(vec.x()) >= speed); 
			else if (Math.abs(vec.z()) < speed) {
				e.setPosX(e.getPosX() + ((Math.round(moveVec.x())) - vec.z()
						- (moveVec.x() / Math.abs(moveVec.x()))));
			}
			onCollide(hit); 
			*/
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
