package components.control;

import object.Entity;
import components.Component;
import components.TypeComponent;

public abstract class BaseControl extends Component implements ControlComponent {
	
	protected TypeComponent type = TypeComponent.CONTROL;
	
	@Override
	public abstract void update(Entity e);
	
	public void destroy(Entity e){
		
	}
	
	public TypeComponent getType() {
		return type;
	}
}
