package Object;

public abstract class Collidable {
	protected float posX;
	protected float posY;
	protected float width;
	protected float height;
	
	public Collidable(float posX, float posY, float width, float height){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
	public boolean doesCollide(float x, float y, float w, float h){
		if(collideFunction(x, y)){
			return true;
		}
		
		x += w;
		
		if(collideFunction(x, y)){
			return true;
		}
		
		y += h;
		
		if(collideFunction(x, y)){
			return true;
		}
		
		x -= w;
		
		if(collideFunction(x, y)){
			return true;
		}
		return false;
	}
	
	public boolean collideFunction(float x, float y){
		if(((x >= posX) && (x <= (posX + width))) && ((y >= posY) && (y <= (posY + height)))){
			return true;
		}
		return false;
	}
	
	public void onCollide(){
		return;
	}
}
