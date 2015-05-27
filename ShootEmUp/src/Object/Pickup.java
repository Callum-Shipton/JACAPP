package Object;

import Display.Art;
import Main.ShootEmUp;

public class Pickup extends Entity {
	
	public Pickup(float posX, float posY){
		super(posX, posY);
		
		width = 8;
		height = 8;
		image = Art.shoes;
		animating = true;
	}
	
	public void remove(){
		ShootEmUp.currentLevel.pickups.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
}
