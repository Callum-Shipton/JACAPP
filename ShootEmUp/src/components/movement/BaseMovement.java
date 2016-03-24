package components.movement;

import object.Entity;
import main.ShootEmUp;
import math.Vector2;
import math.Vector4;
import components.Component;
import components.TypeComponent;
import components.Message;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;

public abstract class BaseMovement extends Component implements MovementComponent {
	
	protected TypeComponent type = TypeComponent.MOVEMENT;
	
	protected int speed;
	protected int realSpeed;
	protected BaseCollision BC;
	protected boolean flat;
	
	private boolean frost = false;
	private int frostCounter = 0;
	private int frostTime = 100;

	@Override
	public void move(Entity e, Vector2 moveVec){
		if(frost == true){
			speed = speed / 2;
			frostCounter++;
			if(frostCounter > frostTime){
				frost = false;
				speed = realSpeed;
				frostCounter = 0;
			}
		}
	}
	
	@Override
	public abstract Vector4 doesCollide(Entity moving, Entity checked);

	@Override
	public abstract Vector4 collideFunction(BaseGraphics BG, float x, float y);
	
	public void destroy(Entity e){
		
	}
	
	public boolean getFlat(){
		return flat;
	}
	
	public void setFlat(boolean flat){
		this.flat = flat;
	}

	public TypeComponent getType() {
		return type;
	}
	
	@Override
	public void receive(Message m, Entity e) {
		if(m == Message.ENTITY_MOVED){
			ShootEmUp.currentLevel.eMap.removeEntity(BC.getGridPos(), e);
			BC.setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
			ShootEmUp.currentLevel.eMap.addEntity(BC.getGridPos(), e);
		}
		
	}
	
	public void increaseSpeed(int increase){
		speed += increase;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setFrost(boolean frost) {
		this.frost = frost;
	}
}
