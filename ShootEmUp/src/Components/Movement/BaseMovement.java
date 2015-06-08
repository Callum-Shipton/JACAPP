package Components.Movement;

import Components.Component;
import Components.ComponentType;
import Math.Vector2;
import Math.Vector4;
import Object.Entity;

public abstract class BaseMovement extends Component implements MovementComponent {
	
	private static final ComponentType type = ComponentType.MOVEMENT;
	
	protected boolean flat;
	
	@Override
	public abstract void move(Entity e, Vector2 moveVec);
	
	@Override
	public abstract Vector4 doesCollide(Entity moving, Entity checked);

	@Override
	public abstract Vector4 collideFunction(Entity e, float x, float y);
	
	public boolean getFlat(){
		return flat;
	}
	
	public void setFlat(boolean flat){
		this.flat = flat;
	}
}
