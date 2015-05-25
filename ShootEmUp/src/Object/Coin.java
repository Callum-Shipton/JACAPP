package Object;

import Display.Art;
import Display.Image;
import Main.ShootEmUp;

public class Coin extends Entity{	

	public Coin(float posX, float posY){
   		super(posX, posY);
		
		width = 32;
		height = 32;
		image = Art.coin;
		animating = true;
		
	}
	
	public void remove(){
		ShootEmUp.currentLevel.coins.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
}
