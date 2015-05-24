package Object;

import Main.ShootEmUp;
import Math.Vector2;

public class Tile implements Comparable<Tile> {

	private Vector2 position;
	private Vector2 playerPos = new Vector2((float)Math.floor(ShootEmUp.currentLevel.getPlayer().getX() / 32), (float)Math.floor(ShootEmUp.currentLevel.getPlayer().getY()/32));
	private Vector2 distanceV;
	private float distanceF;
	private Tile parent;
	
	public Tile(float X, float Y, Tile parent){
		position = new Vector2(X,Y);
		distanceV = new Vector2();
		distanceV = new Vector2(position);
		distanceV.sub(playerPos);
		distanceF = distanceV.length();
		this.parent = parent;
	}
	
	public Tile(Tile tile){
		position = new Vector2(tile.getX(), tile.getY());
		distanceV = new Vector2();
		distanceV = new Vector2(position);
		distanceV.sub(playerPos);
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
	
	public Vector2 getPositionVector(){
		return new Vector2(position.x() * 32, position.y() * 32);
	}
	
	public Tile getParent(){
		return parent;
	}

	@Override
	public int compareTo(Tile t) {
		return Float.compare(distanceF,t.getDistance());
	}
}
