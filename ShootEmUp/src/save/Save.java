package save;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import components.TypeComponent;
import io.FileManager;
import main.GameBase;
import main.ShootEmUp;

public class Save implements Serializable {

	private static final long serialVersionUID = 7179389236763035983L;

	private static final byte[] KEY = "funbrella0000000".getBytes();

	private Map<String, CharacterSave> characters = new HashMap<>();

	private int level = 1;

	public CharacterSave getCharacter(String type) {
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

	public void saveCharacter(String attackType) {
		GameBase gameBase = ShootEmUp.getGame();
		level = gameBase.getLevel();

		characters.put(attackType, new CharacterSave(gameBase.getPlayer().getComponent(TypeComponent.ATTACK)));
	}

	public void saveToSystem(int num) {
		FileManager.saveEncryptedFile("save" + num + ".ser", KEY, this);
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Map<String, CharacterSave> getCharacters() {
		return characters;
	}
}
