
public class Particle extends Entity{
	private int damage;
	
	public Particle(int xPos, int yPos, int speed, String image, int damage){
		super(xPos, yPos, speed, image);
		this.damage = damage;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
}
