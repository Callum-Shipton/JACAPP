package Object;

import Main.ShootEmUp;
import Math.Vector2;

public class Tile extends Collidable implements Comparable<Tile> {

	private Vector2 position;
	private Vector2 playerPos = new Vector2((float)Math.floor(ShootEmUp.currentLevel.getPlayer().getX() / 32), (float)Math.floor(ShootEmUp.currentLevel.getPlayer().getY()/32));
	private Vector2 distanceV;
	private float distanceF;
	private Tile parent;
	private Vector2 texture;
	
	public Tile(float X, float Y, Tile parent){
		super(X*32.0f, Y*32.0f, 31.0f, 31.0f, false);
		position = new Vector2(X,Y);
		distanceV = new Vector2();
		distanceV = new Vector2(position);
		distanceV.sub(playerPos);
		distanceF = distanceV.length();
		this.parent = parent;
	}
	
	public Tile(float X, float Y, float width, float height, boolean flat, Vector2 texture){
		super(X, Y, width, height, flat);
		position = new Vector2(X/32.0f,Y/32.0f);
		distanceV = new Vector2();
		distanceV = new Vector2(position);
		distanceV.sub(playerPos);
		distanceF = distanceV.length();
		this.texture = texture;
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
	
	public Vector2 getTexture(){
		return texture;
	}
	
	public Tile getParent(){
		return parent;
	}

	@Override
	public int compareTo(Tile t) {
		return Float.compare(distanceF,t.getDistance());
	}
}
