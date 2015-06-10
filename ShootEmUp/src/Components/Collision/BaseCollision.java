package Components.Collision;

import java.util.HashSet;

import Components.Component;
import Components.ComponentType;
import Math.Vector2;
import Object.Entity;

public abstract class BaseCollision extends Component implements CollisionComponent{

	protected ComponentType type = ComponentType.COLLISION;
	
	protected HashSet<Vector2> gridPos;
	
	public abstract void collision(Entity hitter, Entity hit);

	public void destroy(Entity e){
		
	}
	
	public ComponentType getType() {
		return type;
	}
	
	public HashSet<Vector2> getGridPos(){
		return gridPos;
	}
	
	public void setGridPos(HashSet<Vector2> gridPos){
		this.gridPos = gridPos;
	}
}
