package Components.Collision;

import Components.Component;
import Components.ComponentType;
import Object.Entity;

public abstract class BaseCollision extends Component implements CollisionComponent{

	private static final ComponentType type = ComponentType.COLLISION;
	
	public abstract void collision(Entity hitter, Entity hit);
}
