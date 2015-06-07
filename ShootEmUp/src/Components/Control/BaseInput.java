package Components.Control;

import java.util.HashSet;

import Components.Component;
import Components.Collision.BaseCollision;
import Math.Vector2;
import Object.Entity;

public abstract class BaseInput extends Component implements InputComponent {
	protected int speed;
	protected HashSet<Vector2> gridPos;
	protected BaseCollision BC;
	
	@Override
	public abstract void update(Entity e);

	@Override
	public abstract void move(Entity e, Vector2 moveVec);
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public int getSpeed(){
		return speed;
	}
}
