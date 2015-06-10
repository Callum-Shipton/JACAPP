package Components.Movement;

import java.util.HashSet;

import Components.ComponentType;
import Components.Message;
import Components.Collision.BaseCollision;
import Components.Graphical.BaseGraphics;
import Main.ShootEmUp;
import Math.Vector2;
import Math.Vector4;
import Object.Entity;

public class BasicMovement extends BaseMovement{
	protected int speed;
	protected BaseCollision BC;
	protected BaseGraphics BG;
	
	public BasicMovement(Entity e, BaseCollision BC, BaseGraphics BG, int speed){
		this.BC = BC;
		this.BG = BG;
		BC.setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(BC.getGridPos(), e);
		this.speed = speed;
	}
	
	public void move(Entity e, Vector2 moveVec) {
		BG.setX((BG.getX() + Math.round(moveVec.x() * speed)));
		BG.setY(BG.getY() + Math.round(moveVec.y() * speed));
		checkCollision(e);
	}
	
	public void checkCollision(Entity e){
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap.getEntites(BC.getGridPos());
		boolean collision = false;
		Entity hit = null;
		for (Entity character : entities) {
				if (((doesCollide(e, character) != null) && (character != e))) {
				collision = true;
				hit = character;
				break;
			}
		}
		
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(e);
		ShootEmUp.currentLevel.eMap.removeEntity(BC.getGridPos(), e);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, e);
		BC. setGridPos(newGrid);
		
		if (collision == true) {
			BC.collision(e, hit);
		}
	}
	
	public Vector4 doesCollide(Entity moving, Entity checked) {
		BaseGraphics CG = (BaseGraphics) checked.getComponent(ComponentType.GRAPHICS);
		
		float x = BG.getX();
		float y = BG.getY();
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}
		
		x += BG.getWidth()/4;

		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		x += BG.getWidth()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		x += BG.getWidth()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		x += BG.getWidth()/4;

		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		y += BG.getHeight()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		y += BG.getHeight()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		y += BG.getHeight()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		y += BG.getHeight()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		x -= BG.getWidth()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		x -= BG.getWidth()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		x -= BG.getWidth()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		x -= BG.getWidth()/4;

		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		y -= BG.getHeight()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		y -= BG.getHeight()/4;
		
		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}

		y -= BG.getHeight()/4;

		if (collideFunction(CG, x, y) != null) {
			return collideFunction(CG, x, y);
		}
		return null;
	}

	public Vector4 collideFunction(BaseGraphics BG, float x, float y) {
		if (((x >= BG.getX()) && (x <= (BG.getX() + BG.getWidth()))) && ((y >= BG.getY()) && (y <= (BG.getY() + BG.getHeight())))) {
			return new Vector4(x - BG.getX(), y - BG.getY(), x -(BG.getX() + BG.getWidth()), y - (BG.getY() + BG.getHeight()));	
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

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
