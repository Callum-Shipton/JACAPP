package Components.Collision;

import java.util.HashSet;

import Components.Component;
import Components.ComponentType;
import Components.Message;
import Main.ShootEmUp;
import Math.Vector2;
import Object.Entity;

public abstract class BaseCollision extends Component implements CollisionComponent{

	protected ComponentType type = ComponentType.COLLISION;
	
	protected HashSet<Vector2> gridPos;
	protected boolean moveBack;
	
	public abstract void collision(Entity hitter, Entity hit);

	public void destroy(Entity e){
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, e);
		ShootEmUp.currentLevel.oldEntities.add(e);
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
	
	public boolean getMoveBack(){
		return moveBack;
	}
	
	@Override
	public void receive(Message m, Entity e) {
		if(m == Message.ENTITY_DIED){
			destroy(e);
		}
	}
}
