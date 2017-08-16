package components.control;

import org.joml.Vector2f;

import components.Message;
import components.TypeComponent;
import components.movement.BaseMovement;

public class BallControl extends BaseControl {
	private Vector2f direction = new Vector2f(1.0f,0);
	
	public BallControl() {

	}

	public BallControl(BallControl ballControl) {
		this.direction = ballControl.direction;
	}
	
	@Override
	public void update() {
		BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);
		movementComponent.move(getEntity(), direction);
		
	}

	@Override
	public void receive(Message m) {
		// TODO Auto-generated method stub
		
	}
	
	public void bounce(){
		direction.perpendicular();
	}
}
