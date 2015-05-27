package Object;

import Display.Image;
import Main.ShootEmUp;

public class Armour extends Entity {
	
	public Armour(float posX, float posY, Image image){
		super(posX, posY);
		
		width = 16;
		height = 16;
		this.image = image;
		animating = true;
	}
	
	public void remove(){
		ShootEmUp.currentLevel.armour.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
}
