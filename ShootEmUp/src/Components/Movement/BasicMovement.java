package Components.Movement;

import java.util.HashSet;

import Components.Message;
import Components.Collision.BaseCollision;
import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;
import Object.Entity;

public class BasicMovement extends BaseMovement{
	protected int speed;
	protected HashSet<Vector2> gridPos;
	protected BaseCollision BC;
	
	public BasicMovement(Entity e){
		gridPos = ShootEmUp.currentLevel.eMap.getGridPos(e);
		ShootEmUp.currentLevel.eMap.addEntity(gridPos, e);
	}
	
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
	
	public Vector4 doesCollide(Entity moving, Entity checked) {
		if((Math.abs(checked.getPosX() - moving.getPosX()) > 74) && (Math.abs(moving.getPosY() - checked.getPosY()) > 74)){
			return null;
		}
		
		float x = moving.getPosX();
		float y = moving.getPosY();
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}
		
		x += checked.getWidth()/4;

		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x += checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x += checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x += checked.getWidth()/4;

		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y += checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y += checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y += checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y += checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x -= checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x -= checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x -= checked.getWidth()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		x -= checked.getWidth()/4;

		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y -= checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y -= checked.getHeight()/4;
		
		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}

		y -= checked.getHeight()/4;

		if (collideFunction(checked, x, y) != null) {
			return collideFunction(checked, x, y);
		}
		return null;
	}

	public Vector4 collideFunction(Entity e, float x, float y) {
		if (((x >= e.getPosX()) && (x <= (e.getPosX() + e.getWidth()))) && ((y >= e.getPosY()) && (y <= (e.getPosY() + e.getHeight())))) {
			return new Vector4(x - e.getPosX(), y - e.getPosY(), x -(e.getPosX() + e.getWidth()), y - (e.getPosY() + e.getHeight()));	
		}
		return null;
	}

	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
}
