package save;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.TypeAttack;
import io.FileManager;
import main.ShootEmUp;

public class Save implements Serializable {

	private static final long serialVersionUID = 7179389236763035983L;

	private static final byte[] KEY = "funbrella0000000".getBytes();

	private Map<TypeAttack, CharacterSave> characters = new EnumMap<>(TypeAttack.class);

	private int level = 1;

	public CharacterSave getCharacter(TypeAttack type) {
		return characters.get(type);
	}

	public int getLevel() {
		return level;
	}

	public Save load(int num) throws Exception {

		Save save = (Save) FileManager.loadEncryptedFile("save" + num + ".ser", KEY);

		characters.putAll(save.getCharacters());

		this.level = save.level;

		return save;
	}

	public void saveCharacter() {
		if (ShootEmUp.getGame().getLevel() > level) {
			level = ShootEmUp.getGame().getLevel();
		}
		BaseAttack attackComponent = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.ATTACK);
		characters.put(attackComponent.getAttackType(), new CharacterSave());
	}

	public void saveToSystem(int num) {
		FileManager.saveEncryptedFile("save" + num + ".ser", KEY, this);
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Map<TypeAttack, CharacterSave> getCharacters() {
		return characters;
	}
}
