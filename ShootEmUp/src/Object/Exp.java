package Object;

import Display.Image;

public class Exp extends Collidable{
	
	protected Image image;
	
	public Exp(float posX, float posY, float width, float height, boolean flat, Image image){
		super(posX, posY, width, height, flat);
		this.image = image;
	}
}
