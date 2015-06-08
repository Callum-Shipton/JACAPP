package Components.Control;

import java.util.HashSet;

import Components.Component;
import Components.ComponentType;
import Components.Collision.BaseCollision;
import Math.Vector2;
import Object.Entity;

public abstract class BaseControl extends Component implements ControlComponent {
	
	protected ComponentType type = ComponentType.CONTROL;
	
	@Override
	public abstract void update(Entity e);

	public ComponentType getType() {
		return type;
	}
}
