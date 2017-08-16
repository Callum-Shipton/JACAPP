package components.control;

import org.joml.Vector2f;

import components.Message;
import components.MessageId;
import components.TypeComponent;
import components.graphical.AnimatedGraphics;
import components.movement.BaseMovement;
import loop.GameLoop;
import main.Pong;

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
		AnimatedGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);
		if(graphicsComponent.getY()>0 && graphicsComponent.getY()+graphicsComponent.getHeight()<GameLoop.getDisplay().getHeight()) {
		movementComponent.move(getEntity(), direction);
		}
		else {
			direction.mul(1, -1);
			movementComponent.move(getEntity(), direction);
		}
		if(graphicsComponent.getX()<0) {
			Pong.enemyPoint();
			getEntity().send(new Message(MessageId.ENTITY_DIED));
		}else if(graphicsComponent.getX()+graphicsComponent.getWidth()>GameLoop.getDisplay().getWidth()) {
			Pong.playerPoint();
			getEntity().send(new Message(MessageId.ENTITY_DIED));
		}
		
	}

	@Override
	public void receive(Message m) {
		// TODO Auto-generated method stub
		
	}
	
	public void bounce(){
		direction.mul(-1, 0.3f).add(0, (float) ((Math.random()-0.5)*0.8));
	}
}
