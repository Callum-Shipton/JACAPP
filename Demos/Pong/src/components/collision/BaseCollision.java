package components.collision;

import java.util.Set;

import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.MessageId;
import components.TypeComponent;
import components.interfaces.CollisionComponent;

public abstract class BaseCollision extends Component implements CollisionComponent {

	protected transient boolean moveBack;

	public BaseCollision() {
	}

	@Override
	public void destroy() {
	}

	public boolean getMoveBack() {
		return moveBack;
	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.COLLISION;
	}

	@Override
	public void receive(Message m) {
	}

}
