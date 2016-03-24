package save;
import java.io.Serializable;

import components.TypeComponent;
import components.attack.TypeAttack;
import components.attack.PlayerAttack;
import main.ShootEmUp;

public class Save implements Serializable{

	private static final long serialVersionUID = 7179389236763035983L;

	private CharacterSave warrior;
	private CharacterSave archer;
	private CharacterSave mage;
	
	private int level;
	
	private int wave;
	
	public Save(){
		TypeAttack tempAttack = ((PlayerAttack) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.ATTACK)).getTypeAttack();
		
		switch(tempAttack){
		case WARRIOR:
			warrior = new CharacterSave();
			break;
		case ARCHER:
			archer = new CharacterSave();
			break;
		case MAGE:
			mage = new CharacterSave();
			break;
		}
		
		level = ShootEmUp.currentLevel.getLevel();
		wave = ShootEmUp.currentLevel.getSpawner().getWave();
	}

	public CharacterSave getWarrior() {
		return warrior;
	}

	public CharacterSave getArcher() {
		return archer;
	}

	public CharacterSave getMage() {
		return mage;
	}

	public int getLevel() {
		return level;
	}

	public int getWave() {
		return wave;
	}
}
