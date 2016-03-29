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
	private CharacterSave battleMage;
	private CharacterSave rogue;
	
	private int level;
	
	public Save(){	
		
	}

	public void saveCharacter(){
		if(ShootEmUp.currentLevel.getLevel() > level){
			level = ShootEmUp.currentLevel.getLevel();
		}
		
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
		case BATTLE_MAGE:
			battleMage = new CharacterSave();
			break;
		case ROGUE:
			rogue = new CharacterSave();
			break;
		}
	}
	
	public CharacterSave getCharacter(TypeAttack type) {
		switch(type){
		case ARCHER:
			return archer;
		case BATTLE_MAGE:
			return battleMage;
		case MAGE:
			return mage;
		case ROGUE:
			return rogue;
		case WARRIOR:
			return warrior;
		default:
			return warrior;
		}
	}

	public int getLevel() {
		return level;
	}
}
