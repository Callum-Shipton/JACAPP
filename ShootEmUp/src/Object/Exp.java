package Object;

import Display.Art;
import Display.Image;

public class Exp extends Entity{	

	public Exp(float posX, float posY){
		super(posX, posY);
		
		width = 16;
		height = 16;
		image = Art.exp;
		animating = true;
	}
}
