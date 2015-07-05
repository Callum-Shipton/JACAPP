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
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(e);
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap.getEntites(newGrid);
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
			if(((BaseCollision)hit.getComponent(ComponentType.COLLISION)).getMoveBack() == true){
				moveBackY(e, moveVec, collVec);
				newGrid = ShootEmUp.currentLevel.eMap.getGridPos(e);
			} 
			if ((e.getComponent(ComponentType.COLLISION) != null)) {
				BC.collision(e, hit);
			}
			BaseCollision EC = (BaseCollision) hit.getComponent(ComponentType.COLLISION);
			if (EC != null) {
				EC.collision(hit, e);
			}
		}
		
		ShootEmUp.currentLevel.eMap.removeEntity(BC.getGridPos(), e);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, e);
		BC.setGridPos(newGrid);
		
		
	}

	public void checkCollisionX(Entity e, Vector2 moveVec){
		HashSet<Vector2> newGrid = ShootEmUp.currentLevel.eMap.getGridPos(e);
		HashSet<Entity> entities = ShootEmUp.currentLevel.eMap.getEntites(newGrid);
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
			if(((BaseCollision)hit.getComponent(ComponentType.COLLISION)).getMoveBack() == true){
				moveBackX(e, moveVec, collVec);
				newGrid = ShootEmUp.currentLevel.eMap.getGridPos(e);
			} 
			if ((e.getComponent(ComponentType.COLLISION) != null)) {
				BC.collision(e, hit);
			}
			BaseCollision EC = (BaseCollision) hit.getComponent(ComponentType.COLLISION);
			if (EC != null) {
				EC.collision(hit, e);
			}
		}
		
		ShootEmUp.currentLevel.eMap.removeEntity(BC.getGridPos(), e);
		ShootEmUp.currentLevel.eMap.addEntity(newGrid, e);
		BC.setGridPos(newGrid);
		
	}
	
	public void moveBackX(Entity e, Vector2 moveVec, Vector4 collVec){
				if (Math.abs(collVec.x()) <= speed) {
					BG.setX(BG.getX() - collVec.x());
				}
				else if (Math.abs(collVec.z()) <= speed) {
					BG.setX(BG.getX() - collVec.z());
				}
	}
	
	public void moveBackY(Entity e, Vector2 moveVec, Vector4 collVec){
		if (Math.abs(collVec.y()) <= speed) {
			BG.setY(BG.getY() - collVec.y());
		}
		else if (Math.abs(collVec.w()) <= speed) {
			BG.setY(BG.getY() - collVec.w());
		}
	}
	
	public Vector4 doesCollide(Entity moving, Entity checked) {
		BaseGraphics CG = (BaseGraphics) checked.getComponent(ComponentType.GRAPHICS);
		float x = BG.getX();
		float y = BG.getY();
		float w = BG.getWidth();
		float h = BG.getHeight();

		float cx = CG.getX();
		float cy = CG.getY();
		float cw = CG.getWidth();
		float ch = CG.getHeight();
		
		if((x < (cx + cw) && (x + w) > cx && y < (cy + ch) && (y + h) > cy)){
			return new Vector4(x - (cx+cw), y - (cy+ch), (x+w) - cx , (y+h) - cy);
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
		System.out.println(e.toString() + " : " + BG.getX() + ", " +BG.getY());
	}
}
