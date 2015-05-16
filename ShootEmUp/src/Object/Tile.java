package Object;

import Main.ShootEmUp;
import Math.Vector2;

public class Tile implements Comparable<Tile> {

	private Vector2 position;
	private Vector2 playerPos = new Vector2(ShootEmUp.currentLevel.getPlayer().getX(), ShootEmUp.currentLevel.getPlayer().getY());
	private Vector2 distanceV;
	private float distanceF;
	
	public Tile(float X, float Y){
		position = new Vector2(X,Y);
		distanceV = new Vector2();
		distanceV = position.sub(playerPos);
		distanceF = distanceV.length();
	}
	
	public float getDistance(){
		return distanceF;
	}
	
	public float getX(){
		return position.x();
	}
	
	public float getY(){
		return position.y();
	}

	@Override
	public int compareTo(Tile t) {
		if(t.distanceF < distanceF){
			return 1;
		} else if(t.distanceF == distanceF){
			return 0;
		}
			return -1;
	}
}
