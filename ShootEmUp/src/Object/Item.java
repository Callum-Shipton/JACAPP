package Object;

import Display.Image;
import Main.ShootEmUp;

public class Item extends Entity {
	
	public Item(float posX, float posY, Image image){
		super(posX, posY);
		
		width = 16;
		height = 16;
		this.image = image;
		animating = true;
	}
	
	public void remove(){
		ShootEmUp.currentLevel.items.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
}
