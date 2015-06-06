package Components.Input;

import java.util.HashSet;

import Components.Physics.BaseCollision;
import Math.Vector2;
import Object.Entity;

public abstract class BaseInput implements InputComponent {
	protected int speed;
	protected HashSet<Vector2> gridPos;
	protected BaseCollision BC;
	
	@Override
	public abstract void update(Entity e);

	@Override
	public abstract void move(Entity e, Vector2 moveVec);
}
