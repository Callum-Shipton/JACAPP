package Object;

import Display.Image;
import Main.ShootEmUp;

public class Item extends Entity {
	
	public Item(float posX, float posY, Image image){
		super(posX, posY);
		
		setWidth(16);
		setHeight(16);
		this.image = image;
		setAnimating(true);
	}
	
	public void remove(){
		ShootEmUp.currentLevel.items.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
}
