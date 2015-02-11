package Object;


public class Weapon {
	
	private Particle particle;
	
	public Weapon(Particle particle){
		this.particle = particle;
	}
	
	public Particle getParticle(){
		return particle;
	}
	
	public void setParticle(Particle particle){
		this.particle = particle;
	}
}
