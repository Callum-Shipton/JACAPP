package save;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2i;

import components.attack.PlayerAttack;
import main.GameBase;
import main.ShootEmUp;

public class ShootEmUpSave extends Save {

	private static final long serialVersionUID = 7179389236763035983L;

	private Map<String, CharacterSave> characters = new HashMap<>();
	private Vector2i level;

	@Override
	public ShootEmUpSave load(int num) throws Exception {
		ShootEmUpSave save = (ShootEmUpSave) super.load(num);
		characters.putAll(save.characters);
		level = save.level;
		return save;
	}

	public void saveCharacter(String attackType, PlayerAttack attack) {
		GameBase gameBase = ShootEmUp.getGame();
		characters.put(attackType, new CharacterSave(attack));
	}

	public Vector2i getLevel() {
		return level;
	}

	public void setLevel(Vector2i level) {
		this.level = level;
	}

	public Map<String, CharacterSave> getCharacters() {
		return characters;
	}

	public CharacterSave getCharacter(String type) {
		return characters.get(type);
	}
}
