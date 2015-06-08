package Components.Collision;

import Components.Component;
import Components.ComponentType;
import Components.Message;
import Math.Vector4;
import Object.Entity;

public abstract class BaseCollision extends Component implements CollisionComponent{

	protected ComponentType type = ComponentType.COLLISION;
	
	public abstract void collision(Entity hitter, Entity hit);

	public ComponentType getType() {
		return type;
	}
}
