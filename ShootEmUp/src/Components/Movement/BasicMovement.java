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
	protected BaseGraphics BG;
	
	public BasicMovement(Entity e, BaseCollision BC, BaseGraphics BG, int speed){
		this.BG = BG;
		this.BC = BC;
		this.speed = speed;
	}
	
	public void move(Entity e, Vector2 moveVec) {
		if(Math.abs(moveVec.x()) > 0){
			BG.setX((BG.getX() + Math.round(moveVec.x() * speed)));
			checkCollisionX(e, moveVec);
		}
		if(Math.abs(moveVec.y()) > 0){
			BG.setY(BG.getY() + Math.round(moveVec.y() * speed));
			checkCollisionY(e, moveVec);
		}
	}
	
	private void checkCollisionY(Entity e, Vector2 moveVec) {
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap.getEntites(BC.getGridPos());
		boolean collision = false;
		Vector4 collVec = null;
		Entity hit = null;
		for (Entity character : entities) {
			if(character != e){
				collVec = doesCollide(e, character);
				if (collVec != null) {
				collision = true;
				hit = character;
				break;
				}
			}
		}
		

		
		if (collision == true) {
			if(((BaseCollision)e.getComponent(ComponentType.COLLISION)).getMoveBack() == true){
				moveBackY(e, moveVec, collVec);
			} 
			if ((e.getComponent(ComponentType.COLLISION) != null)) {
				BC.collision(e, hit);
			}
		}
		
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(e);
		ShootEmUp.currentLevel.eMap.removeEntity(BC.getGridPos(), e);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, e);
		BC.setGridPos(newGrid);
		
		
	}

	public void checkCollisionX(Entity e, Vector2 moveVec){
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap.getEntites(BC.getGridPos());
		boolean collision = false;
		Vector4 collVec = null;
		Entity hit = null;
		for (Entity character : entities) {
			if(character != e){
				collVec = doesCollide(e, character);
				if (collVec != null) {
				collision = true;
				hit = character;
				break;
				}
			}
		}
		

		
		if (collision == true) {
			if(((BaseCollision)e.getComponent(ComponentType.COLLISION)).getMoveBack() == true){
				moveBackX(e, moveVec, collVec);
			} 
			if ((e.getComponent(ComponentType.COLLISION) != null)) {
				BC.collision(e, hit);
			}
		}
		
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(e);
		ShootEmUp.currentLevel.eMap.removeEntity(BC.getGridPos(), e);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, e);
		BC.setGridPos(newGrid);
		
	}
	
	public void moveBackX(Entity e, Vector2 moveVec, Vector4 collVec){
				if (Math.abs(collVec.x()) <= speed+1) {
					BG.setX(BG.getX() - collVec.x() - (moveVec.x() / Math.abs(moveVec.x())));
				}
				else if (Math.abs(collVec.z()) <= speed+1) {
					BG.setX(BG.getX() - collVec.z() - (moveVec.x() / Math.abs(moveVec.x())));
				}
	}
	
	public void moveBackY(Entity e, Vector2 moveVec, Vector4 collVec){
		if (Math.abs(collVec.y()) <= speed+1) {
			BG.setY(BG.getY() - collVec.y() - (moveVec.y() / Math.abs(moveVec.y())));
		}
		else if (Math.abs(collVec.w()) <= speed+1) {
			BG.setY(BG.getY() - collVec.w()- (moveVec.y() / Math.abs(moveVec.y())));
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
