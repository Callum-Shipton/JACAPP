package Components.Control;

import Components.Component;
import Components.ComponentType;
import Components.Movement.BaseMovement;
import Object.Entity;

public abstract class BaseControl extends Component implements ControlComponent {
	
	protected ComponentType type = ComponentType.CONTROL;
	
	@Override
	public abstract void update(Entity e);

	public void destroy(Entity e){
		
	}
	
	public ComponentType getType() {
		return type;
	}
}
