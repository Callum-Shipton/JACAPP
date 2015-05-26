package Object;

import Display.Art;
import Main.ShootEmUp;

public class Exp extends Entity{	

	public Exp(float posX, float posY){
		super(posX, posY);
		
		width = 16;
		height = 16;
		image = Art.exp;
		animating = true;
	}
	
	public void remove(){
		ShootEmUp.currentLevel.experience.remove(this);
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, this);
	}
}
