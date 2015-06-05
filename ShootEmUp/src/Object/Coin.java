package Object;

import Display.Art;
import Main.ShootEmUp;

public class Coin extends Entity{	

	public Coin(float posX, float posY){
   		super(posX, posY);
		
		setWidth(32);
		setHeight(32);
		image = Art.coin;
		setAnimating(true);
		
	}
	
	public void remove(){
		ShootEmUp.currentLevel.coins.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
}
