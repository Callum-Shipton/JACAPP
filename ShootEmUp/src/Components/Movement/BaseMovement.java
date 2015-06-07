package Components.Movement;

import Math.Vector4;
import Object.Entity;

public abstract class BaseMovement implements MovementComponent {
	
	protected boolean flat;
	
	@Override
	public abstract Vector4 doesCollide(Entity e, float x, float y);

	@Override
	public abstract Vector4 collideFunction(Entity e, float x, float y);
	
	public boolean getFlat(){
		return flat;
	}
	
	public void setFlat(boolean flat){
		this.flat = flat;
	}
}
