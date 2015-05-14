package Object;

import Display.Art;
import Display.Image;

public class Coin extends Entity{	

	public Coin(float posX, float posY){
		super(posX, posY);
		
		width = 32;
		height = 32;
		image = Art.coin;
		animating = true;
		
	}
}
