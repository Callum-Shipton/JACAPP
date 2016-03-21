package components.collision;

import java.util.HashSet;

import object.Entity;
import main.ShootEmUp;
import math.Vector2;
import components.Component;
import components.TypeComponent;
import components.Message;

public abstract class BaseCollision extends Component implements CollisionComponent{

	protected TypeComponent type = TypeComponent.COLLISION;
	
	protected HashSet<Vector2> gridPos;
	protected boolean moveBack;
	
	public abstract void collision(Entity e, Entity hit);

	public void destroy(Entity e){
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, e);
		ShootEmUp.currentLevel.oldEntities.add(e);
	}
	
	public TypeComponent getType() {
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

	}
}
