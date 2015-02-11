
public class Particle extends Entity implements Collidable{
	private int damage;
	private int range;
	private int distance;
	
	public Particle(int posX, int posY, int speed, int direction, String image, int damage, int range, int distance){
		super(posX, posY, speed, direction, image);
		this.damage = damage;
		this.range = range;
		this.distance = distance;
	}
	
	public void generate(int x, int y, int direction){
		
	}
	
	@Override
	public void onCollision(Collidable c) {
		// TODO Auto-generated method stub
		
		// Despawn and do damage;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}
