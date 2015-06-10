package Components.Movement;

import Components.Component;
import Components.ComponentType;
import Components.Collision.BaseCollision;
import Components.Graphical.BaseGraphics;
import Math.Vector2;
import Math.Vector4;
import Object.Entity;

public abstract class BaseMovement extends Component implements MovementComponent {
	
	protected ComponentType type = ComponentType.MOVEMENT;
	
	protected int speed;
	protected BaseCollision BC;
	protected boolean flat;
	
	@Override
	public abstract void move(Entity e, Vector2 moveVec);
	
	@Override
	public abstract Vector4 doesCollide(Entity moving, Entity checked);

	@Override
	public abstract Vector4 collideFunction(BaseGraphics BG, float x, float y);
	
	public void destroy(Entity e){
		
	}
	//getter and setters
	
	public int getSpeed(){
		return speed;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public boolean getFlat(){
		return flat;
	}
	
	public void setFlat(boolean flat){
		this.flat = flat;
	}

	public ComponentType getType() {
		return type;
	}
}
