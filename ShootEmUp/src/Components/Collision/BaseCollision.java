package Components.Collision;

import Components.Component;
import Object.Entity;

public abstract class BaseCollision extends Component implements CollisionComponent {

	public abstract void Collision(Entity hitter, Entity hit);
	
}
