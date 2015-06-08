package Components.Control;

import java.util.HashSet;

import Components.Component;
import Components.ComponentType;
import Components.Collision.BaseCollision;
import Math.Vector2;
import Object.Entity;

public abstract class BaseControl extends Component implements ControlComponent {
	
	private static final ComponentType type = ComponentType.CONTROL;
	
	@Override
	public abstract void update(Entity e);
}
