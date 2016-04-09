package components.control;

import components.Component;
import components.TypeComponent;
import object.Entity;

public abstract class BaseControl extends Component implements ControlComponent {

	protected TypeComponent type = TypeComponent.CONTROL;

	@Override
	public void destroy(Entity e) {

	}

	@Override
	public TypeComponent getType() {
		return this.type;
	}

	@Override
	public abstract void update(Entity e);
}
