package save;

import java.io.Serializable;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.TypeAttack;
import io.FileManager;
import main.ShootEmUp;

public class Save implements Serializable {

	private static final long serialVersionUID = 7179389236763035983L;

	private static final byte[] KEY = "funbrella0000000".getBytes();

	private CharacterSave warrior;
	private CharacterSave archer;
	private CharacterSave mage;
	private CharacterSave battleMage;
	private CharacterSave rogue;

	private int level = 1;

	public CharacterSave getCharacter(TypeAttack type) {
		switch (type) {
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
			return null;
		}
	}

	public int getLevel() {
		return this.level;
	}

	public Save load(int num) throws Exception {

		Save save = (Save) FileManager.loadEncryptedFile("save" + num + ".ser", KEY);

		this.warrior = save.warrior;
		this.archer = save.archer;
		this.mage = save.mage;
		this.battleMage = save.battleMage;
		this.rogue = save.rogue;

		return save;
	}

	public void saveCharacter() {
		if (ShootEmUp.getGame().getLevel() > this.level) {
			level = ShootEmUp.getGame().getLevel();
		}
		BaseAttack BA = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.ATTACK);
		TypeAttack tempAttack = BA.getAttackType();

		switch (tempAttack) {
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

	public void saveToSystem(int num) {
		FileManager.saveEncryptedFile("save" + num + ".ser", KEY, this);
	}
}
