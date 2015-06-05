package Object;

public class PlayerRegenComponent implements RegenComponent {

	private int manaRegen = 50;

	private int healthRegen = 100;
	
	PlayerAttackComponent PAC;
	
	public PlayerRegenComponent(PlayerAttackComponent PAC){
		this.PAC = PAC;
	}
	
	@Override
	public void update(Entity e) {
		
		if (manaRegen <= 0) {
			manaRegen = 50;
			if (PAC.getMana() < PAC.getMaxMana()) {
				PAC.setMana(PAC.getMana() + 1);
			}
		}
		manaRegen--;

		if (healthRegen <= 0) {
			healthRegen = 100;
			if (PAC.getHealth() < PAC.getMaxHealth()) {
				PAC.setHealth(PAC.getHealth() + 1);
			}
		}
		healthRegen--;

	}

}
