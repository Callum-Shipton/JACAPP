package Object;

import Display.Image;

public class Coin extends Entity{	

	public Coin(float posX, float posY, float width, float height, Image image){
		super(posX, posY, width, height, 0, 0, image);
		animating = true;
	}
}
