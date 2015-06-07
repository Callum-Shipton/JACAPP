package Components.Collision;

import Components.Component;
import Math.Vector4;
import Object.Entity;

public abstract class BaseCollision extends Component implements CollisionComponent {

	protected boolean flat;
	
	@Override
	public abstract Vector4 doesCollide(Entity e, float x, float y);

	@Override
	public abstract Vector4 collideFunction(Entity e, float x, float y);

	@Override
	public abstract void onCollide(Entity hit);

	public boolean getFlat(){
		return flat;
	}
}
