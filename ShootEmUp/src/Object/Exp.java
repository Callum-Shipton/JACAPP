package Object;

import Display.Art;
import Main.ShootEmUp;

public class Exp extends Entity{	

	public Exp(float posX, float posY){
		super(posX, posY);
		
		setWidth(16);
		setHeight(16);
		image = Art.exp;
		setAnimating(true);
	}
	
	public void remove(){
		ShootEmUp.currentLevel.experience.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
}
